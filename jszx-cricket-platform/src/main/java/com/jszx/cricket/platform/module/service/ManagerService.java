package com.jszx.cricket.platform.module.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.BaseEntity;

import io.swagger.v3.oas.annotations.Operation;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月18日 上午11:27:58
 * 
 */

@Path("/manager")
@Produces({ MediaType.APPLICATION_JSON })
public interface ManagerService {

	public final String TAG = "manager";

	public final String CONDITION = "condition";

	@Operation(
		summary = "健康状态查询",
		description = "健康状态查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/manager")
	public String manager() throws ServiceException;

	@Operation(
		summary = "健康状态查询",
		description = "健康状态查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/health")
	public String health() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/info")
	public String info() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/conditions")
	public String conditions() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/configprops")
	public String configprops() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/logfile")
	public String logfile() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/loggers")
	public String loggers() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/loggers/{name}")
	public String selectLoggersByName(@PathParam("name") String name) throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@POST
	@Path("/loggers/{name}")
	public BaseEntity downLoggersByName(@PathParam("name") String name) throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/heapdump")
	public String heapdump() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/metrics")
	public String metrics() throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/metrics/{name}")
	public String metricsByName(@PathParam("name") String name) throws ServiceException;

	@Operation(
		summary = "基本信息查询",
		description = "基本信息查询",
		tags = { TAG },
		method = "get",
		hidden = false
	)
	@GET
	@Path("/httptrace")
	public String httptrace() throws ServiceException;

}
