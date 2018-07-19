package com.jszx.cricket.platform.tool;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网络工具类:提供获取客户端IP等方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年9月5日 下午1:56:52
 * 
 */

public class NetworkTool {

	private static final Logger logger = LoggerFactory.getLogger(NetworkTool.class);

	private static final String LOCALHOST_4 = "127.0.0.1";

	private static final String LOCALHOST_6 = "0:0:0:0:0:0:0:1";

	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (checkIp(ip)) {
			if (checkIp(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (checkIp(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (checkIp(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (checkIp(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (checkIp(ip)) {
				ip = request.getHeader("X-Real-IP");
			}
			if (checkIp(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip.equals(LOCALHOST_6) ? LOCALHOST_4 : ip;
	}

	private static boolean checkIp(String ip) {
		return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
	}

	/**
	 * 
	 * 获取本机ip
	 * 
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月10日 下午3:12:32
	 */
	public static String getLocalhost() throws Exception {
		try {
			InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces
					.hasMoreElements();) {
				NetworkInterface iface = ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							// 如果是site-local地址，就是它了
							return inetAddr.getHostAddress();
						} else if (candidateAddress == null) {
							// site-local类型的地址未被发现，先记录候选地址
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress.getHostAddress();
			}
			// 如果没有发现 non-loopback地址.只能用最次选的方案
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			return jdkSuppliedAddress.getHostAddress();
		} catch (Exception e) {
			logger.error("获取本机地址失败，请检查...", e);
		}
		return null;
	}

	public static String getMac() throws UnknownHostException, SocketException {
		InetAddress ia = InetAddress.getLocalHost();
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		return DatatypeConverter.printHexBinary(mac);
	}

}
