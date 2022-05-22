/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * Mybaits-SQL执行拦截器，可用于打印日志，打印SQL执行原始语句，拼接参数后的语句
 *
 * @author Phray
 * @Version SqlInterceptor.java, v 0.1 2022-05-22 21:34 Sccc Exp $
 */
@Slf4j
@Component
@Intercepts({
		@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
		@Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
		@Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SqlInterceptor implements Interceptor {

	/**
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return null;
	}

	/**
	 * @param o
	 * @return
	 */
	@Override
	public Object plugin(Object o) {
		return null;
	}

	/**
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {

	}
}
