/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.log;

import org.slf4j.Logger;
import phray.sccc.own.json.JsonUtil;
import phray.sccc.own.model.BaseTracingLogModel;

import java.util.Objects;

/**
 * (Jackson序列化)规范化日志打印
 *
 * @author Phray
 * @Version LoggerUtil.java, v 0.1 2022-05-24 7:46 Sccc Exp $
 */
public class LoggerUtil {

	/**
	 * 右（结尾）修饰符
	 */
	private final static char RIGHT_TAG = ']';

	/**
	 * 左（开头）修饰符
	 */
	private final static char LEFT_TAG = '[';

	/**
	 * 日志追踪模型默认Prefix(一般用做日志抓取工具识别的前缀)
	 */
	private final static String TRACE_PREFIX = "tracing_msg:";

	// < ---------- debug level ---------- begin>

	/**
	 * 生成<font color="blue">调试</font>级别日志
	 *
	 * @param logger  日志打印对象
	 * @param message 日志信息
	 */
	public static void debug(Logger logger, String message) {
		if (Objects.isNull(logger)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(getLogString(message));
		}
	}

	/**
	 * 生成<font color="blue">调试</font>级别日志,
	 * 并且通过传入格式化模版{@code format}, 以及格式化参数{@code args}（使用可变参数避免在日志级别不够时字符串拼接带来的资源浪费）,
	 * 通过{@link String#format(String, Object...)}进行格式化.
	 *
	 * @param logger 日志打印对象
	 * @param format 日志格式化信息
	 * @param args   格式化参数
	 */
	public static void debug(Logger logger, String format, Object... args) {
		if (Objects.isNull(logger)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(getLogString(String.format(format, args)));
		}
	}

	/**
	 * 生成<font color="blue">调试</font>级别日志,
	 * 接入<b>日志追踪模型</b>
	 *
	 * @param logger          日志打印对象
	 * @param tracingLogModel 日志追踪模型
	 */
	public static void debug(Logger logger, BaseTracingLogModel tracingLogModel) {
		if (Objects.isNull(logger)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(TRACE_PREFIX + JsonUtil.toJson(tracingLogModel));
		}
	}
	// < ---------- debug level ---------- end>

	// < ---------- info level ---------- begin>

	/**
	 * 生成<font color="green">通知</font>级别日志
	 *
	 * @param logger  日志打印对象
	 * @param message 日志信息
	 */
	public static void info(Logger logger, String message) {
		if (Objects.isNull(logger)) {
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info(getLogString(message));
		}
	}

	/**
	 * 生成<font color="green">通知</font>级别日志,
	 * 并且通过传入格式化模版{@code format}, 以及格式化参数{@code args}（使用可变参数避免在日志级别不够时字符串拼接带来的资源浪费）,
	 * 通过{@link String#format(String, Object...)}进行格式化.
	 *
	 * @param logger 日志打印对象
	 * @param format 日志格式化信息
	 * @param args   格式化参数
	 */
	public static void info(Logger logger, String format, Object... args) {
		if (Objects.isNull(logger)) {
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info(getLogString(String.format(format, args)));
		}
	}

	/**
	 * 生成<font color="green">通知</font>级别日志,
	 * 接入<b>日志追踪模型</b>
	 *
	 * @param logger          日志打印对象
	 * @param tracingLogModel 日志追踪模型
	 */
	public static void info(Logger logger, BaseTracingLogModel tracingLogModel) {
		if (Objects.isNull(logger)) {
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info(TRACE_PREFIX + JsonUtil.toJson(tracingLogModel));
		}
	}
	// < ---------- info level ---------- end>

	// < ---------- warn level ---------- begin>

