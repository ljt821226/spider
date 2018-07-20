package com.jszx.spider.platform.tool;

/**
 * 系统信息工具类:获取系统信息
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月10日 下午11:02:50
 * 
 */

public class SystemTool {

	private final static int MB = 1024 * 1024;;

	/**
	 * 
	 * 获取JVM已用内存，单位为MB
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月18日 下午1:50:33
	 */
	public static long getJvmTotalMemory() {
		return Runtime.getRuntime().totalMemory() / MB;
	}

	/**
	 * 
	 * 获取JVM可用内存，单位为MB
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月18日 下午1:51:10
	 */
	public static long getJvmFreeMemory() {
		return Runtime.getRuntime().freeMemory() / MB;
	}

	/**
	 * 
	 * 获取JVM最大内存，单位为MB
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月18日 下午1:51:31
	 */
	public static long getJvmMaxMemory() {
		return Runtime.getRuntime().maxMemory() / MB;
	}

}
