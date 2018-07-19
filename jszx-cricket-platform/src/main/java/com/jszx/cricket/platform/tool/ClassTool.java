package com.jszx.cricket.platform.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类操作工具类:提供类操作的基本功能
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月8日 下午1:57:54
 * 
 */

public class ClassTool {

	private static final Logger logger = LoggerFactory.getLogger(ClassTool.class);

	/**
	 * 
	 * 根据名称获取属性
	 * 
	 * @param cls
	 * @param fieldName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月8日 下午2:48:51
	 */
	public static Field getFieldByName(Class<?> cls, String fieldName) {
		for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				if (field != null) {
					return field;
				} else {
					continue;
				}
			} catch (NoSuchFieldException e) {
				
			}
		}
		return null;
	}

	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
	}

	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	private static Field getDeclaredField(Object object, String filedName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	private static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 
	 * 判断是否基本类型，基本类型与对应的包装类
	 * 
	 * @param clz
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年12月20日 下午5:43:04
	 */
	public static boolean isWrapClass(Class<?> clz) {
		try {
			return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * 通过反射调用
	 * 
	 * @param value
	 * @param methodName
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年4月28日 下午4:41:01
	 */
	public static Object reflectValue(Object value, String methodName) throws Exception {
		Class<?>[] paramClasses = {};
		Object[] paramValues = {};
		Method m = value.getClass().getMethod(methodName, paramClasses);
		return m.invoke(value, paramValues);
	}
	
	/**
	 * 
	 * 通过反射调用，将入参类型和入参参数 添加到这里来。
	 * 
	 * @param value
	 * @param methodName
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年4月28日 下午4:41:01
	 */
	public static Object reflectValue(Object value, String methodName,Class<?>[] paramClasses,Object[] paramValues) throws Exception {
		Method m = value.getClass().getMethod(methodName, paramClasses);
		return m.invoke(value, paramValues);
	}
	
	public static Class<?> analyzeGeneric(Type genericType) throws ClassNotFoundException {
		Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
		Class<?> entityClass = (Class<?>) params[0];
		return entityClass;
	}

}
