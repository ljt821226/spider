package com.jszx.cricket.platform.expand.cxf.provider;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.jszx.cricket.platform.code.SystemCode;

/**
 * 
 * rest拦截器:解决跨域问题和返回为空问题
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月19日 上午10:33:32
 *
 */

@Provider
public class ContainerResponseFilterProvider implements ContainerResponseFilter {

	// swagger请求
	private final String SWAGGER_PATH = "openapi.json";

	public void filter(ContainerRequestContext inContext, ContainerResponseContext outContext) {
		MultivaluedMap<String, Object> headers = outContext.getHeaders();

		String path = inContext.getUriInfo().getPath();

		if (path.lastIndexOf(SWAGGER_PATH) == -1) {
			/**
			 * 字符串时候
			 */

			Class<?> clazz = outContext.getEntityClass();
			if (outContext.getStatus() == Response.Status.OK.getStatusCode() && clazz != null
					&& clazz.equals(String.class)) {
				headers.add("content-type", "application/json");
				headers.add("x-application-context", "application");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(SystemCode.COMMON.STRING.value(), outContext.getEntity());
				outContext.setEntity(map);
			}

			/**
			 * 为空时候
			 */
			if (outContext.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
				headers.add("content-type", "application/json");
				headers.add("x-application-context", "application");
				outContext.setStatus(Response.Status.OK.getStatusCode());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(SystemCode.COMMON.NULL.value(), SystemCode.COMMON.NULL.value());
				outContext.setEntity(map);
			}
		}
	}

}
