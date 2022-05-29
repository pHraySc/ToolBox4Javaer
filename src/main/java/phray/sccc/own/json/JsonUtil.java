/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import phray.sccc.own.exception.BizException;
import phray.sccc.own.exception.ErrorCodeEnum;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * (Jackson序列化)的日志打印规范工具
 * <ul>
 *     <li>该工具针对Date类型进行序列化和反序列化均采用默认格式"yyyy-MM-dd HH:mm:ss"</li>
 *     <li>该工具针对NULL属性默认不进行序列化(dumper)</li>
 *     <li>将数字以字符串进行输出（兼容长整型输入到前端JS无法处理问题）</li>
 *     <li>其他相关默认配置请查看{@code static{}}静态代码块初始化部分</li>
 * </ul>
 *
 * @author Phray
 * @Version JsonUtil.java, v 0.1 2022-05-24 7:46 Sccc Exp $
 */
public class JsonUtil {

	/**
	 * 默认时间格式
	 */
	private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 默认Jackson#ObjectMapper
	 */
	private final static ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 静态初始化
	 */
	static {
		// < ================================================================================ >
		// 								默认[序列化]相关
		// < ================================================================================ >

		// 不包含NULL值属性
		DEFAULT_OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 将数字作为字符串输出(a. 兼容json-lib-2.4-jdk1.5; b. 长整型在返回前端页面时JS可能出现无法处理情况，将丢失后面的尾数)
		DEFAULT_OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		// BigDecimal不采用科学计数法
		DEFAULT_OBJECT_MAPPER.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
		DEFAULT_OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		// 设置默认日期格式
		DefaultDateFormat dateFormat = new DefaultDateFormat(DEFAULT_DATE_FORMAT);
		DEFAULT_OBJECT_MAPPER.setDateFormat(dateFormat);

		// < ================================================================================ >
		// 								默认[反序列化]相关
		// < ================================================================================ >
		DEFAULT_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		DEFAULT_OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		DEFAULT_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}

	/**
	 * Json字符串转Java对象
	 *
	 * @param jsonStr Json字符串
	 * @param clazz   目标对象类对象
	 * @param <T>     目标对象类型
	 * @return 目标对象实例
	 */
	public static <T> T json2Object(final String jsonStr, Class<T> clazz) {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		try {
			return DEFAULT_OBJECT_MAPPER.readValue(jsonStr, clazz);
		} catch (Exception e) {
			throw new BizException(ErrorCodeEnum.JSON_CONVERT_ERROR, e, "Json格式不正确(%s, %s)", jsonStr,
					clazz.getName());
		}
	}

	/**
	 * Json字符串转Java泛型对象，可以是任何类型；
	 * <p>
	 * 例: Map<String, Object> data = JsonUtil.json2GenericObject(jsonStr, new TypeReference<Map<String, Object>>() {})
	 * </p>
	 *
	 * @param jsonStr       Json字符串
	 * @param typeReference {@link TypeReference} 目标泛型包装类
	 * @param <T>           目标类型泛型
	 * @return 目标对象实例
	 */
	public static <T> T json2GenericObject(final String jsonStr, TypeReference<T> typeReference) {
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		try {
			return DEFAULT_OBJECT_MAPPER.readValue(jsonStr, typeReference);
		} catch (Exception e) {
			throw new BizException(ErrorCodeEnum.JSON_CONVERT_ERROR, e, "Json格式不正确(%s, %s)", jsonStr,
					typeReference.getClass().getName());
		}
	}

	/**
	 * 对象转Json字符串
	 *
	 * @param obj 源对象
	 * @return Json字符串
	 */
	public static String toJson(Object obj) {
		if (Objects.isNull(obj)) {
			return null;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			return DEFAULT_OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			throw new BizException(ErrorCodeEnum.JSON_CONVERT_ERROR, e, "Json格式不正确(%s)", obj);
		}
	}

