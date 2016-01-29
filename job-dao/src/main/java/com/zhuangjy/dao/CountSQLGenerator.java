package com.zhuangjy.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountSQLGenerator {
	private static final Logger logger = LoggerFactory.getLogger(CountSQLGenerator.class);

	public static String getCountSQL(String selectSQL) {
		/*
		 * 不修改原有sql，直接套上count(*)。
		 * 现在数据库能自动识别，如果是count(*)，则子查询的select会不查询具体内容，执行计划与修改sql后相同。
		 * 修改SQL会导致bug（如select中带有子查询时无法判断select结束）
		 */
		//		String uppercaseSql= selectSQL.toUpperCase().trim();
		//		Pattern unionFormat = Pattern.compile("(.+)UNION(.+)");
		String result = "";
		//		if(unionFormat.matcher(uppercaseSql).matches()){
		result = new StringBuilder("select count(*) from (").append(selectSQL.trim()).append(") count_temp__")
				.toString();
		//		}
		//		else{
		//			int firstFromIndex = selectSQL.toUpperCase().indexOf("FROM");
		//			String leftPart = selectSQL.substring(firstFromIndex + 4).trim();
		//			result= new StringBuilder("select count(1) from "+ leftPart).toString();
		//			
		//		}

		logger.trace("Original select SQL: {} to count SQL: {}", new Object[] { selectSQL, result });
		return result;
	}
}
