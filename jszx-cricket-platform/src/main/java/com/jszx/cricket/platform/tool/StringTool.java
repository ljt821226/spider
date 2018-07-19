package com.jszx.cricket.platform.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月13日 上午10:33:24
 * 
 */

public class StringTool {

	private static final Logger logger = LoggerFactory.getLogger(StringTool.class);

	/**
	 * 
	 * 判断字符串是否为空
	 * 
	 * @param value
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 下午2:39:54
	 */
	public static boolean isEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 判断字符串是否不为空
	 * 
	 * @param value
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 下午2:40:05
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 
	 * 字节数组转16进制
	 * 
	 * @param bts
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2016年10月12日 上午11:18:47
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 
	 * 从流中读取字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年12月19日 下午2:09:25
	 */
	public static String transformInputStreamToString(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

}
