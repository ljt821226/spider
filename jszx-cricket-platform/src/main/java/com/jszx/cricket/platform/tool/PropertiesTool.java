package com.jszx.cricket.platform.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件工具类:提供读取classpath下config中的属性文件
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月20日 上午9:56:18
 * 
 */

public class PropertiesTool {

	private final static Logger logger = LoggerFactory.getLogger(PropertiesTool.class);

	private static String CONFIG = "/config/";

	/**
	 * 
	 * 读取属性文件
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午10:20:27
	 */
	public static Properties load(String name) throws Exception {
		InputStream is = null;
		try {
			StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
			Class<?> clazz = Class.forName(stacks[2].getClassName());
			logger.info(clazz.getResource("").getPath());
			is = clazz.getResourceAsStream(CONFIG + name);
			Properties p = new Properties();
			p.load(is);
			return p;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 
	 * [方法描述]
	 * 
	 * @param name
	 * @param flag：是否默认目录
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年7月26日 上午10:51:11
	 */
	public static Properties load(String file, boolean flag) throws Exception {
		if (!flag) {
			CONFIG = "/";
		}
		return load(file);
	}
}
