package com.jszx.cricket.platform.expand.servlet.wrapper;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Response装饰器:增加平台处理能力
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年3月30日 下午10:16:37
 * 
 */

public class ServletResponseWrapper extends HttpServletResponseWrapper {

	/**
	 * @param response
	 */
	public ServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

}
