/**
 * @(#)ReflectionUtil.java 2011-9-6
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
package com.zhuangjy.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 反射的Util函数集合.
 * 
 * 提供访问私有变量,获取泛型类型Class,提取集合中元素的属性,转换字符串到对象等Util函数.
 * 
 * @author 刘圳
 * @version 1.1 2011-9-2
 * @since 1.0
 */
public abstract class ReflectionUtil {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	static {
		DateConverter dc = new DateConverter(null);
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName
					+ "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		}
		catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接读取对象属性值, 经过getter函数.
	 */
	public static String getProperty(final Object object, final String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return BeanUtils.getProperty(object, propertyName);
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName
					+ "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		}
		catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 直接设置对象属性值, 经过setter函数.
	 */
	public static void setProperty(final Object object, final String fieldName, final Object value) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.setProperty(object, fieldName, value);
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName,
			final Class<?>[] parameterTypes, final Object[] parameters) throws InvocationTargetException, IllegalAccessException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null)
			throw new IllegalArgumentException("Could not find method [" + methodName
					+ "] on target [" + object + "]");

		method.setAccessible(true);
        return method.invoke(object, parameters);
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		ValidateUtil.notNull(object, "object不能为空");
		ValidateUtil.notEmpty(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			}
			catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 获取对象的DeclaredFields.
	 * 
	 * @param object
	 * @return
	 */
	public static Field[] getDeclaredFields(final Object object) {
		ValidateUtil.notNull(object, "object不能为空");
		return object.getClass().getDeclaredFields();
	}

	/**
	 * 强行设置Field可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredMethod.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) {
		ValidateUtil.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			}
			catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射,获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao
	 * extends
	 * HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class<?> clazz) {
		return (Class<T>) getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class<?>) params[index];
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 */
	public static List<?> convertElementPropertyToList(final Collection<?> collection,
			final String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		List<Object> list = new ArrayList<Object>();

        for (Object obj : collection) {
            list.add(PropertyUtils.getProperty(obj, propertyName));
        }

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */
	public static String convertElementPropertyToString(final Collection<?> collection,
			final String propertyName, final String separator) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		List<?> list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 利用反射实现对象的toString()方法，列出对象名称及每个属性的值。通常用于logger debug。
	 * 
	 * @param obj
	 *            需要打印的对象
	 * @return toString result
	 * @since 2.0
	 */
	public static String objectToString(Object obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * @since 2.0.29
	 */
	public static Object getPropertyValue(Object bean, String name) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return PropertyUtils.getProperty(bean, name);
	}

    public static List<Field> getFieldsByAnnotation(Class<? extends Annotation> annotationType, Class clz) {
        List<Field> list = new ArrayList<>();
        getFieldsByAnnotation(list, annotationType, clz);
        return list;
    }

    private static void getFieldsByAnnotation(List<Field> list, Class<? extends Annotation> annotation, Class clz) {
        if (clz.equals(Object.class)) {
            return;
        }
        Field[] fields = clz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return ;
        }
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getAnnotation(annotation) != null) {
                list.add(f);
            }
        }
    }
}
