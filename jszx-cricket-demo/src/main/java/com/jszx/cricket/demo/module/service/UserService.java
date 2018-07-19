package com.jszx.cricket.demo.module.service;



import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.jszx.cricket.demo.module.entity.UserEntity;
import com.jszx.cricket.platform.annotation.EntityParam;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.PageEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;

@Path("/user")
@Produces({"application/json"})
public abstract interface UserService
{
  @Operation(summary="平台监控日志查询", hidden=false, method="GET")
  @Parameters({@io.swagger.v3.oas.annotations.Parameter(name="condition", description="查询条件", required=false, schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=UserEntity.class))})
  @POST
  @Path("/get/query")
  public abstract UserEntity query(@NotNull @EntityParam("condition") UserEntity paramUserEntity)
    throws ServiceException;
  
  @GET
  @Path("/get/list")
  public abstract List<UserEntity> list(@QueryParam("condition") UserEntity paramUserEntity)
    throws ServiceException;
  
  @GET
  @Path("/get/page")
  public abstract List<UserEntity> page(@EntityParam("condition") UserEntity paramUserEntity, @EntityParam("page") PageEntity paramPageEntity)
    throws ServiceException;
}
