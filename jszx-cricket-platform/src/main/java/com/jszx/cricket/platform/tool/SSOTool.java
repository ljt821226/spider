package com.jszx.cricket.platform.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.cricket.platform.code.HttpCode;

/**
 * SSO工具类:提供获取ticket、获取token、校验token、注销ticket
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年11月28日 下午11:40:44
 * 
 */

public class SSOTool {

	private static final Logger logger = LoggerFactory.getLogger(SSOTool.class);

	private final static String ACTION_REG = ".*action=\".*/(.*?)\".*";

	private final static String TICKET_PREFIX = "TGT-";

	private final static String ERROR_MSG = "参数不能为空，请检查...";

	/**
	 * 
	 * 生成ticket
	 * 
	 * @param server
	 * @param username
	 * @param password
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws Exception
	 * @date 2016年11月29日 下午5:25:00
	 */
	public static String createTicket(String server, String username, String password) throws Exception {
		if (StringTool.isEmpty(server) || StringTool.isEmpty(username) || StringTool.isEmpty(password)) {
			logger.error(ERROR_MSG);
			throw new Exception(ERROR_MSG);
		}
		HttpTool.HttpParam param = HttpTool.createHttpParam();
		param.insert("username", username).insert("password", password);
		String response = HttpTool.doPost(server, param.toString(),
				HttpCode.CONTENT_TYPE.APPLICATION_FORM_URLENCODED.value());
		Matcher matcher = Pattern.compile(ACTION_REG).matcher(response);
		if (matcher.matches()) {
			String action = matcher.group(1);
			return action.substring(action.indexOf(TICKET_PREFIX));
		}
		return "";
	}

	/**
	 * 
	 * 生成token
	 * 
	 * @param server
	 * @param ticket
	 * @param service
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws Exception
	 * @date 2016年11月29日 下午5:25:18
	 */
	public static String createToken(String server, String ticket, String service) throws Exception {
		if (StringTool.isEmpty(server) || StringTool.isEmpty(ticket) || StringTool.isEmpty(service)) {
			logger.error(ERROR_MSG);
			throw new Exception(ERROR_MSG);
		}
		HttpTool.HttpParam param = HttpTool.createHttpParam();
		param.insert("service", service);
		String response = HttpTool.doPost(server + "/" + ticket, param.toString(),
				HttpCode.CONTENT_TYPE.APPLICATION_FORM_URLENCODED.value());
		return response;
	}

	/**
	 * 
	 * 校验token
	 * 
	 * @param server
	 * @param token
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws Exception
	 * @date 2016年11月29日 下午5:25:36
	 */
	public static String validateToken(String server, String token, String service) throws Exception {
		if (StringTool.isEmpty(server) || StringTool.isEmpty(token) || StringTool.isEmpty(service)) {
			logger.error(ERROR_MSG);
			throw new Exception(ERROR_MSG);
		}
		HttpTool.HttpParam param = HttpTool.createHttpParam();
		param.insert("service", service).insert("ticket", token);
		String response = HttpTool.doGet(server + "?" + param.toString());
		Pattern p = Pattern.compile("<cas:user>(.*)</cas:user>");
		Matcher m = p.matcher(response);
		String username = null;
		while (m.find()) {
			username = m.group(1);
		}
		return username;
	}

	/**
	 * 
	 * 注销ticket
	 * 
	 * @param ticket
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws Exception
	 * @date 2016年11月29日 下午5:25:56
	 */
	public static String cancelTicket(String server, String ticket) throws Exception {
		if (StringTool.isEmpty(server) || StringTool.isEmpty(ticket)) {
			logger.error(ERROR_MSG);
			throw new Exception(ERROR_MSG);
		}
		String response = HttpTool.doDelete(server + "/" + ticket, "");
		return response;
	}

}
