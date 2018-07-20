package com.jszx.spider.platform.tool;

import java.net.SocketException;
import java.net.UnknownHostException;

import org.codehaus.plexus.util.StringUtils;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月23日 下午4:58:01
 * 
 */

public class KeyTool {

	private static int index = 10000;

	private static long lastTime = 0L;

	private static String MAC = "";

	public synchronized static String generate(String prefix) throws UnknownHostException, SocketException {
		if (StringUtils.isEmpty(MAC)) {
			MAC = NetworkTool.getMac();
		}
		String key = "";
		long time = System.nanoTime();
		if (time == lastTime) {
			key = String.valueOf(time + "-" + (index++));
		} else {
			lastTime = time;
			index = 10000;
			key = String.valueOf(time + "-" + (index));
		}
		if (StringTool.isEmpty(prefix)) {
			return MAC + "-" + key;
		} else {
			return prefix + "-" + MAC + "-" + key;
		}
	}

	public static String generate() throws UnknownHostException, SocketException {
		return generate(null);
	}

}
