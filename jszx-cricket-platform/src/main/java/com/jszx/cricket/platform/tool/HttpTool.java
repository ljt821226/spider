/**  
 * @Title: HttpUtil.java
 * @Package com.pioneer.frame.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 吕俊涛   nw_lvjuntao@163.com 
 * @date 2014年4月12日 下午9:20:06
 * @version V1.0  
 */
package com.jszx.cricket.platform.tool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.cricket.platform.code.HttpCode;
import com.jszx.cricket.platform.code.SystemCode;

/**
 * 
 * HTTP工具类:处理http请求
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月11日 上午10:12:13
 *
 */
public class HttpTool {

	private static final Logger logger = LoggerFactory.getLogger(HttpTool.class);

	// 连接超时时长
	public static final int CONNECT_TIMEOUT = 300;

	// 读取超时时长
	public static final int READ_TIMEOUT = 3000;

	private static final String POST = "POST";

	private static final String GET = "GET";

	private static final String PUT = "PUT";

	private static final String DELETE = "DELETE";

	/**
	 * 
	 * POST请求处理函数
	 * 
	 * @param url
	 * @param contentType
	 *            :参考CommonConstant.CONTENT_TYPE
	 * @param message
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午2:56:56
	 */
	public static String doPost(String url, String message, String contentType, Map<String, String> headers,
			int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = buildConnection(url, POST, contentType, headers, connectTimeout, readTimeout);
			writeData(connection, message);
			return readData(connection);
		} catch (IOException e) {
			logger.error("请求失败，请查看具体日志信息...", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * POST请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:09:37
	 */
	public static String doPost(String url, String message, int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), null, connectTimeout, readTimeout);
	}

	/**
	 * 
	 * POST请求处理函数
	 * 
	 * @param url
	 * @param message
	 * @param contentType
	 *            :参考CommonConstant.CONTENT_TYPE
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:29
	 */
	public static String doPost(String url, String message, String contentType) throws IOException {
		return doPost(url, message, contentType, null, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

	/**
	 * 
	 * POST请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:40
	 */
	public static String doPost(String url, String message) throws IOException {
		return doPost(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), null, CONNECT_TIMEOUT,
				READ_TIMEOUT);
	}

	/**
	 * 
	 * POST请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException
	 * @author zhu_yan@uisftech.com
	 * @date 2016年10月19日 下午3:01:40
	 */
	public static String doPost(String url, String message, Map<String, String> headers) throws IOException {
		return doPost(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), headers, CONNECT_TIMEOUT,
				READ_TIMEOUT);
	}

