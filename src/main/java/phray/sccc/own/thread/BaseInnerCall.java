/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import phray.sccc.own.log.LoggerUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程执行Callable
 *
 * @author Phray
 * @Version BaseInnerCall.java, v 0.1 2022-05-24 7:22 Sccc Exp $
 */
@Slf4j
public abstract class BaseInnerCall<R> extends BaseJob implements Callable<R> {
	protected BaseInnerCall() {
		super();
	}

	protected BaseInnerCall(String bizName) {
		super(bizName);
	}

	protected BaseInnerCall(String bizName, CountDownLatch latch) {
		super(bizName, latch);
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public R call() {
		StopWatch sw = new StopWatch(getDesc());
		try {
			sw.start();
			super.onBefore();
			return doCall();
		} catch (Exception e) {
			super.onError(e);
			return null;
		} finally {
			if (sw.isRunning()) {
				sw.stop();
				LoggerUtil.info(log, "任务: [%s] - 耗时: [%s]ms", sw.getId(), sw.getTotalTimeMillis());
			}
			super.onEnd();
		}
	}


	/**
	 * 具体的Task实现
	 *
	 * @return Callable Result
	 */
	protected abstract R doCall();

}
