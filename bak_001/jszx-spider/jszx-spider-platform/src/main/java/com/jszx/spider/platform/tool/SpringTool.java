package com.jszx.spider.platform.tool;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 
 * Spring工具类:提供获取bean的功能
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月11日 下午10:38:04
 *
 */

public class SpringTool {

	private static ApplicationContext applicationContext = null;

	public static void setApplicationContext(ApplicationContext ac) {
		applicationContext = ac;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 
	 * 通过name获取 Bean
	 * 
	 * @param name
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 下午10:39:32
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 
	 * 通过class获取Bean
	 * 
	 * @param clazz
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 下午10:39:51
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 
	 * 通过name,以及Clazz返回指定的Bean
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 下午10:40:01
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}

	/**
	 * @return env
	 */
	public static Environment getEnv() {
		return applicationContext.getEnvironment();
	}

	/**
	 * 
	 * 获取属性
	 * 
	 * @param key
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月10日 下午2:42:41
	 */
	public static String getProperty(String key) {
		return applicationContext.getEnvironment().getProperty(key);
	}

	/**
	 * 
	 * 获取属性并不存在时设置默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2018年1月3日 下午3:11:13
	 */
	public static String getProperty(String key, String defaultValue) {
		return applicationContext.getEnvironment().getProperty(key, defaultValue);
	}

	/**
	 * 
	 * 根据类型获取属性
	 * 
	 * @param key
	 * @param targetType
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月10日 下午2:42:57
	 */
	public static Object getProperty(String key, Class<?> targetType) {
		return applicationContext.getEnvironment().getProperty(key, targetType);
	}

	/**
	 * 
	 * 根据注解获取bean
	 * 
	 * @param annotationType
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 下午5:09:18
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

}
