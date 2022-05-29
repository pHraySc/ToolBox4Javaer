/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 日志追踪组件模型-基础属性(Open Tracing)
 *
 * @author Phray
 * @Version BaseTracingLogModel.java, v 0.1 2022-05-29 21:16 Sccc Exp $
 */
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseTracingLogModel implements Serializable {

	private static final long serialVersionUID = 5210407142755810883L;

	/**
	 * 链路追踪ID(required)
	 */
	@JsonProperty("message_id")
	private String messageId;

	/**
	 * 服务(业务)类型(例如：对外服务、下游服务、中间件、数据库等等)
	 */
	@JsonProperty("type")
	private String type;

	/**
	 * 状态：Y: 成功, N: 失败(required)
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 相应时间：单位: ms(required)
	 */
	@JsonProperty("rt")
	private String rt;

	/**
	 * 错误码(nullable)
	 */
	@JsonProperty("err_code")
	private String errCode;

	/**
	 * 错误信息(nullable)
	 */
	@JsonProperty("err_info")
	private String errInfo;

	/**
	 * 日志类型
	 */
	@JsonProperty("log_type")
	private String logType;

	/**
	 * 预留扩展
	 */
	@JsonProperty("ext_field")
	private String extField;
}
