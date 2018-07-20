package com.jszx.spider.platform.module.service.impl;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.jszx.spider.platform.code.ReturnCode;
import com.jszx.spider.platform.exception.ServiceException;
import com.jszx.spider.platform.module.entity.BaseEntity;
import com.jszx.spider.platform.module.service.ManagerService;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月23日 下午2:43:15
 * 
 */

@Service("com.jszx.spider.platform.service.manager")
public class ManagerServiceImpl implements ManagerService {

	private final String MANAGER_PATH = "http://localhost:8101/manager";

	public ManagerServiceImpl() {
	}

	@Override
	public String manager() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.jszx.spider.platform.module.service.ManagerService#health()
	 */
	@Override
	public String health() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/health").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.jszx.spider.platform.module.service.ManagerService#info()
	 */
	@Override
	public String info() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/info").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.jszx.spider.platform.module.service.ManagerService#conditions()
	 */
	@Override
	public String conditions() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/conditions").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.jszx.spider.platform.module.service.ManagerService#configprops()
	 */
	@Override
	public String configprops() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/configprops").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.jszx.spider.platform.module.service.ManagerService#logfile()
	 */
	@Override
	public String logfile() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/logfile").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.jszx.spider.platform.module.service.ManagerService#loggers()
	 */
	@Override
	public String loggers() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/loggers").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.jszx.spider.platform.module.service.ManagerService#selectLoggersByName(java.
	 * lang.String)
	 */
	@Override
	public String selectLoggersByName(String name) throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/loggers/" + name).accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public BaseEntity downLoggersByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String heapdump() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/heapdump").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public String metrics() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/metrics").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public String metricsByName(String name) throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/metrics/" + name).accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	@Override
	public String httptrace() throws ServiceException {
		try {
			WebClient client = WebClient.create(MANAGER_PATH);
			Response response = client.path("/httptrace").accept(MediaType.APPLICATION_JSON_VALUE).get();
			return response.readEntity(String.class);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

}
