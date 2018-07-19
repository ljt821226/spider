package com.jszx.cricket.platform.module.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jszx.cricket.platform.annotation.EntityParam;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.TransactionRequestEntity;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * 事务服务类:提供事务准备、提交和回滚方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年9月25日 上午11:05:10
 * 
 */

@Path("/transaction")
@Produces({ MediaType.APPLICATION_JSON })
public interface TransactionService {

	/**
	 * 
	 * 准备
	 * 
	 * @param questEntity
	 * @return
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月11日 下午3:13:34
	 */
	@Operation(
		summary = "事务预提交",
		description = "事务预提交",
		tags = { "transaction" },
		method = "POST",
		hidden = true
	)
	@POST
	@Path("/prepare")
	public Object prepare(@EntityParam("condition") TransactionRequestEntity questEntity) throws ServiceException;

	/**
	 * 
	 * 提交
	 * 
	 * @param questEntity
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月11日 下午3:13:42
	 */
	@Operation(
		summary = "事务提交",
		description = "事务提交",
		tags = { "transaction" },
		method = "POST",
		hidden = true
	)
	@POST
	@Path("/submit")
	public void submit(@EntityParam("condition") TransactionRequestEntity questEntity) throws ServiceException;

	/**
	 * 
	 * 回滚
	 * 
	 * @param questEntity
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月11日 下午3:13:51
	 */
	@Operation(
		summary = "事务回滚",
		description = "事务回滚",
		tags = { "transaction" },
		method = "POST",
		hidden = true
	)
	@POST
	@Path("/rollback")
	public void rollback(@EntityParam("condition") TransactionRequestEntity questEntity) throws ServiceException;

}
