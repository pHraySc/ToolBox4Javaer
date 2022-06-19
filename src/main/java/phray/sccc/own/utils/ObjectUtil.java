/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

/**
 * 对象判空工具(多值)，任意不空，默认值等
 *
 * @author Phray
 * @Version ObjectUtil.java, v 0.1 2022-06-19 21:03 Sccc Exp $
 */
public class ObjectUtil {

	// <========== Boolean包装类型请使用 - org.apache.commons.lang3.BooleanUtils ==========>

	/**
	 * 解析可能为空的Integer包装类型{@code wrapper}为基本类型(int)，使用{@code defaultVal}作为默认值
	 *
	 * @param wrapper    Integer包装类型，可空
	 * @param defaultVal 基本类型默认值
	 * @return 解析后的基本类型值
	 */
	public static int intValue(Integer wrapper, int defaultVal) {
		return Objects.isNull(wrapper) ? defaultVal : wrapper;
	}

	/**
	 * 解析可能为空的Long包装类型{@code wrapper}为基本类型(long)，使用{@code defaultVal}作为默认值
	 *
	 * @param wrapper    Long包装类型，可空
	 * @param defaultVal 基本类型默认值
	 * @return 解析后的基本类型值
	 */
	public static long longValue(Long wrapper, long defaultVal) {
		return Objects.isNull(wrapper) ? defaultVal : wrapper;
	}

	/**
	 * 解析可能为空的Float包装类型{@code wrapper}为基本类型(float)，使用{@code defaultVal}作为默认值
	 *
	 * @param wrapper    Float包装类型，可空
	 * @param defaultVal 基本类型默认值
	 * @return 解析后的基本类型值
	 */
	public static float floatValue(Float wrapper, float defaultVal) {
		return Objects.isNull(wrapper) ? defaultVal : wrapper;
	}

	/**
	 * 如果{@code obj}为null，则返回默认值{@code defaultVal}
	 *
	 * @param obj        需要检查的对象，可Null
	 * @param defaultVal obj为null时返回的默认值，也可null
	 * @param <T>        对象泛型
	 * @return obj不为null则返回obj，否则返回defaultVal
	 */
	public static <T> T defaultIfNull(final T obj, final T defaultVal) {
		return Objects.isNull(obj) ? defaultVal : obj;
	}

	/**
	 * 判断入参是否有任意一个对象不为Null
	 *
	 * @param values 需要检查的对象数组(可变)
	 * @return true: 有不为Null元素; false: 所有元素都为Null
	 */
	public static boolean anyNotNull(final Object... values) {
		if (ArrayUtils.isEmpty(values)) {
			return false;
		}
		for (Object value : values) {
			if (Objects.nonNull(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断入参是否所有元素都为Null
	 *
	 * @param values 需要检查的对象数组(可变)
	 * @return true: 所有元素都不为Null; false: 有为Null的元素
	 */
	public static boolean allNotNull(final Object... values) {
		if (ArrayUtils.isEmpty(values)) {
			return false;
		}

		for (Object value : values) {
			if (Objects.isNull(value)) {
				return false;
			}
		}
		return true;
	}
}
