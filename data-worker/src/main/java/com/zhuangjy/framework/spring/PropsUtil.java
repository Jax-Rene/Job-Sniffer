/**
 * @(#)PropsUtil.java 2011-9-7
 * 
 * Copyright 2000-2011 by ChinanetCenter Corporation.
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

package com.zhuangjy.framework.spring;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取配置文件的值，供Java调用。值从PortalPropertyPlaceholderConfigurer注入。
 */
public class PropsUtil {

	private static Map<String, String> properties = null;

	/**
	 * CALL'ed by PortalPropertyPlaceholderConfigurer
	 * @param p
	 */
	public static void setProperties(Map<String, String> p) {
		// 非空的话，表示已设置过，不修改。
		if (properties == null) {
			PropsUtil.properties = p;
		}
	}

	public static String get(String key) {
		checkProperty();
		return properties.get(key);
	}

	public static String get(String key, String defaultValue) {
		String val = get(key);
		return (val == null) ? defaultValue : val;
	}

	public static Map<String, String> getMap(String prefix, boolean removePrefix) {
		checkProperty();
		Map<String, String> source = properties;
		Map<String, String> result = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : source.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith(prefix)) {
				String value = entry.getValue();
				if (removePrefix) {
					key = key.substring(prefix.length());
				}
				result.put(key, value);
			}
		}
		return result;
	}

	private static void checkProperty() {
		if (properties == null) {
			throw new RuntimeException("property is null,not inject?");
		}
	}

}
