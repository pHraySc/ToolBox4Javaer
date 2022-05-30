/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.thread;

/**
 * 利用依赖倒装实现的，Callable多任务结果处理器接口
 *
 * @author Phray
 * @Version CallableTaskProcessService.java, v 0.1 2022-05-24 7:24 Sccc Exp $
 */
public interface CallableTaskProcessService<R> {

	/**
	 * 结果数据结构Init
	 */
	void init();

	/**
	 * 数据Merge/fill
	 *
	 * @param data
	 */
	void fill(R data);

	/**
	 * 数据获取
	 *
	 * @return result
	 */
	R get();
}