	/**
	 * 默认时间格式化
	 * 基于joda-time(thread-safe)
	 */
	private static class DefaultDateFormat extends DateFormat {

		private static final long serialVersionUID = -4475953152706468531L;

		private final static Calendar CALENDAR = Calendar.getInstance();

		private final static NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

		/**
		 * 格式化日期格式
		 */
		private final String format;

		/**
		 * 构造函数
		 *
		 * @param format 格式化日期格式
		 */
		private DefaultDateFormat(String format) {
			super.setCalendar(CALENDAR);
			super.setNumberFormat(NUMBER_FORMAT);
			this.format = format;
		}

		/**
		 * Formats a Date into a date/time string.
		 *
		 * @param date          a Date to be formatted into a date/time string.
		 * @param toAppendTo    the string buffer for the returning date/time string.
		 * @param fieldPosition keeps track of the position of the field
		 *                      within the returned string.
		 *                      On input: an alignment field,
		 *                      if desired. On output: the offsets of the alignment field. For
		 *                      example, given a time text "1996.07.10 AD at 15:08:56 PDT",
		 *                      if the given fieldPosition is DateFormat.YEAR_FIELD, the
		 *                      begin index and end index of fieldPosition will be set to
		 *                      0 and 4, respectively.
		 *                      Notice that if the same time field appears
		 *                      more than once in a pattern, the fieldPosition will be set for the first
		 *                      occurrence of that time field. For instance, formatting a Date to
		 *                      the time string "1 PM PDT (Pacific Daylight Time)" using the pattern
		 *                      "h a z (zzzz)" and the alignment field DateFormat.TIMEZONE_FIELD,
		 *                      the begin index and end index of fieldPosition will be set to
		 *                      5 and 8, respectively, for the first occurrence of the timezone
		 *                      pattern character 'z'.
		 * @return the string buffer passed in as toAppendTo, with formatted text appended.
		 */
		@Override
		public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
			return toAppendTo.append(new DateTime(date.getTime()).toString(format));
		}

		/**
		 * Parse a date/time string according to the given parse position.  For
		 * example, a time text {@code "07/10/96 4:5 PM, PDT"} will be parsed into a {@code Date}
		 * that is equivalent to {@code Date(837039900000L)}.
		 *
		 * <p> By default, parsing is lenient: If the input is not in the form used
		 * by this object's format method but can still be parsed as a date, then
		 * the parse succeeds.  Clients may insist on strict adherence to the
		 * format by calling {@link #setLenient(boolean) setLenient(false)}.
		 *
		 * <p>This parsing operation uses the {@link #calendar} to produce
		 * a {@code Date}. As a result, the {@code calendar}'s date-time
		 * fields and the {@code TimeZone} value may have been
		 * overwritten, depending on subclass implementations. Any {@code
		 * TimeZone} value that has previously been set by a call to
		 * {@link #setTimeZone(TimeZone) setTimeZone} may need
		 * to be restored for further operations.
		 *
		 * @param source The date/time string to be parsed
		 * @param pos    On input, the position at which to start parsing; on
		 *               output, the position at which parsing terminated, or the
		 *               start position if the parse failed.
		 * @return A {@code Date}, or {@code null} if the input could not be parsed
		 */
		@Override
		public Date parse(String source, ParsePosition pos) {
			return DateTimeFormat.forPattern(format).parseDateTime(source).toDate();
		}

		/**
		 * Parses text from the beginning of the given string to produce a date.
		 * The method may not use the entire text of the given string.
		 * <p>
		 * See the {@link #parse(String, ParsePosition)} method for more information
		 * on date parsing.
		 *
		 * @param source A <code>String</code> whose beginning should be parsed.
		 * @return A <code>Date</code> parsed from the string.
		 * @throws ParseException if the beginning of the specified string
		 *                        cannot be parsed.
		 */
		@Override
		public Date parse(String source) throws ParseException {
			return parse(source, null);
		}
	}
}
