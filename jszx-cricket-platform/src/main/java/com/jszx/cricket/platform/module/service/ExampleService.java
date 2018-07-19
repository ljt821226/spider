package com.jszx.cricket.platform.module.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jszx.cricket.platform.annotation.EntityParam;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.ExampleEntity;
import com.jszx.cricket.platform.module.entity.PageEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 示例服务类:提供基本操作接口示例
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月18日 上午11:43:54
 * 
 */

@Path("/example")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface ExampleService {

	/**
	 * 
	 * 单条查询
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月19日 下午5:49:39
	 */
	@Operation(
		summary = "单条查询",
		description = "单条查询",
		tags = { "example" },
		method = "get",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "查询条件"
	)
	@GET
	@Path("/single")
	public ExampleEntity select(@NotNull @EntityParam("condition") ExampleEntity entity) throws ServiceException;

	/**
	 * 
	 * 批量查询
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午10:04:36
	 */
	@Operation(
		summary = "批量查询",
		description = "批量查询",
		tags = { "example" },
		method = "get",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "查询条件"
	)
	@GET
	@Path("/batch")
	public ExampleEntity[] selectBatch(@EntityParam("condition") ExampleEntity entity) throws ServiceException;

	/**
	 * 
	 * 分页查询
	 * 
	 * @param entity
	 * @param page
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午10:05:03
	 */
	@Operation(
		summary = "分页查询",
		description = "分页查询",
		tags = { "example" },
		method = "get",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "查询条件"
	)
	@Parameter(
		name = "page",
		description = "分页条件"
	)
	@GET
	@Path("/page")
	public ExampleEntity[] selectPage(@EntityParam("condition") ExampleEntity entity, @EntityParam("page") PageEntity page) throws ServiceException;

	/**
	 * 
	 * 单条增加
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:55:07
	 */
	@Operation(
		summary = "单条增加",
		description = "单条增加",
		tags = { "example" },
		method = "post",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "增加的数据"
	)
	@POST
	@Path("/single")
	public int insert(@EntityParam("condition") ExampleEntity entity) throws ServiceException;

	/**
	 * 
	 * 批量增加
	 * 
	 * @param entities
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:55:19
	 */
	@Operation(
		summary = "批量增加",
		description = "批量增加",
		tags = { "example" },
		method = "post",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "增加的数据"
	)
	@POST
	@Path("/batch")
	public int insertBatch(@EntityParam("condition") ExampleEntity[] entities) throws ServiceException;

	/**
	 * 
	 * 单条更新
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:55:07
	 */
	@Operation(
		summary = "单条更新",
		description = "单条更新",
		tags = { "example" },
		method = "put",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "更新的数据"
	)
	@PUT
	@Path("/single")
	public int update(@EntityParam("condition") ExampleEntity entity) throws ServiceException;

	/**
	 * 
	 * 批量更新
	 * 
	 * @param entities
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:55:19
	 */
	@Operation(
		summary = "批量更新",
		description = "批量更新",
		tags = { "example" },
		method = "put",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "更新的数据"
	)
	@PUT
	@Path("/batch")
	public int updateBatch(@EntityParam("condition") ExampleEntity[] entities) throws ServiceException;

	/**
	 * 
	 * 单条删除
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:55:07
	 */
	@Operation(
		summary = "单条删除",
		description = "单条删除",
		tags = { "example" },
		method = "delete",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "删除的数据"
	)
	@DELETE
	@Path("/single")
	public int delete(@EntityParam("condition") ExampleEntity entity) throws ServiceException;

	/**
	 * 
	 * 批量删除
	 * 
	 * @param entities
	 * @return
	 * @throws ServiceException
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:55:19
	 */
	@Operation(
		summary = "批量删除",
		description = "批量删除",
		tags = { "example" },
		method = "delete",
		hidden = false
	)
	@Parameter(
		name = "condition",
		description = "删除的数据"
	)
	@DELETE
	@Path("/batch")
	public int deleteBatch(@EntityParam("condition") ExampleEntity[] entities) throws ServiceException;

}