	/**
	 * 生成<font color="brown">警告</font>级别日志
	 *
	 * @param logger  日志打印对象
	 * @param message 日志信息
	 */
	public static void warn(Logger logger, String message) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.warn(getLogString(message));
	}

	/**
	 * 生成<font color="brown">警告</font>级别日志,
	 * 并且通过传入格式化模版{@code format}, 以及格式化参数{@code args}（使用可变参数避免在日志级别不够时字符串拼接带来的资源浪费）,
	 * 通过{@link String#format(String, Object...)}进行格式化.
	 *
	 * @param logger 日志打印对象
	 * @param format 日志格式化信息
	 * @param args   格式化参数
	 */
	public static void warn(Logger logger, String format, Object... args) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.warn(getLogString(String.format(format, args)));
	}

	/**
	 * 生成<font color="brown">警告</font>级别日志,
	 * 接入<b>日志追踪模型</b>
	 *
	 * @param logger          日志打印对象
	 * @param tracingLogModel 日志追踪模型
	 */
	public static void warn(Logger logger, BaseTracingLogModel tracingLogModel) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.warn(TRACE_PREFIX + JsonUtil.toJson(tracingLogModel));
	}

	/**
	 * 生成<font color="brown">警告</font>级别日志
	 *
	 * @param logger 日志打印对象
	 * @param cause  异常堆栈(nullable)
	 * @param msg    日志信息
	 */
	public static void warn(Logger logger, Throwable cause, String msg) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.warn(getLogString(msg), cause);
	}

	/**
	 * 生成<font color="brown">警告</font>级别日志,
	 * 并且通过传入格式化模版{@code format}, 以及格式化参数{@code args}（使用可变参数避免在日志级别不够时字符串拼接带来的资源浪费）,
	 * 通过{@link String#format(String, Object...)}进行格式化.
	 *
	 * @param logger 日志打印对象
	 * @param cause  异常堆栈(nullable)
	 * @param format 日志格式化信息
	 * @param args   格式化参数
	 */
	public static void warn(Logger logger, Throwable cause, String format, Object... args) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.warn(getLogString(String.format(format, args)), cause);
	}
	// < ---------- warn level ---------- end>

	// < ---------- error level ---------- begin>

	/**
	 * 生成<font color="red">错误</font>级别日志
	 *
	 * @param logger  日志打印对象
	 * @param message 日志信息
	 */
	public static void error(Logger logger, String message) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.error(getLogString(message));
	}

	/**
	 * 生成<font color="red">错误</font>级别日志,
	 * 并且通过传入格式化模版{@code format}, 以及格式化参数{@code args}（使用可变参数避免在日志级别不够时字符串拼接带来的资源浪费）,
	 * 通过{@link String#format(String, Object...)}进行格式化.
	 *
	 * @param logger 日志打印对象
	 * @param format 日志格式化信息
	 * @param args   格式化参数
	 */
	public static void error(Logger logger, String format, Object... args) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.error(getLogString(String.format(format, args)));
	}

	/**
	 * 生成<font color="red">错误</font>级别日志,
	 * 接入<b>日志追踪模型</b>
	 *
	 * @param logger          日志打印对象
	 * @param tracingLogModel 日志追踪模型
	 */
	public static void error(Logger logger, BaseTracingLogModel tracingLogModel) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.error(TRACE_PREFIX + JsonUtil.toJson(tracingLogModel));
	}

	/**
	 * 生成<font color="red">错误</font>级别日志
	 *
	 * @param logger 日志打印对象
	 * @param cause  异常堆栈(nullable)
	 * @param msg    日志信息
	 */
	public static void error(Logger logger, Throwable cause, String msg) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.error(getLogString(msg), cause);
	}

	/**
	 * 生成<font color="brown">警告</font>级别日志,
	 * 并且通过传入格式化模版{@code format}, 以及格式化参数{@code args}（使用可变参数避免在日志级别不够时字符串拼接带来的资源浪费）,
	 * 通过{@link String#format(String, Object...)}进行格式化.
	 *
	 * @param logger 日志打印对象
	 * @param cause  异常堆栈(nullable)
	 * @param format 日志格式化信息
	 * @param args   格式化参数
	 */
	public static void error(Logger logger, Throwable cause, String format, Object... args) {
		if (Objects.isNull(logger)) {
			return;
		}
		logger.error(getLogString(String.format(format, args)), cause);
	}
	// < ---------- error level ---------- end>

	/**
	 * 生成输出到日志的字符串
	 *
	 * @param objs 任意个要输出到日志的参数
	 * @return 输出到日志的字符串
	 */
	private static String getLogString(final Object... objs) {
		if (Objects.isNull(objs)) {
			return "";
		}

		final StringBuilder sb = new StringBuilder();

		for (Object obj : objs) {
			sb.append(RIGHT_TAG);
			sb.append(obj);
			sb.append(LEFT_TAG);
		}

		return sb.toString();
	}

}
