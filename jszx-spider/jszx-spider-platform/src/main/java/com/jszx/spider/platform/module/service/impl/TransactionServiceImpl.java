package com.jszx.spider.platform.module.service.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.spider.platform.annotation.EntityParam;
import com.jszx.spider.platform.code.ReturnCode;
import com.jszx.spider.platform.code.TransactionCode;
import com.jszx.spider.platform.exception.ServiceException;
import com.jszx.spider.platform.module.entity.BaseEntity;
import com.jszx.spider.platform.module.entity.TransactionRequestEntity;
import com.jszx.spider.platform.module.service.TransactionService;
import com.jszx.spider.platform.tool.ClassTool;
import com.jszx.spider.platform.tool.JsonTool;
import com.jszx.spider.platform.tool.SpringTool;
import com.jszx.spider.platform.tool.TransactionTool;

/**
 * 
 * 事务处理服务:提供事务准备、提交、回滚
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年9月25日 下午10:55:09
 * 
 */

@Service
public class TransactionServiceImpl implements TransactionService {

	public Object prepare(TransactionRequestEntity requestEntity) throws ServiceException {
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			attributes.getRequest().setAttribute(TransactionCode.MARK.TOKEN.value(), requestEntity.getTransactionId());
			TransactionTool.put(requestEntity);
			return invokeService(requestEntity);
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	public void submit(TransactionRequestEntity requestEntity) throws ServiceException {
		try {
			if (TransactionTool.get(requestEntity.getTransactionId()) != null) {
				TransactionTool.submit(requestEntity.getTransactionId());
			}
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	public void rollback(TransactionRequestEntity requestEntity) throws ServiceException {
		try {
			if (TransactionTool.get(requestEntity.getTransactionId()) != null) {
				TransactionTool.rollback(requestEntity.getTransactionId());
			}
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/**
	 * 
	 * 调用服务
	 * 
	 * @param requestEntity
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @date 2017年9月30日 下午2:02:16
	 */
	private Object invokeService(TransactionRequestEntity requestEntity) throws Exception {
		Object service = SpringTool.getBean(requestEntity.getService());
		Class<?> serviceClass = getServiceClass(service);
		Method method = getMethod(serviceClass, requestEntity.getMethod());
		Class<?>[] paramTypes = method.getParameterTypes();
		String[] paramNames = getMethodParamNames(serviceClass, method);
		Object[] paramValues = getMethodParamValues(method, paramNames, paramTypes, requestEntity.getParameter());
		return ReflectionUtils.invokeMethod(method, service, paramValues);
	}

	/**
	 * 
	 * 获取调用方法
	 * 
	 * @param serviceClass
	 * @param methodName
	 * @return
	 * @throws ClassNotFoundException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月16日 下午12:14:27
	 */
	private Method getMethod(Class<?> serviceClass, String methodName) throws ClassNotFoundException {
		Method[] methods = serviceClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 
	 * 获取方法参数值
	 * 
	 * @param method
	 * @param paramNames
	 * @param paramTypes
	 * @param data
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月16日 下午12:14:16
	 */
	private Object[] getMethodParamValues(Method method, String[] paramNames, Class<?>[] paramTypes, BaseEntity data)
			throws Exception {
		Object[] paramValues = new Object[paramNames.length];
		int paramLength = paramNames.length;
		for (int i = 0; i < paramLength; i++) {
			String paramName = paramNames[i];
			Parameter parameter = method.getParameters()[i];
			String value = JsonTool.toJson(data.get(paramName));
			if (paramTypes[i].equals(List.class)) {
				paramValues[i] = JsonTool.toList(value, ClassTool.analyzeGeneric(parameter.getParameterizedType()));
			} else {
				paramValues[i] = JsonTool.toEntity(value, paramTypes[i]);
			}
		}
		return paramValues;
	}

	/**
	 * 
	 * 获取service类
	 * 
	 * @param service
	 * @return
	 * @throws ClassNotFoundException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月16日 下午12:13:51
	 */
	private Class<?> getServiceClass(Object service) throws ClassNotFoundException {
		String className = service.getClass().getName();
		className = className.substring(0, className.indexOf("$$"));
		return Class.forName(className);
	}

	/**
	 * 
	 * 获取方法参数名称
	 * 
	 * @param service
	 * @param method
	 * @return
	 * @throws ClassNotFoundException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月30日 下午2:00:26
	 */
	public String[] getMethodParamNames(Class<?> serviceClass, Method method) throws Exception {
		Method[] methods = serviceClass.getInterfaces()[0].getMethods();
		Parameter[] parameters = null;
		for (Method m : methods) {
			if (method.getName().equals(m.getName())) {
				parameters = m.getParameters();
				break;
			}
		}
		String[] names = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			EntityParam cp = parameters[i].getAnnotation(EntityParam.class);
			names[i] = cp.value();
		}
		return names;
	}

}
