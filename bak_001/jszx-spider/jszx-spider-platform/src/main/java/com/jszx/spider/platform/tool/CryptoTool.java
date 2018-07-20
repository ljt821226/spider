package com.jszx.spider.platform.tool;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密解密工具类:提供通用加密和解密方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月12日 上午10:27:48
 * 
 */

public class CryptoTool {

	private static final Logger logger = LoggerFactory.getLogger(CryptoTool.class);

	/**
	 * 
	 * MD5加密，返回字节数组
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:09:46
	 */
	public static byte[] encryptMD5ToByte(String key) throws Exception {
		try {
			return DigestUtils.md5(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * SHA-1加密，返回字节数组
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:10:03
	 */
	public static byte[] encryptSHA1ToByte(String key) throws Exception {
		try {
			return DigestUtils.sha1(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * SHA-256加密，返回字节数组
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:10:39
	 */
	public static byte[] encryptSHA256ToBype(String key) throws Exception {
		try {
			return DigestUtils.sha256(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * SHA-512加密，返回字节数组
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:10:39
	 */
	public static byte[] encryptSHA512ToBype(String key) throws Exception {
		try {
			return DigestUtils.sha512(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * MD5加密，返回字符串
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:32:39
	 */
	public static String encryptMD5ToHex(String key) throws Exception {
		try {
			return DigestUtils.md5Hex(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * SHA-1加密，返回字符串
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:32:24
	 */
	public static String encryptSHA1ToHex(String key) throws Exception {
		try {
			return DigestUtils.sha1Hex(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * SHA-256加密，返回字符串
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:32:06
	 */
	public static String encryptSHA256ToHex(String key) throws Exception {
		try {
			return DigestUtils.sha256Hex(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * SHA-512加密，返回字符串
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午11:31:39
	 */
	public static String encryptSHA512ToHex(String key) throws Exception {
		try {
			return DigestUtils.sha512Hex(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * BASE64解码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午10:48:55
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		try {
			return Base64.decodeBase64(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * BASE64编码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 上午10:49:11
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		try {
			return Base64.encodeBase64String(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
}
