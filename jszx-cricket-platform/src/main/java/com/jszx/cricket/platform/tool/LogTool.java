package com.jszx.cricket.platform.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类:提供日志记录基本方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月9日 下午5:50:58
 * 
 */

public class LogTool {

	private final static int TARGET_INDEX = 3;

	public static void trace(String msg) {
		Logger logger = getLogger();
		if (logger.isTraceEnabled()) {
			logger.trace(msg);
		}
	}

	public static void trace(String msg, Throwable e) {
		Logger logger = getLogger();
		if (logger.isTraceEnabled()) {
			logger.trace(msg, e);
		}
	}

	public static void debug(String msg) {
		Logger logger = getLogger();
		if (logger.isDebugEnabled()) {
			logger.debug(msg);
		}
	}

	public static void debug(String msg, Throwable e) {
		Logger logger = getLogger();
		if (logger.isDebugEnabled()) {
			logger.debug(msg, e);
		}
	}

	public static void info(String msg) {
		Logger logger = getLogger();
		if (logger.isInfoEnabled()) {
			logger.info(msg);
		}
	}

	public static void info(String msg, Throwable e) {
		Logger logger = getLogger();
		if (logger.isInfoEnabled()) {
			logger.info(msg, e);
		}
	}

	public static void warn(String msg) {
		Logger logger = getLogger();
		if (logger.isWarnEnabled()) {
			logger.warn(msg);
		}
	}

	public static void warn(String msg, Throwable e) {
		Logger logger = getLogger();
		if (logger.isWarnEnabled()) {
			logger.warn(msg, e);
		}
	}

	public static void error(String msg) {
		Logger logger = getLogger();
		if (logger.isErrorEnabled()) {
			logger.error(msg);
		}
	}

	public static void error(String msg, Throwable e) {
		Logger logger = getLogger();
		if (logger.isErrorEnabled()) {
			logger.error(msg, e);
		}
	}

	private static Logger getLogger() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		Logger logger = LoggerFactory.getLogger(stacks[TARGET_INDEX].getClassName());
		return logger;
	}

}
