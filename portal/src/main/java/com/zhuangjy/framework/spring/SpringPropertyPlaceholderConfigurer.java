/**
 * @(#)PortalPropertyPlaceholderConfigurer.java 2011-9-7
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

import com.zhuangjy.dao.CryptoUtil;
import com.zhuangjy.framework.config.PropsUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 替代 Spring 的 {@link PropertyPlaceholderConfigurer}，用于开放配置文件的参数访问接口，
 * 不再局限于spring context中访问，也可以在其他任意地方得到这些值（如非spring bean，tag，jsp）。
 * 
 * @author 刘圳
 * @version 1.0 2011-9-7
 * @since 2.0
 */
public class SpringPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	/**
	 * 如果值进行加密, 则在对应的值前面添加该字符串
	 */
	public static final String SECRET_PREFIX = "secret:";

	private Map<String, String> resolvedProps;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		/*
		 * 覆盖父类的方法，截取解析到的参数值，放入自定义的map中，供外部调用。
		 */

		// build the properties we need
		Map<String, String> tempProps = new HashMap<String, String>(props.size());
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(DEFAULT_PLACEHOLDER_PREFIX, DEFAULT_PLACEHOLDER_SUFFIX);
		for (Object keyObj : props.keySet()) {
			String key = String.valueOf(keyObj);
			String value = props.getProperty(key);
			if (StringUtils.hasText(value) && value.startsWith(SECRET_PREFIX)) {
                try {
                    String tmp = CryptoUtil.decryptApi(value.substring(SECRET_PREFIX.length()));
                    value = tmp;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
			value = helper.replacePlaceholders(value, props);
			tempProps.put(key, value);
			props.setProperty(key, value);
		}
		// 这个map只读
		this.resolvedProps = Collections.unmodifiableMap(tempProps);
        PropsUtil.setProperties(resolvedProps);

		// 必须放在后面执行, 否则在xml配置属性的时候会不可用
		super.processProperties(beanFactoryToProcess, props);
	}

	/**
	 * @return spring读取的配置参数Map，该map只读。
	 */
	public Map<String, String> getResolvedProps() {
		return resolvedProps;
	}
}
