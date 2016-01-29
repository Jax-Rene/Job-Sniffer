/**
 * @(#)CountStatementUtils.java 2010-4-3
 * 
 * Copyright (c) 2000-2010 by ChinanetCenter Corporation.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 * 
 */

package com.zhuangjy.dao;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.AutoResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.ExecuteListener;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.mapping.statement.SelectStatement;
import com.ibatis.sqlmap.engine.scope.ErrorContext;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从select生成select count(*)，用于分页时获取记录总数
 * 
 * @author 刘圳
 * @version 1.1 2011-10-10
 * @since 1.0
 */
public class CountStatementUtil {

	private static final Logger logger = LoggerFactory.getLogger(CountStatementUtil.class);

	private static List<String> idCache = new ArrayList<String>();

	/**
	 * 生成statement ID，并添加countStatment。
	 */
	public static String getCountStatementId(String selectStatementId,
			SqlMapExecutorDelegate delegate) {
		String countId = new StringBuilder(selectStatementId).append("__count__").toString();
		// 第一次使用,先转换添加statement
		if (!idCache.contains(countId)) {
			synchronized (idCache) {
				if (!idCache.contains(countId)) {
					logger.debug("add count statement: {}", countId);
					delegate.addMappedStatement(createCountStatement(delegate
							.getMappedStatement(selectStatementId), countId));
					idCache.add(countId);
				}
			}
		}
		return countId;
	}

	/**
	 * 由select生成count
	 */
	private static MappedStatement createCountStatement(MappedStatement mappedStatement,
			String countId) {
		if (mappedStatement instanceof SelectStatement) {
			return new CountStatement((SelectStatement) mappedStatement, countId);
		}
		// not SelectStatement
		throw new RuntimeException("not a select statement:" + mappedStatement.getId());
	}

	public static class CountStatement extends SelectStatement {

		public CountStatement(SelectStatement selectStatement, String countId) {
			super();
			setId(countId);
			setResultSetType(selectStatement.getResultSetType());
			setFetchSize(1);
			setParameterMap(selectStatement.getParameterMap());
			setParameterClass(selectStatement.getParameterClass());
			setSql(selectStatement.getSql());
			setResource(selectStatement.getResource());
			setSqlMapClient(selectStatement.getSqlMapClient());
			setTimeout(selectStatement.getTimeout());
			List<?> executeListeners = (List<?>) ReflectionUtil.getFieldValue(selectStatement,
					"executeListeners");
			if (executeListeners != null) {
				for (Object listener : executeListeners) {
					addExecuteListener((ExecuteListener) listener);
				}
			}
			ResultMap resultMap = new AutoResultMap(((SqlMapClientImpl) getSqlMapClient())
					.getDelegate(), false);
			resultMap.setId(getId() + "-AutoResultMap");
			resultMap.setResultClass(Long.class);
			resultMap.setResource(getResource());
			setResultMap(resultMap);
		}

		@Override
		protected void executeQueryWithCallback(StatementScope statementScope, Connection conn,
				Object parameterObject, Object resultObject, RowHandler rowHandler,
				int skipResults, int maxResults) throws SQLException {
			ErrorContext errorContext = statementScope.getErrorContext();
			errorContext.setActivity("preparing the mapped statement for execution");
			errorContext.setObjectId(this.getId());
			errorContext.setResource(this.getResource());

			try {
				parameterObject = validateParameter(parameterObject);

				Sql sql = getSql();

				errorContext.setMoreInfo("Check the parameter map.");
				ParameterMap parameterMap = sql.getParameterMap(statementScope, parameterObject);

				errorContext.setMoreInfo("Check the result map.");
				ResultMap resultMap = sql.getResultMap(statementScope, parameterObject);

				statementScope.setResultMap(resultMap);
				statementScope.setParameterMap(parameterMap);

				errorContext.setMoreInfo("Check the parameter map.");
				Object[] parameters = parameterMap.getParameterObjectValues(statementScope,
						parameterObject);

				errorContext.setMoreInfo("Check the SQL statement.");
				// 替换sql中的select部分
				//String sqlString = sql.getSql(statementScope, parameterObject);
				String sqlString = getSqlString(statementScope, parameterObject, sql);

				errorContext.setActivity("executing mapped statement");
				errorContext.setMoreInfo("Check the SQL statement or the result map.");
				RowHandlerCallback callback = new RowHandlerCallback(resultMap, resultObject,
						rowHandler);
				sqlExecuteQuery(statementScope, conn, sqlString, parameters, skipResults,
						maxResults, callback);

				errorContext.setMoreInfo("Check the output parameters.");
				if (parameterObject != null) {
					postProcessParameterObject(statementScope, parameterObject, parameters);
				}

				errorContext.reset();
				sql.cleanup(statementScope);
				notifyListeners();
			}
			catch (SQLException e) {
				errorContext.setCause(e);
				throw new NestedSQLException(errorContext.toString(), e.getSQLState(), e
						.getErrorCode(), e);
			}
			catch (Exception e) {
				errorContext.setCause(e);
				throw new NestedSQLException(errorContext.toString(), e);
			}
		}

		/**
		 * 生产count SQL
		 */
		private String getSqlString(StatementScope statementScope, Object parameterObject, Sql sql) {
			String sqlString = sql.getSql(statementScope, parameterObject);
			String result = CountSQLGenerator.getCountSQL(sqlString);
			logger.debug("count statement sql: {}", result);
			return result;
		}

	}
}