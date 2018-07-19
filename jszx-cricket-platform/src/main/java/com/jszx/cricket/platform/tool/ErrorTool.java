package com.jszx.cricket.platform.tool;

import com.jszx.cricket.platform.code.ErrorCode;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.ErrorEntity;

/**
 * 业务错误信息类:业务错误信息工具类
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月14日 上午10:51:20
 * 
 */

public class ErrorTool {

	/**
	 * 
	 * 为空错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:54:50
	 */
	public static void NULL(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.NULL.value(), msg);
	}

	/**
	 * 
	 * 类型错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:55:01
	 */
	public static void TYPE(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.TYPE.value(), msg);
	}

	/**
	 * 
	 * 格式错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:55:11
	 */
	public static void FORMAT(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.FORMAT.value(), msg);
	}

	/**
	 * 
	 * 长度错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:55:24
	 */
	public static void LENGTH(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.LENGTH.value(), msg);
	}

	/**
	 * 
	 * 重复错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:55:35
	 */
	public static void REPEAT(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.REPEAT.value(), msg);
	}

	/**
	 * 
	 * 认证错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:55:47
	 */
	public static void AUTH(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.AUTH.value(), msg);
	}

	/**
	 * 
	 * 时限错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:56:00
	 */
	public static void LIMIT(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.LIMIT.value(), msg);
	}

	/**
	 * 
	 * 未发现错误
	 * 
	 * @param msg
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月14日 上午10:56:24
	 */
	public static void NOTFOUND(String msg) throws ServiceException {
		throw new ServiceException(ErrorCode.CODE.NOTFOUND.value(), msg);
	}

	/**
	 * 
	 * 日志收集方法
	 * 
	 * @param logEntity
	 * @throws ServiceException
	 * @author lv_juntao@uisftech.com
	 * @date 2018年1月3日 上午11:33:12
	 */
	public static void COLLECT(ErrorEntity errorEntity) throws ServiceException {
		throw new ServiceException(errorEntity);
	}

}
