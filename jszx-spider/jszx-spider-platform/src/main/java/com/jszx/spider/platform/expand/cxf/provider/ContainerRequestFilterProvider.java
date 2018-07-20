package com.jszx.spider.platform.expand.cxf.provider;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;;

/**
 * 
 * cxfRequest过滤器:过滤request请求
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月19日 上午10:33:00
 *
 */

@Provider
public class ContainerRequestFilterProvider implements ContainerRequestFilter {

	public void filter(ContainerRequestContext requestContext) throws IOException {
		//Message message = JAXRSUtils.getCurrentMessage();
	}

}
