package com.zhuangjy.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;

public final class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

    public static Object getBean(Class<?> clazz) {
        return context.getBean(clazz);
    }

	public static String getMessage(String key) {
		return context.getMessage(key, null, Locale.getDefault());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

    public ApplicationContext getContext() {
        return context;
    }

}
