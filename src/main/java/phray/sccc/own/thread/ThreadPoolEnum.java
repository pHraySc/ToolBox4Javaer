/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程的线程池枚举，统一封装创建线程池的核心参数
 *
 * @author Phray
 * @Version ThreadPoolEnum.java, v 0.1 2022-05-22 22:11 Sccc Exp $
 */
@Getter
@AllArgsConstructor
public enum ThreadPoolEnum {

	/**
	 * 通用线程池
	 * 建议 -
	 * CPU密集：coreCnt = n + 1
	 * IO密集：coreCnt = 2n (由于IO密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程，如CPU核数*2)
	 * <p>
	 * IO密集型，即该任务需要大量的IO，即大量的阻塞。
	 * 在单线程上运IO密集型的任务会导致浪费大量的CPU运算能力浪费在等待。
	 * 所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上，这种加速主要就是利用了被浪费掉的阻塞时间。
	 * IO密集型时，大部分线程都阻塞，故需要多配置线程数：
	 * 参考公式：CPU核数 /（1 - 阻系数）
	 * 比如8核CPU：8/(1 - 0．9)=80个线程数
	 * 阻塞系数在0.8~0.9之间
	 * </p>
	 * n = Core数量
	 */
	COMMON("COMMON", "通用", 4, 8, 512, new ThreadPoolExecutor.CallerRunsPolicy(), 60L);

	/**
	 * 标识码
	 */
	private final String code;

	/**
	 * 描述
	 */
	private final String desc;

	/**
	 * 核心线程数
	 */
	private final int coreCnt;

	/**
	 * 最大线程数
	 */
	private final int maxCnt;

	/**
	 * 任务队列大小
	 */
	private final int queueSize;

	/**
	 * 拒绝策略
	 */
	private final RejectedExecutionHandler handler;

	/**
	 * 空闲线程存活时间(second)
	 */
	private final Long keepAliveTime;
}
