package com.jszx.cricket.platform.module.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.stereotype.Service;

import com.jszx.cricket.platform.annotation.EntityParam;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.BaseEntity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * 
 * kasaya平台监控日志:[提供日志的查询和下载功能]
 * 
 * @version   1.0    
 * @author   xiao_bin@uisftech.com
 * @date 2018年2月5日 上午9:58:19
 *
 */
@Path("/platform/log")
@Produces({ MediaType.APPLICATION_JSON })
public interface LogService {
	
	@Operation(
			summary = "平台监控日志查询", 
			hidden = false, 
			method = "GET")
	@Parameters({
		@Parameter(
				name = "condition", 
				description = "查询条件", 
				required = true)
	})
	@GET
	@Path("/selection/list")
	public List<BaseEntity> list(@EntityParam("condition") BaseEntity entity) throws ServiceException;

	
	@GET
	@Path("/download")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void download(@Parameter(hidden = true) @EntityParam("condition") BaseEntity entity, @Context MessageContext context) throws ServiceException;

}
