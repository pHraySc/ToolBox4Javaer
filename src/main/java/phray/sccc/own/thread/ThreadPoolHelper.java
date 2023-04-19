/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.thread;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import phray.sccc.own.exception.BizException;
import phray.sccc.own.exception.ErrorCodeEnum;
import phray.sccc.own.log.LoggerUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 多线程服务辅助类，包装批处理任务，对外提供统一执行入口
 *
 * @author Phray
 * @Version ThreadPoolHelper.java, v 0.1 2022-05-22 22:10 Sccc Exp $
 */
@Slf4j
@Component
public class ThreadPoolHelper {

	/**
	 * 线程池Map<ThreadPoolEnum.code(String), ThreadPoolExecutor>
	 *
	 * @see ThreadPoolEnum
	 * @see java.util.concurrent.ThreadPoolExecutor
	 */
	private final Map<String, ThreadPoolExecutor> executorMap = Maps.newConcurrentMap();

	// < ========== Init ThreadPoolExecutor ========== begin>

	/**
	 * 创建executor，使用双重检查，并设置线程默认为【守护线程】，设置默认【允许核心线程Timeout】
	 *
	 * @param threadPoolEnum 线程池枚举
	 * @return executor
	 */
	public ThreadPoolExecutor getExecutor(ThreadPoolEnum threadPoolEnum) {
		if (!executorMap.containsKey(threadPoolEnum.getCode())) {
			synchronized (ThreadPoolHelper.class) {
				// Double Check
				if (!executorMap.containsKey(threadPoolEnum.getCode())) {

					ThreadPoolExecutor executor = doGetExecutor(threadPoolEnum);
					executorMap.put(threadPoolEnum.getCode(), executor);

					LoggerUtil.info(log, "Create thread_pool: [%s], core: [%d], max: [%d], queueSize: [%d]",
							threadPoolEnum.getDesc(),
							threadPoolEnum.getCoreCnt(), threadPoolEnum.getMaxCnt(),
							threadPoolEnum.getQueueSize());
				} else {
					LoggerUtil.debug(log, "[Concurrent Created]");
				}
			}
		}
		return executorMap.get(threadPoolEnum.getCode());
	}

	private ThreadPoolExecutor doGetExecutor(ThreadPoolEnum threadPoolEnum) {
		ThreadFactoryBuilder builder = createThreadFactoryBuilder(threadPoolEnum);
		return createExecutor(threadPoolEnum, builder);
	}

	private ThreadFactoryBuilder createThreadFactoryBuilder(ThreadPoolEnum threadPoolEnum) {
		ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
		builder.setNameFormat(threadPoolEnum.getCode() + "_%d");
		builder.setDaemon(true);
		return builder;
	}

