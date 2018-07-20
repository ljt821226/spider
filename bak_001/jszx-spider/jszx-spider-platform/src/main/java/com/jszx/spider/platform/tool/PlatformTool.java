package com.jszx.spider.platform.tool;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.spider.platform.exception.ServiceException;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年10月19日 下午12:48:21
 * 
 */

public class PlatformTool {

	private final static Logger logger = LoggerFactory.getLogger(PlatformTool.class);

	private static final String RETURN_MESSAGE = "KASAYA_RETURN_MESSAGE";

	/**
	 * 
	 * 获取HttpServletRequest对象
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月19日 下午12:54:14
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (sra != null) {
			return sra.getRequest();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 设置返回信息
	 * 
	 * @param message
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月19日 下午12:54:34
	 */
	public static void setSuccessMessage(String message) {
		HttpServletRequest hsr = getRequest();
		if (hsr == null) {
			return;
		}
		hsr.setAttribute(RETURN_MESSAGE, message);
	}

	/**
	 * 
	 * 获取返回信息
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月19日 下午12:54:58
	 */
	public static String getSuccessMessage() {
		HttpServletRequest hsr = getRequest();
		if (hsr == null) {
			return null;
		}
		Object obj = hsr.getAttribute(RETURN_MESSAGE);
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 
	 * 加载resources目录下文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年12月4日 下午4:12:04
	 */
	public static InputStream loadResource(String file) throws Exception {
		InputStream is = null;
		try {
			StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
			Class<?> clazz = Class.forName(stacks[2].getClassName());
			is = clazz.getResourceAsStream(file);
			return is;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * 获取最初的ServiceException
	 * 
	 * @param e
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2018年1月3日 下午2:46:28
	 */
	public static ServiceException getServiceException(Throwable e) {
		ServiceException se = null;
		List<Throwable> list = new ArrayList<Throwable>();
		Throwable t = e;
		while (t != null) {
			list.add(t);
			t = t.getCause();
		}
		Throwable item = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			item = list.get(i);
			if (item == null) {
				continue;
			}
			if (item.getClass().equals(ServiceException.class)) {
				se = (ServiceException) item;
				break;
			}
		}
		return se;
	}

}
