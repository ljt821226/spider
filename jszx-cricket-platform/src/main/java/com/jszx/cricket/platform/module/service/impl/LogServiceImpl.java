package com.jszx.cricket.platform.module.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.stereotype.Service;

import com.jszx.cricket.platform.code.ReturnCode;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.BaseEntity;
import com.jszx.cricket.platform.module.service.LogService;
import com.jszx.cricket.platform.tool.DateTool;
import com.jszx.cricket.platform.tool.StringTool;

@Service("com.jszx.cricket.platform.module.service.logService")
public class LogServiceImpl implements LogService {

	/**
	 * 平台监控日志查询
	 */
	public List<BaseEntity> list(BaseEntity entity) throws ServiceException {
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		try {
			String date = entity.getString("date");
			String path = getFilePath(date);
			BaseEntity b = readFileAttributes(path);
			if (b != null) {
				list.add(b);
			}
			return list;
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/**
	 * 
	 * 获得日志文件路径
	 * 
	 * @param date
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午11:09:40
	 */
	private String getFilePath(String date) {
		String path = null;
		String today = DateTool.getCurrentDate("yyyy-MM-dd", new Date());
		// 如果传的时间为空或者为当天，那么直接返回当天日志文件路径
		if (StringTool.isEmpty(date) || date.equals(today)) {
			path = String.format("%s/%s/%s.log", System.getProperty("logging.path"), System.getProperty("logging.file"),
					System.getProperty("logging.file"));
		} else { // 否则，根据输入的时间进行对应的日志文件路径
			path = String.format("%s/%s/%s.log", System.getProperty("logging.path"), System.getProperty("logging.file"),
					System.getProperty("logging.file") + "." + date);
		}
		return path;
	}

	/**
	 * 
	 * 获得文件相关属性
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午11:09:49
	 */
	private BaseEntity readFileAttributes(String path) throws Exception {
		BaseEntity b = null;
		try {
			File f = new File(path);
			if (f.isFile() && f.exists()) {
				b = new BaseEntity();
				b.set("fileName", f.getName());
				b.set("fileSize", getFileSize(f.length()));
			}
		} catch (Exception e) {
			throw e;
		}
		return b;
	}

	/**
	 * 
	 * 文件大小单位转换
	 * 
	 * @param size
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午11:09:57
	 */
	private String getFileSize(long size) {
		StringBuffer bytes = new StringBuffer();
		DecimalFormat format = new DecimalFormat("###.0");
		if (size >= 1024 * 1024 * 1024) {
			double i = (size / (1024.0 * 1024.0 * 1024.0));
			bytes.append(format.format(i)).append("GB");
		} else if (size >= 1024 * 1024) {
			double i = (size / (1024.0 * 1024.0));
			bytes.append(format.format(i)).append("MB");
		} else if (size >= 1024) {
			double i = (size / (1024.0));
			bytes.append(format.format(i)).append("KB");
		} else if (size < 1024) {
			if (size <= 0) {
				bytes.append("0B");
			} else {
				bytes.append((int) size).append("B");
			}
		}
		return bytes.toString();
	}

	/**
	 * 
	 * 根据传入的文件名找到文件的路径
	 * 
	 * @param fileName
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午11:10:07
	 */
	private String getDownLoadFilePath(String fileName) {
		String path = null;
		if (fileName.indexOf("-") > -1) {
			String[] temp = fileName.split("\\.");
			path = getFilePath(temp[1]);
		} else {
			path = getFilePath(null);
		}
		return path;
	}

	/**
	 * 平台监控日志下载
	 */
	public void download(BaseEntity entity, MessageContext context) throws ServiceException {
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			// 获得文件名，文件路径，文件大小
			String fileName = entity.getString("fileName");
			String filePath = getDownLoadFilePath(fileName);
			File f = new File(filePath);
			String fileSize = String.valueOf(f.length());

			// 设置下载响应头
			HttpServletResponse response = context.getHttpServletResponse();
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition",
					"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
			response.addHeader("Content-Length", fileSize);

			// 将文件输出流刷新到浏览器
			os = response.getOutputStream();
			fis = new FileInputStream(f);
			byte[] b = new byte[(int) f.length()];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			os.flush();
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
			}
		}
	}
}
