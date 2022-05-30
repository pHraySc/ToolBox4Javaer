/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.exception;

/**
 * 业务异常
 *
 * @author Phray
 * @Version BizException.java, v 0.1 2022-05-24 7:37 Sccc Exp $
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -3592428677323499855L;

	/**
	 * 异常错误码枚举
	 */
	private final ErrorCodeEnum code;

	public BizException(final ErrorCodeEnum code, final String exceptionMsg) {
		super(exceptionMsg);
		this.code = code;
	}

	public BizException(final ErrorCodeEnum code, final String format, final Object... formatArgs) {
		super(String.format(format, formatArgs));
		this.code = code;
	}

	public BizException(final ErrorCodeEnum code, final Throwable cause, final String exceptionMsg) {
		super(exceptionMsg, cause);
		this.code = code;
	}

	public BizException(final ErrorCodeEnum code, final Throwable cause) {
		super(code.getMessage(), cause);
		this.code = code;
	}


	public BizException(ErrorCodeEnum code, final Throwable cause,
						final String format, final Object... formatArgs) {
		super(String.format(format, formatArgs), cause);
		this.code = code;
	}
}