	/**
	 * 
	 * PUT请求处理函数
	 * 
	 * @param url
	 * @param contentType
	 *            :参考CommonConstant.CONTENT_TYPE
	 * @param message
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午2:56:56
	 */
	public static String doPut(String url, String message, String contentType, Map<String, String> headers,
			int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = buildConnection(url, PUT, contentType, headers, connectTimeout, readTimeout);
			writeData(connection, message);
			return readData(connection);
		} catch (IOException e) {
			logger.error("请求失败，请查看具体日志信息...", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * PUT请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:09:37
	 */
	public static String doPut(String url, String message, int connectTimeout, int readTimeout) throws IOException {
		return doPut(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), null, connectTimeout, readTimeout);
	}

	/**
	 * 
	 * PUT请求处理函数
	 * 
	 * @param url
	 * @param message
	 * @param contentType
	 *            :参考CommonConstant.CONTENT_TYPE
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:29
	 */
	public static String doPut(String url, String message, String contentType) throws IOException {
		return doPut(url, message, contentType, null, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

	/**
	 * 
	 * PUT请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:40
	 */
	public static String doPut(String url, String message) throws IOException {
		return doPut(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), null, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

	/**
	 * 
	 * PUT请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException
	 * @author zhu_yan@uisftech.com
	 * @date 2016年10月19日 下午3:01:40
	 */
	public static String doPut(String url, String message, Map<String, String> headers) throws IOException {
		return doPut(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), headers, CONNECT_TIMEOUT,
				READ_TIMEOUT);
	}

	/**
	 * 
	 * DELETE请求处理函数
	 * 
	 * @param url
	 * @param contentType
	 *            :参考CommonConstant.CONTENT_TYPE
	 * @param message
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午2:56:56
	 */
	public static String doDelete(String url, String message, String contentType, Map<String, String> headers,
			int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = buildConnection(url, DELETE, contentType, headers, connectTimeout, readTimeout);
			writeData(connection, message);
			return readData(connection);
		} catch (IOException e) {
			logger.error("请求失败，请查看具体日志信息...", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * DELETE请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:09:37
	 */
	public static String doDelete(String url, String message, int connectTimeout, int readTimeout) throws IOException {
		return doDelete(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), null, connectTimeout,
				readTimeout);
	}

	/**
	 * 
	 * DELETE请求处理函数
	 * 
	 * @param url
	 * @param message
	 * @param contentType
	 *            :参考CommonConstant.CONTENT_TYPE
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:29
	 */
	public static String doDelete(String url, String message, String contentType) throws IOException {
		return doDelete(url, message, contentType, null, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

	/**
	 * 
	 * DELETE请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:40
	 */
	public static String doDelete(String url, String message) throws IOException {
		return doDelete(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), null, CONNECT_TIMEOUT,
				READ_TIMEOUT);
	}

	/**
	 * 
	 * DELETE请求处理函数，contentType默认为application/json
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:01:40
	 */
	public static String doDelete(String url, String message, Map<String, String> header) throws IOException {
		return doDelete(url, message, HttpCode.CONTENT_TYPE.APPLICATION_JSON.value(), header, CONNECT_TIMEOUT,
				READ_TIMEOUT);
	}

	/**
	 * 
	 * GET请求处理方法，需自己拼接参数到URL
	 * 
	 * @param url
	 * @param connectTimeout
	 *            :连接超时时长
	 * @param readTimeout
	 *            :读取超时时长
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 上午10:13:03
	 */
	public static String doGet(String url, Map<String, String> headers, int connectTimeout, int readTimeout)
			throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = buildConnection(url, GET, "", headers, connectTimeout, readTimeout);
			return readData(connection);
		} catch (IOException e) {
			logger.error("请求失败，请查看具体日志信息...", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * GET请求处理方法，需自己拼接参数到URL
	 * 
	 * @param url
	 * @param connectTimeout
	 *            :连接超时时长
	 * @param readTimeout
	 *            :读取超时时长
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 上午10:13:03
	 */
	public static String doGet(String url, int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = buildConnection(url, GET, "", null, connectTimeout, readTimeout);
			return readData(connection);
		} catch (IOException e) {
			logger.error("请求失败，请查看具体日志信息...", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * GET请求处理方法，需自己拼接参数到URL，时限采用默认的
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月19日 下午3:04:33
	 */
	public static String doGet(String url) throws IOException {
		return doGet(url, null, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

	/**
	 * 
	 * GET请求处理方法，需自己拼接参数到URL，时限采用默认的
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @author zhu_yan@uisftech.com
	 * @date 2016年10月19日 下午3:04:33
	 */
	public static String doGet(String url, Map<String, String> headers) throws IOException {
		return doGet(url, headers, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

	/**
	 * 
	 * 获取连接
	 * 
	 * @param url
	 * @param type
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 上午10:14:38
	 */
	// private static HttpURLConnection buildConnection(String url, String type,
	// String contentType, int connectTimeout,
	// int readTimeout) throws IOException {
	// URL postUrl = new URL(url);
	// HttpURLConnection connection = (HttpURLConnection)
	// postUrl.openConnection();
	// connection.setConnectTimeout(connectTimeout);
	// connection.setReadTimeout(readTimeout);
	// connection.setRequestMethod(type);
	// connection.setUseCaches(false);
	// connection.setRequestMethod(type);
	// if (POST.equalsIgnoreCase(type) || PUT.equalsIgnoreCase(type)) {
	// if (StringTool.isEmpty(contentType)) {
	// contentType = CommonConstant.CONTENT_TYPE.APPLICATION_JSON.value();
	// }
	// connection.setDoOutput(true);
	// connection.setDoInput(true);
	// connection.setInstanceFollowRedirects(true);
	// connection.setRequestProperty("Content-Type", contentType);
	// }
	// connection.connect();
	// return connection;
	// }
	/**
	 * 
	 * 获取连接
	 * 
	 * @param url
	 * @param type
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 * @author zhu_yan@uisftech.com
	 * @date 2016年10月11日 上午10:14:38
	 */
	private static HttpURLConnection buildConnection(String url, String type, String contentType,
			Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
		URL postUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setConnectTimeout(connectTimeout);
		connection.setReadTimeout(readTimeout);
		connection.setRequestMethod(type);
		connection.setUseCaches(false);
		connection.setRequestMethod(type);
		connection.setRequestProperty("Charset", "UTF-8");
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				connection.setRequestProperty(header.getKey(), header.getValue());
			}
		}
		if (POST.equalsIgnoreCase(type) || PUT.equalsIgnoreCase(type) || DELETE.equalsIgnoreCase(type)) {
			if (StringTool.isEmpty(contentType)) {
				contentType = HttpCode.CONTENT_TYPE.APPLICATION_JSON.value();
			}
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", contentType);
		}
		connection.connect();
		return connection;
	}

	/**
	 * 
	 * 写入数据
	 * 
	 * @param connection
	 * @param message
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 上午10:14:48
	 */
	private static void writeData(HttpURLConnection connection, String message) throws IOException {
		if (StringTool.isEmpty(message)) {
			return;
		}
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(connection.getOutputStream(), SystemCode.COMMON.ENCODING.value());
			osw.write(message.toString().toCharArray(), 0, message.toString().length());
			osw.flush();
		} finally {
			if (osw != null) {
				osw.close();
			}
		}
	}

	/**
	 * 
	 * 读取数据
	 * 
	 * @param connection
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月11日 上午10:15:00
	 */
	private static String readData(HttpURLConnection connection) throws IOException {
		BufferedReader reader = null;
		StringBuilder lines = new StringBuilder();
		String line;
		try {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			while ((line = reader.readLine()) != null) {
				if (line.length() > 0) {
					lines.append(line.trim());
				}
			}
			return lines.toString();
		} catch (IOException e) {
			logger.error("HTTP Response Code is " + connection.getResponseCode() + " ,please check...");
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static HttpParam createHttpParam() {
		return new HttpParam();
	}

	public static class HttpParam {

		private final Map<String, Object> params = new HashMap<String, Object>();

		public HttpParam() {
		}

		public HttpParam insert(String key, Object value) {
			params.put(key, value);
			return this;
		}

		public HttpParam delete(String key) {
			params.remove(key);
			return this;
		}

		public HttpParam clear() {
			params.clear();
			return this;
		}

		public String toString() {
			try {
				StringBuilder sb = new StringBuilder();
				for (Entry<String, Object> entry : params.entrySet()) {
					sb.append(entry.getKey()).append("=").append(String.valueOf(entry.getValue()));
					sb.append("&");
				}
				String ret = sb.toString();
				return ret.substring(0, ret.length() - 1);
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param action
	 * @param fileList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static String doUpload(String action, List<Map<String, String>> fileList, Map<String, String> paramMap)
			throws Exception {
		String res = "";
		HttpURLConnection conn = null;
		// boundary就是request头和上传文件内容的分隔符
		String BOUNDARY = "---------------------------###############";
		String END = "\r\n";
		String TWOHYPHENS = "--";
		try {
			URL url = new URL(action);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(300000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// text
			if (paramMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = paramMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (fileList != null && !fileList.isEmpty()) {
				for (Map<String, String> fileMap : fileList) {
					String inputName = fileMap.get("inputName");
					String filePath = fileMap.get("filePath");
					String fileName = fileMap.get("fileName");
					if (StringTool.isEmpty(filePath)) {
						continue;
					}
					File file = new File(filePath);
					if (StringTool.isEmpty(fileName)) {
						fileName = file.getName();
					}
					if (StringTool.isEmpty(inputName)) {
						inputName = "file";
					}

					String contentType = FileTool.mimeType(filePath);

					StringBuffer strBuf = new StringBuffer();
					strBuf.append(END).append(TWOHYPHENS).append(BOUNDARY).append(END);
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + fileName
							+ "\"");
					strBuf.append(END);
					strBuf.append("Content-Type:").append(contentType);
					strBuf.append(END).append(END);
					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = (END + TWOHYPHENS + BOUNDARY + TWOHYPHENS + END).getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			logger.error("附件上传失败，请检查...", e);
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}

	/**
	 * 
	 * 文件下载
	 * 
	 * @param action
	 * @param downloadDir
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年12月2日 下午5:07:34
	 */
	public static File doDownload(String action, String downloadDir) throws Exception {
		File file = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(action);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(300000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.connect();

			BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());
			String path = downloadDir + File.separatorChar + getFilename(conn.getHeaderField("Content-Disposition"));
			file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			int size = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				out.write(buf, 0, size);
			}
			bin.close();
			out.close();
			return file;
		} catch (Exception e) {
			logger.error("文件下载失败，请检查...", e);
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String getFilename(String contentDisposition) {
		int start = contentDisposition.indexOf("filename=\"") + "filename=\"".length();
		int end = contentDisposition.lastIndexOf("\"");
		String filename = contentDisposition.substring(start, end);
		return filename;
	}
}
