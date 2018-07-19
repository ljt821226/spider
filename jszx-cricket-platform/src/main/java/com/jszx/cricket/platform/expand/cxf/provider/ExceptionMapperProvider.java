package com.jszx.cricket.platform.expand.cxf.provider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.cricket.platform.code.ReturnCode;
import com.jszx.cricket.platform.code.TransactionCode;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.ResponseEntity;
import com.jszx.cricket.platform.tool.PlatformTool;
import com.jszx.cricket.platform.tool.SpringTool;
import com.jszx.cricket.platform.tool.StringTool;

/**
 * 
 * 异常控制类:提供异常的统一记录和输出
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月19日 上午10:30:19
 *
 */

@Provider
public class ExceptionMapperProvider implements ExceptionMapper<ServiceException> {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionMapperProvider.class);

	public Response toResponse(ServiceException e) {
		ServiceException ex = PlatformTool.getServiceException(e);
		if (ex.getCode() != -1) {
			logger.error(ex.getMessage());
		} else {
			logger.error(ex.getMessage(), ex);
		}
		return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE)
				.entity(buildReturn(ex)).build();
	}

	/**
	 * 
	 * 构建返回信息
	 * 
	 * @param e
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月9日 下午12:14:59
	 */
	private String buildReturn(ServiceException e) {
		try {
			ResponseEntity responseEntity = new ResponseEntity();
			responseEntity.setData(null);
			responseEntity.setCode(e.getCode());
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (attributes != null) {
				String path = attributes.getRequest().getRequestURI();
				if (path.lastIndexOf(TransactionCode.ACTION.PREPARE.value()) == 0) {
					responseEntity.set(TransactionCode.MARK.LOCALHOST.code(),
							SpringTool.getProperty(TransactionCode.MARK.LOCALHOST.value()));
				}
			}
			if (StringTool.isEmpty(e.getMessage())) {
				responseEntity.setMessage(ReturnCode.MESSAGE.FAILURE.value());
			} else {
				responseEntity.setMessage(e.getMessage());
			}
			return responseEntity.toJson();
		} catch (Exception e1) {
			logger.error("构建返回信息失败，请检查。。。", e1);
			return null;
		}
	}
}