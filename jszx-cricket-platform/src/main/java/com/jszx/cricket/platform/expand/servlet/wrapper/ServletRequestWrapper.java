package com.jszx.cricket.platform.expand.servlet.wrapper;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Request包装器:增强平台处理能力
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年3月30日 下午2:57:38
 * 
 */

public class ServletRequestWrapper extends HttpServletRequestWrapper {

	private final String FILE_SIGN_TYPE = "application/file";
	
	private final String FILE_REAL_TYPE = "multipart/form-data;charset=utf-8";

	public ServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getContentType() {
		String contentType = getHttpServletRequest().getContentType();
		if (contentType != null && contentType.indexOf(FILE_SIGN_TYPE) > -1) {
			return FILE_REAL_TYPE;
		}
		return contentType;
	}

	public String getHeader(String name) {
		String head = getHttpServletRequest().getHeader(name);
		if (head != null && head.indexOf(FILE_SIGN_TYPE) > -1) {
			return FILE_REAL_TYPE;
		}
		return head;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		Enumeration<String> head = getHttpServletRequest().getHeaders(name);
		if (null != name && name.equalsIgnoreCase("Content-Type")) {
			if (head.nextElement().indexOf(FILE_SIGN_TYPE) > -1) {
				return new Enumeration<String>() {

					public boolean hasMoreElements() {
						return false;
					}

					public String nextElement() {
						return FILE_REAL_TYPE;
					}
				};
			}
		}
		return super.getHeaders(name);
	}

	private HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) this.getRequest();
	}
}
