/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 判空取值工具，可以用于对象、列表、哈希表判空取值，简洁代码
 *
 * @author Phray
 * @Version OptionalUtil.java, v 0.1 2022-05-21 21:38 Sccc Exp $
 */
public class OptionalUtil {

	/**
	 * 通过Object类型获取值，包含判空逻辑
	 * <p>
	 * 若{@code value}为{@link String}，使用{@link StringUtils#isNotBlank(CharSequence)}进行判空；<br>
	 * 若判断结果为“空”，使用{@code isNullFunc}Function方法获取结果，否则使用{@code notNullFunc}Function方法获取结果。
	 * </p>
	 *
	 * @param value       原始值，入参，可为空
	 * @param isNullFunc  原始值{@code value}为空时执行的获取结果的Function
	 * @param notNullFunc 原始值{@code value}不为空时执行的获取结果的Function
	 * @param <T>         入参{@code value}类型泛型
	 * @param <R>         出参类型泛型
	 * @return 返回转换后的结果
	 */
	public static <T, R> R getByObj(T value, Function<T, R> isNullFunc, Function<T, R> notNullFunc) {
		if (value instanceof String) {
			return StringUtils.isNotBlank((CharSequence) value) ? notNullFunc.apply(value) : isNullFunc.apply(value);
		}
		return Optional.ofNullable(value).isPresent() ? notNullFunc.apply(value) : isNullFunc.apply(null);
	}

	/**
	 * 通过List类型获取值，包含判空逻辑，返回值由Function的出参决定
	 * <p>
	 * 对{@code value}使用{@link CollectionUtils#isNotEmpty(Collection)}进行判空；<br>
	 * 若判断结果为“空”，使用{@code isNullFunc}Function方法获取结果，否则使用{@code notNullFunc}Function方法获取结果。
	 * </p>
	 *
	 * @param value        原始值，入参，可为空
	 * @param isEmptyFunc  原始值{@code value}为空时执行的获取结果的Function
	 * @param notEmptyFunc 原始值{@code value}不为空时执行的获取结果的Function
	 * @param <T>          入参{@code value}类型泛型
	 * @param <R>          出参类型泛型
	 * @return 返回转换后的结果
	 */
	public static <T, R> R getByList(List<T> value, Function<List<T>, R> isEmptyFunc,
									 Function<List<T>, R> notEmptyFunc) {
		return CollectionUtils.isNotEmpty(value) ? notEmptyFunc.apply(value) : isEmptyFunc.apply(value);
	}

	/**
	 * 通过List类型获取值，包含判空逻辑，返回值为List对象，
	 * 对List打平进行处理，传入对List中的元素处理逻辑的Function，内部会过滤掉为null的元素
	 * <p>
	 * 对{@code value}使用{@link CollectionUtils#isNotEmpty(Collection)}进行判空；<br>
	 * 若判断结果为“空”，使用{@code isNullFunc}Function方法获取结果，否则使用{@code notNullFunc}Function方法获取结果。
	 * </p>
	 *
	 * @param value        原始值，入参，可为空
	 * @param isEmptyFunc  原始值{@code value}为空时执行的获取结果的Function
	 * @param notEmptyFunc 原始值{@code value}不为空时执行的，对List内部元素进行处理以获取结果的Function
	 * @param <T>          入参{@code value}类型泛型
	 * @param <R>          出参类型泛型
	 * @return 返回转换后的结果
	 */
	public static <T, R> List<R> getByListFlat(List<T> value, Function<List<T>, List<R>> isEmptyFunc,
											   Function<T, R> notEmptyFunc) {
		return CollectionUtils.isNotEmpty(value)
				? value.stream().filter(Objects::nonNull).map(notEmptyFunc).collect(Collectors.toList())
				: isEmptyFunc.apply(value);
	}

	/**
	 * 通过Set类型获取值，包含判空逻辑，返回值由Function的出参决定
	 * <p>
	 * 对{@code value}使用{@link CollectionUtils#isNotEmpty(Collection)}进行判空；<br>
	 * 若判断结果为“空”，使用{@code isNullFunc}Function方法获取结果，否则使用{@code notNullFunc}Function方法获取结果。
	 * </p>
	 *
	 * @param value        原始值，入参，可为空
	 * @param isEmptyFunc  原始值{@code value}为空时执行的获取结果的Function
	 * @param notEmptyFunc 原始值{@code value}不为空时执行的获取结果的Function
	 * @param <T>          入参{@code value}类型泛型
	 * @param <R>          出参类型泛型
	 * @return 返回转换后的结果
	 */
	public static <T, R> R getBySet(Set<T> value, Function<Set<T>, R> isEmptyFunc,
									Function<Set<T>, R> notEmptyFunc) {
		return CollectionUtils.isNotEmpty(value) ? notEmptyFunc.apply(value) : isEmptyFunc.apply(value);
	}

	/**
	 * 通过Set类型获取值，包含判空逻辑，返回值为Sett对象，
	 * 对Set打平进行处理，传入对Set中的元素处理逻辑的Function，内部会过滤掉为null的元素
	 * <p>
	 * 对{@code value}使用{@link CollectionUtils#isNotEmpty(Collection)}进行判空；<br>
	 * 若判断结果为“空”，使用{@code isNullFunc}Function方法获取结果，否则使用{@code notNullFunc}Function方法获取结果。
	 * </p>
	 *
	 * @param value        原始值，入参，可为空
	 * @param isEmptyFunc  原始值{@code value}为空时执行的获取结果的Function
	 * @param notEmptyFunc 原始值{@code value}不为空时执行的，对Set内部元素进行处理以获取结果的Function
	 * @param <T>          入参{@code value}类型泛型
	 * @param <R>          出参类型泛型
	 * @return 返回转换后的结果
	 */
	public static <T, R> Set<R> getBySetFlat(Set<T> value, Function<Set<T>, Set<R>> isEmptyFunc,
											 Function<T, R> notEmptyFunc) {
		return CollectionUtils.isNotEmpty(value)
				? value.stream().filter(Objects::nonNull).map(notEmptyFunc).collect(Collectors.toSet())
				: isEmptyFunc.apply(value);
	}

	/**
	 * 通过Map类型获取值，包含判空逻辑，返回值由Function的出参决定
	 * <p>
	 * 对{@code value}使用{@link CollectionUtils#isNotEmpty(Collection)}进行判空；<br>
	 * 若判断结果为“空”，使用{@code isNullFunc}Function方法获取结果，否则使用{@code notNullFunc}Function方法获取结果。
	 * </p>
	 *
	 * @param value        原始值，入参，可为空
	 * @param isEmptyFunc  原始值{@code value}为空时执行的获取结果的Function
	 * @param notEmptyFunc 原始值{@code value}不为空时执行的获取结果的Function
	 * @param <K>          入参{@code value}Key类型泛型
	 * @param <V>          入参{@code value}Value类型泛型
	 * @param <R>          出参类型泛型
	 * @return 返回转换后的结果
	 */
	public static <K, V, R> R getByMap(Map<K, V> value, Function<Map<K, V>, R> isEmptyFunc,
									   Function<Map<K, V>, R> notEmptyFunc) {
		return MapUtils.isNotEmpty(value)
				? notEmptyFunc.apply(value) : isEmptyFunc.apply(value);
	}
}
