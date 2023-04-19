/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
import phray.sccc.own.exception.BizException;
import phray.sccc.own.exception.ErrorCodeEnum;
import phray.sccc.own.exception.SysException;
import phray.sccc.own.log.LoggerUtil;
import phray.sccc.own.utils.ObjectUtil;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 多线程执行-基类模版
 *
 * @author Phray
 * @Version BaseJob.java, v 0.1 2022-05-24 7:22 Sccc Exp $
 */
@Data
@Slf4j
public class BaseJob {

    /**
     * trace信息，存储MDC
     */
    private Map<String, String> traceContext;

    /**
     * 发令枪
     */
    private CountDownLatch countDownLatch;

    /**
     * 异常标识，用来标记执行任务过程中是否产生了异常
     */
    private AtomicBoolean errorFlag;

    /**
     * 异常对象引用，将线程池内的异常抛到主线程
     */
    private AtomicReference<RuntimeException> errorReference;

    /**
     * 任务描述(可选)
     */
    private String desc;

    protected BaseJob() {
        desc = "DEFAULT";
        traceContext = MDC.getCopyOfContextMap();
    }

    protected BaseJob(String bizName) {
        this();
        this.desc = bizName;
    }

    protected BaseJob(String bizName, CountDownLatch countDownLatch) {
        this();
        this.desc = bizName;
        this.countDownLatch = countDownLatch;
    }

    /**
     * 前置：资源注入
     */
    protected final void onBefore() {
        if (Objects.nonNull(traceContext)) {
            MDC.setContextMap(traceContext);
        }
    }

    /**
     * 异常：错误处理
     *
     * @param e 异常对象
     */
    protected final void onError(Exception e) {
        LoggerUtil.error(log, e, "Job: [%s]-线程内部执行异常 -> [%s]", desc, ExceptionUtils.getStackTrace(e));
        if (!ObjectUtil.allNotNull(errorFlag, errorReference)) {
            throw new BizException(ErrorCodeEnum.PROGRAM_ERROR, e, ExceptionUtils.getMessage(e));
        }

        // 1-标记异常
        errorFlag.set(true);

        // 2-填充异常对象
        if (e instanceof BizException) {
            errorReference.set((BizException) e);
        } else if (e instanceof SysException) {
            errorReference.set((SysException) e);
        } else {
            errorReference.set(
                    new BizException(
                            ErrorCodeEnum.ASYNC_EXEC_EXCEPTION, e, ExceptionUtils.getStackTrace(e)
                    )
            );
        }
    }

    /**
     * 后置：释放资源; cdl.countDown()
     */
    protected final void onEnd() {
        if (Objects.nonNull(countDownLatch)) {
            countDownLatch.countDown();
        }
        if (MapUtils.isNotEmpty(traceContext)) {
            MDC.clear();
        }
    }
}