	private ThreadPoolExecutor createExecutor(ThreadPoolEnum threadPoolEnum, ThreadFactoryBuilder builder) {
		ThreadPoolExecutor executor = doCreateExecutor(threadPoolEnum, builder);
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

	private ThreadPoolExecutor doCreateExecutor(ThreadPoolEnum threadPoolEnum, ThreadFactoryBuilder builder) {
		return new ThreadPoolExecutor(
				threadPoolEnum.getCoreCnt(), threadPoolEnum.getMaxCnt(),
				threadPoolEnum.getKeepAliveTime(), TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(threadPoolEnum.getQueueSize()),
				builder.build(),
				threadPoolEnum.getHandler()
		);
	}

	// < ========== Init ThreadPoolExecutor ========== end>

	/**
	 * 批量执行Runnable任务
	 *
	 * @param runnableTasks
	 * @param bizName
	 * @param threadPoolEnum
	 */
	public void runBatchTask(List<BaseInnerRun> runnableTasks, String bizName, ThreadPoolEnum threadPoolEnum) {
		if (CollectionUtils.isEmpty(runnableTasks)) {
			return;
		}
		// 1-获取executor
		ThreadPoolExecutor executor = getExecutor(threadPoolEnum);
		// 2-创建cdl，Count=任务列表size
		CountDownLatch innerLatch = new CountDownLatch(runnableTasks.size());
		// 3-错误标识
		AtomicBoolean errorFlag = new AtomicBoolean();
		// 4-异常(这里抛出异常是为了在主线程中抓住，主要目的是抛到主线程，具体异常会在线程中打印出来)
		AtomicReference<RuntimeException> errorRef = new AtomicReference<>();
		// 5-提交任务
		for (BaseInnerRun task : runnableTasks) {
			fillTaskAttr(innerLatch, errorFlag, errorRef, task);
			executor.execute(task);
		}

		try {
			LoggerUtil.info(log, "[%s]_running_task: [%d], queued_task: [%d]",
					threadPoolEnum.getCode(), executor.getActiveCount(), executor.getQueue().size());
			innerLatch.await();

			if (errorFlag.get()) {
				throw errorRef.get();
			}
		} catch (RuntimeException e) {
			LoggerUtil.error(log, e, "[%s]: 执行异常!", bizName);
			throw e;
		} catch (Exception e) {
			LoggerUtil.error(log, e, "[%s]: 未知异常, 请检查!", bizName);
			throw new BizException(ErrorCodeEnum.UNKNOWN_EXCEPTION, e, "[%s]: 未知异常, 请检查!", bizName);
		}
	}

	/**
	 * 执行任务，返回{@link Future}，外部自行阻塞获取结果
	 *
	 * @param task
	 * @param threadPoolEnum
	 * @param <R>
	 * @return
	 */
	public <R> Future<R> runCallableTask(BaseInnerCall<R> task, ThreadPoolEnum threadPoolEnum) {
		if (Objects.isNull(task)) {
			return null;
		}

		ThreadPoolExecutor executor = getExecutor(threadPoolEnum);
		return executor.submit(task);
	}

	public <R> R runBatchTasksWithCallback(List<BaseInnerCall<R>> callableTasks,
										   CallableTaskProcessService<R> processService,
										   int timeout, TimeUnit unit,
										   String bizName, ThreadPoolEnum threadPoolEnum) {
		// 1-初始化返回数据
		processService.init();
		if (CollectionUtils.isEmpty(callableTasks)) {
			return processService.get();
		}

		// 2-获取executor
		ThreadPoolExecutor executor = getExecutor(threadPoolEnum);
		// 2-创建cdl，Count=任务列表size
		CountDownLatch innerLatch = new CountDownLatch(callableTasks.size());
		// 3-错误标识
		AtomicBoolean errorFlag = new AtomicBoolean();
		// 4-异常(这里抛出异常是为了在主线程中抓住，主要目的是抛到主线程，具体异常会在线程中打印出来)
		AtomicReference<RuntimeException> errorRef = new AtomicReference<>();
		// 5-提交任务
		List<Future<R>> futureWrapper = Lists.newLinkedList();
		for (BaseInnerCall<R> task : callableTasks) {
			fillTaskAttr(innerLatch, errorFlag, errorRef, task);
			futureWrapper.add(executor.submit(task));
		}

		try {
			LoggerUtil.info(log, "[%s]_running_task: [%d], queued_task: [%d]",
					threadPoolEnum.getCode(), executor.getActiveCount(), executor.getQueue().size());
			if (!innerLatch.await(timeout, unit)) {
				throw new BizException(ErrorCodeEnum.PROGRAM_ERROR, "异步任务[%s]执行超时, Timeout: [%s], TimeUnit: [%s]",
						bizName, timeout, unit);
			}
		} catch (InterruptedException e) {
			LoggerUtil.error(log, e, "[%s]: 异步执行被中断!", bizName);
		}

		for (Future<R> future : futureWrapper) {
			try {
				R curResult = future.get(timeout, unit);
				if (errorFlag.get()) {
					throw errorRef.get();
				}
				processService.fill(curResult);
			} catch (RuntimeException e) {
				LoggerUtil.error(log, e, "[%s]: 执行异常!", bizName);
				throw e;
			} catch (TimeoutException e) {
				LoggerUtil.error(log, e, "[%s]: 执行超时!", bizName);
				throw new BizException(ErrorCodeEnum.PROGRAM_ERROR, e, "[%s]: 执行超时!", bizName);
			} catch (Exception e) {
				LoggerUtil.error(log, e, "[%s]: 未知异常, 请检查!", bizName);
				throw new BizException(ErrorCodeEnum.UNKNOWN_EXCEPTION, e, "[%s]: 未知异常, 请检查!", bizName);
			}
		}

		return processService.get();
	}

	/**
	 * Job基础属性填充
	 *
	 * @param innerLatch
	 * @param errorFlag
	 * @param errorRef
	 * @param task
	 */
	private void fillTaskAttr(CountDownLatch innerLatch, AtomicBoolean errorFlag,
							  AtomicReference<RuntimeException> errorRef, BaseJob task) {
		task.setCountDownLatch(innerLatch);
		task.setErrorFlag(errorFlag);
		task.setErrorReference(errorRef);
	}

    public <Req, Resp> void doExecute(List<ExecContext<Req, Resp>> execList,
                                      ThreadPoolEnum threadPoolEnum,
                                      String bizName,
                                      Req req, Resp resp) {
        Assert.notEmpty(execList, "需要执行的上下文不能为空!");
        Assert.notNull(threadPoolEnum, "线程池枚举不能为空!");
        Assert.notNull(bizName, "bizName不能为空!");

        List<BaseInnerRun> runnableTasks = Lists.newArrayList();
        for (ExecContext<Req, Resp> context : execList) {
            if (Objects.isNull(context.getExecFunc())) {
                continue;
            }
            runnableTasks.add(new BaseInnerRun(context.getBizName()) {
                @Override
                protected void doRun() {
                    context.getExecFunc().accept(req, resp);
                }
            });
        }
        this.runBatchTask(runnableTasks, bizName, threadPoolEnum);
    }

}
