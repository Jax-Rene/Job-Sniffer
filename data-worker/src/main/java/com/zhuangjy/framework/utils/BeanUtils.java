package com.zhuangjy.framework.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * bean属性拷贝帮助类
 * 
 * @author zengjie
 * @version [版本号, 2012-11-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /**
     * 对一个bean进行深度复制，所有的属性节点全部会被复制
     * 
     * @param source
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T deepCopyBean(T source)
    {
        if (source == null)
        {
            return null;
        }
        try
        {
            if (source instanceof Collection)
            {
                return (T)deepCopyCollection((Collection)source);
            }
            if (source.getClass().isArray())
            {
                return (T)deepCopyArray(source);
            }
            if (source instanceof Map)
            {
                return (T)deepCopyMap((Map)source);
            }
            if (source instanceof Date)
            {
                return (T)new Date(((Date)source).getTime());
            }
            if (source instanceof Timestamp)
            {
                return (T)new Timestamp(((Timestamp)source).getTime());
            }
            // 基本类型直接返回原值
            if (source.getClass().isPrimitive() || source instanceof String || source instanceof Boolean
                || Number.class.isAssignableFrom(source.getClass()) || source instanceof Enum)
            {
                return source;
            }
            if (source instanceof StringBuilder)
            {
                return (T)new StringBuilder(source.toString());
            }
            if (source instanceof StringBuffer)
            {
                return (T)new StringBuffer(source.toString());
            }
            Object dest = source.getClass().newInstance();
            BeanUtilsBean bean = BeanUtilsBean.getInstance();
            PropertyDescriptor[] origDescriptors = bean.getPropertyUtils().getPropertyDescriptors(source);
            for (int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if ("class".equals(name))
                {
                    continue;
                }
                
                if (bean.getPropertyUtils().isReadable(source, name) && bean.getPropertyUtils().isWriteable(dest, name))
                {
                    try
                    {
                        Object value = deepCopyBean(bean.getPropertyUtils().getSimpleProperty(source, name));
                        bean.getPropertyUtils().setSimpleProperty(dest, name, value);
                    }
                    catch (NoSuchMethodException e)
                    {
                    }
                }
            }
            return (T)dest;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Collection deepCopyCollection(Collection source)
        throws InstantiationException, IllegalAccessException
    {
        Collection dest = source.getClass().newInstance();
        for (Object o : source)
        {
            dest.add(deepCopyBean(o));
        }
        return dest;
    }
    
    private static Object deepCopyArray(Object source)
        throws InstantiationException, IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException
    {
        int length = Array.getLength(source);
        Object dest = Array.newInstance(source.getClass().getComponentType(), length);
        if (length == 0)
        {
            return dest;
        }
        for (int i = 0; i < length; i++)
        {
            Array.set(dest, i, deepCopyBean(Array.get(source, i)));
        }
        return dest;
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Map deepCopyMap(Map source)
        throws InstantiationException, IllegalAccessException
    {
        Map dest = source.getClass().newInstance();
        for (Object o : source.entrySet())
        {
            Entry e = (Entry)o;
            dest.put(deepCopyBean(e.getKey()), deepCopyBean(e.getValue()));
        }
        return dest;
    }
    
    

	public static void copyPropertiesToString(Object source, Object target)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, String.valueOf(value));
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
}
