/**
 * @(#)SpringContextHolder.java 2011-9-14
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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicaitonContext.
 */
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBean(requiredType);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}

	public static <T> T getBean(String beanName, Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBean(beanName, requiredType);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		checkApplicationContext();
		return applicationContext.getBeansOfType(type);
	}

	public static Map<String, Object> getBeansWithAnnotation(
			Class<? extends Annotation> annotationType) {
		checkApplicationContext();
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicationContext未注入");
		}
	}
}
