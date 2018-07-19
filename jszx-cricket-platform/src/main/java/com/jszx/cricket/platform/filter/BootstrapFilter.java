package com.jszx.cricket.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jszx.cricket.platform.expand.servlet.wrapper.ServletResponseWrapper;
import com.jszx.cricket.platform.expand.servlet.wrapper.ServletRequestWrapper;

//@Order(1)
//@Component("bootstrapFilter")
public class BootstrapFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		corsHandler(httpResponse);
		if (!httpRequest.getMethod().equalsIgnoreCase("options")) {
			ServletRequestWrapper requestWrapper = new ServletRequestWrapper(httpRequest);
			ServletResponseWrapper reponseWrapper = new ServletResponseWrapper(httpResponse);
			chain.doFilter(requestWrapper, reponseWrapper);
		}
	}

	/**
	 * 
	 * 跨域问题处理
	 * 
	 * @param httpResponse
	 * @author lv_juntao@uisftech.com
	 * @date 2017年3月30日 下午10:13:43
	 */
	private void corsHandler(HttpServletResponse httpResponse) {
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Headers",
				"User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken,X-Requested-With,Accept,auth-ticket,auth-token");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		httpResponse.setHeader("Access-Control-Max-Age", "1209600");
		httpResponse.setHeader("Access-Control-Expose-Headers", "accesstoken");
		httpResponse.setHeader("Access-Control-Request-Headers", "accesstoken");
		httpResponse.setHeader("Expires", "-1");
		httpResponse.setHeader("Cache-Control", "no-cache");
		httpResponse.setHeader("pragma", "no-cache");
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}

}