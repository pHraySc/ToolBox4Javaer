/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import phray.sccc.own.log.LoggerUtil;

import java.util.concurrent.CountDownLatch;

/**
 * 多线程执行Runnable
 *
 * @author Phray
 * @Version BaseInnerRun.java, v 0.1 2022-05-24 7:21 Sccc Exp $
 */
@Slf4j
public abstract class BaseInnerRun extends BaseJob implements Runnable {

	protected BaseInnerRun() {
		super();
	}

	protected BaseInnerRun(String bizName) {
		super(bizName);
	}

	protected BaseInnerRun(String bizName, CountDownLatch latch) {
		super(bizName, latch);
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		StopWatch sw = new StopWatch(getDesc());
		try {
			sw.start();
			super.onBefore();
			doRun();
		} catch (Exception e) {
			super.onError(e);
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
	 */
	protected abstract void doRun();
}
