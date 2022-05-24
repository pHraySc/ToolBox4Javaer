/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常码枚举，可自定义新增
 *
 * @author Phray
 * @Version ErrorCodeEnum.java, v 0.1 2022-05-24 7:38 Sccc Exp $
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

	SUCCESS("000", "成功"),
	ILLEGAL_ARGUMENT("001", "参数不正确"),
	PROGRAM_ERROR("098", "程序错误"),
	COMMON_CONVERTER_ERROR("100", "Converter转换异常"),
	UNKNOWN_EXCEPTION("999", "未知异常"),
	;

	/**
	 * 编码
	 */
	private final String code;

	/**
	 * 将传递到外部的异常信息
	 */
	private final String message;
}
