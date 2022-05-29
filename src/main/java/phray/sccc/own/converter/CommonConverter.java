/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import phray.sccc.own.exception.BizException;
import phray.sccc.own.exception.ErrorCodeEnum;
import phray.sccc.own.json.JsonUtil;
import phray.sccc.own.log.LoggerUtil;
import phray.sccc.own.utils.OptionalUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 公用转换器，利用{@link org.springframework.beans.BeanUtils#copyProperties(Object, Object)}进行换换
 *
 * @author Phray
 * @Version CommonConverter.java, v 0.1 2022-05-24 7:27 Sccc Exp $
 */
@Slf4j
public class CommonConverter {

	/**
	 * 对象转换，底层使用了{@link BeanUtils#copyProperties(Object, Object)}反射获取属性进行转换
	 * PS: Spring的这个方法必须保证Source属性的Getter出参类型与Target属性的Setter入参类型一致
	 * (ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType()))
	 * 若{@code source}为null，则返回null
	 *
	 * @param source     需要被转换的源数据对象
	 * @param targetType 需要进行转换的目标数据对象Class
	 * @param <S>        源数据对象泛型
	 * @param <T>        目标数据对象泛型
	 * @return 转换后的目标数据对象实例
	 * @throws BizException 转换时发生的异常
	 */
	public static <S, T> T convert(S source, Class<T> targetType) throws BizException {
		if (Objects.isNull(source)) {
			return null;
		}

		T target;
		try {
			target = targetType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LoggerUtil.error(log, e, "CommonConverter.convert()转换异常，source=[%s]，targetType=[%s]",
					JsonUtil.toJson(source), targetType.getName());
			throw new BizException(ErrorCodeEnum.COMMON_CONVERTER_ERROR, e);
		}

		return convert(source, target);
	}

	/**
	 * 对象转换，底层使用了{@link BeanUtils#copyProperties(Object, Object)}反射获取属性进行转换
	 * PS: Spring的这个方法必须保证Source属性的Getter出参类型与Target属性的Setter入参类型一致
	 * (ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType()))
	 * 若{@code source}为null，则返回{@code target}
	 *
	 * @param source 需要被转换的源数据对象
	 * @param target 需要进行转换的目标数据对象
	 * @param <S>    源数据对象泛型
	 * @param <T>    目标数据对象泛型
	 * @return 转换后的目标数据对象实例
	 * @throws BizException 转换时发生的异常
	 */
	public static <S, T> T convert(S source, T target) throws BizException {
		if (Objects.isNull(source)) {
			return target;
		}

		try {
			BeanUtils.copyProperties(source, target);
			return target;
		} catch (BeansException e) {
			LoggerUtil.error(log, e, "CommonConverter.convert()转换异常，source=[%s]，target=[%s]",
					JsonUtil.toJson(source), JsonUtil.toJson(target));
			throw new BizException(ErrorCodeEnum.COMMON_CONVERTER_ERROR, e);
		}
	}

	/**
	 * 列表对象转换，底层使用了{@link CommonConverter#convert(Object, Class)}进行转换
	 * 若{@code sourceList}为null or empty，则返回空列表{@link Collections#emptyList()}
	 *
	 * @param sourceList 需要被转换的源数据对象列表
	 * @param targetType 需要进行转换的目标数据对象Class
	 * @param <S>        源数据对象泛型
	 * @param <T>        目标数据对象泛型
	 * @return 转换后的目标数据对象实例
	 * @throws BizException 转换时发生的异常
	 */
	public static <S, T> List<T> convert(List<S> sourceList, Class<T> targetType) throws BizException {
		return OptionalUtil.getByListFlat(sourceList, p -> Collections.emptyList(),
				p -> convert(p, targetType));
	}
}
