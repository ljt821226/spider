package com.jszx.spider.platform.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 文件操作工具类：提供常用的文件操作方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月20日 上午11:49:13
 *
 */
public class FileTool {

	private static final Logger logger = LoggerFactory.getLogger(FileTool.class);

	/**
	 * 
	 * 获取文件mime-type
	 * 
	 * @param fileName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:49:32
	 */
	public static String mimeType(String fileName) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(fileName);
		if(StringTool.isEmpty(type)){
			return "text/plain";
		}
		return type;
	}

	/**
	 * 
	 * 创建文件或者目录
	 * 
	 * @param fileName
	 *            要創建的文件名
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:49:51
	 */
	public static File create(String fileName) throws IOException {
		logger.info("【" + fileName + "】：创建开始...");
		File file = null;
		try {
			file = new File(fileName);
			if (!file.getParentFile().exists()) {
				File parentFile = file.getParentFile();
				parentFile.mkdirs();
				if (file.isFile()) {
					file.createNewFile();
				} else {
					file.mkdirs();
				}
			}
		} catch (IOException e) {
			logger.error("【" + fileName + "】：创建失败...");
			throw e;
		}
		logger.info("【" + fileName + "】：创建成功...");
		return file;
	}

	/**
	 * 
	 * 文件写入
	 * 
	 * @param fileName
	 *            要写入文件
	 * @param content
	 *            要写入文件的内容
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:51:15
	 */
	public static File write(String fileName, String content) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			logger.warn("【" + fileName + "】：不存在，已中止操作，请检查...");
			return null;
		}

		if (!file.isFile()) {
			logger.warn("【" + fileName + "】：不是有效的文件，已中止操作，请检查...");
			return null;
		}

		if (content == null || "".equals(content)) {
			logger.warn("【content】：为空，已中止操作，请检查...");
			return file;
		}
		logger.info("【" + fileName + "】：写入开始...");
		FileWriter fw;
		try {
			fw = new FileWriter(fileName);
			PrintWriter pw = new PrintWriter(fw);
			String strContent = content;
			pw.println(strContent);
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			logger.error("【" + fileName + "】：写入失败...");
			throw e;
		}
		logger.info("【" + fileName + "】：写入成功...");
		return file;
	}

	/**
	 * 
	 * 移动文件到指定目录,并删除原文件
	 * 
	 * @param srcPath
	 *            包含路径的文件名 如：E:/phsftp/src/ljq.txt
	 * @param destPath
	 *            目标文件目录 如：E:/phsftp/dest
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:52:06
	 */
	public static File move(String srcPath, String destPath) {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		if (!srcFile.exists()) {
			logger.warn("【" + srcPath + "】：不存在，已中止操作，请检查...");
			return null;
		}
		if (!destFile.exists()) {
			logger.warn("【" + destFile + "】：不存在，已中止操作，请检查...");
			return null;
		}
		logger.info("【" + srcPath + "】：移动开始...");
		File returnFile = new File(destFile, srcFile.getName());
		srcFile.renameTo(returnFile);
		logger.info("【" + srcPath + "】：移动成功...");
		return returnFile;
	}

	/**
	 * 
	 * 删除文件或者文件夹
	 * 
	 * @param file
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:52:42
	 */
	public static void delete(File file) {
		logger.info("【" + file.getPath() + "】：删除开始...");
		if (file.isFile()) {
			file.delete();
		} else { // 文件夹
			// 获得当前文件夹下的所有子文件和子文件夹
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				// 递归调用，处理每个文件对象
				delete(files[i]);
			}
			// 删除当前文件夹
			file.delete();
		}
		logger.info("【" + file.getPath() + "】：删除成功...");
	}

	/**
	 * 
	 * 复制文件夹
	 * 
	 * @param srcFile
	 *            源文件夹路径 如：E:/phsftp/src
	 * @param folder
	 *            目标文件夹路径 如：E:/phsftp/dest
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:53:01
	 */
	public static void copy(String srcFile, String folder) throws IOException {
		logger.info("【" + srcFile + "】：复制开始...");
		// 如果文件夹不存在 则新建文件夹
		create(folder);
		File file = new File(srcFile);
		if (file.isFile()) {
			FileInputStream input = null;
			FileOutputStream output = null;
			try {
				input = new FileInputStream(srcFile);

				output = new FileOutputStream(folder + "/" + (file.getName()).toString());
				byte[] buffer = new byte[1024 * 2];
				int len;
				while ((len = input.read(buffer)) != -1) {
					output.write(buffer, 0, len);
				}

			} catch (FileNotFoundException e) {
				logger.error("【" + srcFile + "】：文件未找到...", e);
				throw e;
			} catch (IOException e) {
				logger.error("【" + srcFile + "】：复制失败...", e);
				throw e;
			} finally {
				try {
					output.flush();
					output.close();
					input.close();
				} catch (IOException e) {
					logger.error("【" + srcFile + "】：复制失败...", e);
				}
			}
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					copy(files[i].getPath(), folder);
				}
				if (files[i].isDirectory()) {
					copy(files[i].getPath(), folder + "/" + files[i].getName());
				}
			}
		}
		logger.info("【" + srcFile + "】：复制成功...");
	}

	/**
	 * 
	 * 复制文件，并指定文件编码
	 * 
	 * @param srcFile
	 * @param folder
	 * @param srcCoding
	 * @param destCoding
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:54:12
	 */
	public static void copy(String srcFile, String folder, String srcCoding, String destCoding) throws IOException {
		logger.info("【" + srcFile + "】：复制开始...");
		// 如果文件夹不存在 则新建文件夹
		create(folder);
		File file = new File(srcFile);
		if (file.isFile()) {
			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile), srcCoding));

				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(folder + "/" + (file.getName()).toString()), destCoding));

				char[] cbuf = new char[1024 * 5];
				int len = cbuf.length;
				int off = 0;
				int ret = 0;
				while ((ret = br.read(cbuf, off, len)) > 0) {
					off += ret;
					len -= ret;
				}
				bw.write(cbuf, 0, off);
				bw.flush();
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持编码：", e);
			} catch (FileNotFoundException e) {
				logger.error("【" + srcFile + "】：文件未找到...", e);
			} catch (IOException e) {
				logger.error("复制失败：", e);
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					logger.error("复制失败：", e);
				}
			}
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					copy(files[i].getPath(), folder);
				}
				if (files[i].isDirectory()) {
					copy(files[i].getPath(), folder + "/" + files[i].getName());
				}
			}
		}
		logger.info("【" + srcFile + "】：复制成功...");
	}

	/**
	 * 
	 * [方法描述]解压文件到指定的文件夹
	 * 
	 * @param zipPath
	 * @param descDir
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:54:25
	 */
	public static File unzip(String zipPath, String descDir) {
		logger.info("【" + zipPath + "】：解压开始...");
		ZipFile zip = null;
		OutputStream out = null;
		InputStream in = null;
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		try {
			zip = new ZipFile(new File(zipPath));
			for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				in = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
				// 判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				// 输出文件路径信息
				out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
			}
		} catch (ZipException e) {
			logger.error("解压失败：", e);
		} catch (IOException e) {
			logger.error("解压失败：", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("解压失败：", e);
			}
		}
		logger.info("【" + zipPath + "】：解压成功...");
		return pathFile;
	}

	/**
	 * 
	 * [方法描述]压缩文件或者文件夹到指定的文件
	 * 
	 * @param srcFileName
	 *            要压缩的文件
	 * @param zipFileName
	 *            压缩后的文件
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:54:40
	 */
	public static File zip(String srcFileName, String zipFileName) throws IOException {
		logger.info("【" + srcFileName + "】：压缩开始...");
		File srcFile = new File(srcFileName);
		File zipFile = new File(zipFileName);
		ZipOutputStream zos = null;
		BufferedOutputStream bos = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFile));
			bos = new BufferedOutputStream(zos);
			zip(zos, srcFile, srcFile.getName(), bos);
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				logger.error("解压失败：", e);
			}
		}
		logger.info("【" + srcFileName + "】：压缩成功...");
		return zipFile;
	}

	/**
	 * 
	 * 读取文件到二进制
	 * 
	 * @param file
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:55:04
	 */
	public static byte[] readFileByByte(File file) throws IOException {
		InputStream is = null;
		byte[] bytes = null;
		int offset = 0;
		int numRead = 0;
		try {
			is = new FileInputStream(file);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				// File is too large
			}
			bytes = new byte[(int) length];

			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			if (offset < bytes.length) {

			}
		} catch (IOException e) {
			logger.error("读取失败：", e);
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return bytes;
	}

	/**
	 * 
	 * 获取标准文件大小，如30KB，15.5MB
	 * 
	 * @param file
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:55:34
	 */
	public static String size(File file) {
		long size = file.length();
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (size < 1024 * 1024) {
			f = (float) ((float) size / (float) 1024);
			return (df.format(new Float(f).doubleValue()) + " KB");
		} else {
			f = (float) ((float) size / (float) (1024 * 1024));
			return (df.format(new Float(f).doubleValue()) + " MB");
		}

	}

	/**
	 * 
	 * 检查文件是否存在
	 * 
	 * @param fileName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:55:45
	 */
	public static boolean isExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 
	 * 读取文件到字符串
	 * 
	 * @param fileName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:55:55
	 */
	public static String readFileByString(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader in = null;
		StringBuffer sb = null;
		String str = "";
		try {
			in = new BufferedReader(new FileReader(file));
			sb = new StringBuffer();
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (FileNotFoundException e) {
			logger.error("文件未找到...", e);
			throw e;
		} catch (IOException e) {
			logger.error("读取失败...", e);
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return sb.toString();
	}
	
	public static String readFileByString(String fileName,String format) throws IOException {
		File file = new File(fileName);
		BufferedReader in = null;
		StringBuffer sb = null;
		String str = "";
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"utf-8");
			in = new BufferedReader(read);
			sb = new StringBuffer();
			while ((str = in.readLine()) != null) {
				sb.append(str + format);
			}
		} catch (FileNotFoundException e) {
			logger.error("文件未找到...", e);
			throw e;
		} catch (IOException e) {
			logger.error("读取失败...", e);
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 获取目录所有所有文件和文件夹
	 * 
	 * @param fileName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 上午11:56:50
	 */
	public static List<File> list(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return null;
		}
		return Arrays.asList(file.listFiles());
	}

	/**
	 * 
	 * 读取数据
	 * 
	 * @param is
	 * @param charsetName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:57:04
	 */
	public static String readData(InputStream is, String charsetName) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		byte[] data = null;
		int len = -1;
		String str = null;
		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			data = baos.toByteArray();
			str = new String(data, charsetName);
		} catch (IOException e) {
			logger.error("读取失败...", e);
			throw e;
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return str;
	}

	/**
	 * 
	 * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
	 * 
	 * @param path
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:57:40
	 */
	public static Set<String> readFileByLine(String path) throws IOException {
		Set<String> datas = new HashSet<String>();
		FileReader fr = null;
		BufferedReader br = null;
		String line = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				datas.add(line);
			}
		} catch (FileNotFoundException e) {
			logger.error("文件未找到...", e);
			throw e;
		} catch (IOException e) {
			logger.error("文件读取...", e);
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		}
		return datas;
	}

	/**
	 * 
	 * 递归压缩文件或者文件夹
	 * 
	 * @param zos
	 * @param srcFile
	 * @param base
	 * @param bos
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:58:22
	 */
	private static void zip(ZipOutputStream zos, File srcFile, String base, BufferedOutputStream bos)
			throws IOException {
		logger.info("【文件压缩】:压缩文件开始...");
		FileInputStream in = null;
		BufferedInputStream bi = null;
		int b;
		try {
			if (srcFile.isFile()) {
				zos.putNextEntry(new ZipEntry(base));
				// 创建zip压缩进入点base
				in = new FileInputStream(srcFile);
				bi = new BufferedInputStream(in);
				while ((b = bi.read()) != -1) {
					bos.write(b); // 将字节流写入当前zip目录
				}
			} else if (srcFile.isDirectory()) {
				File[] fileList = srcFile.listFiles();
				if (fileList.length == 0) {
					zos.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
					System.out.println(base + "/");
				}
				for (int i = 0; i < fileList.length; i++) {
					zip(zos, fileList[i], base + "/" + fileList[i].getName(), bos);
				}
			}
		} catch (IOException e) {
			logger.error("压缩失败...", e);
			throw e;
		} finally {
			if (bi != null) {
				bi.close();
			}
			if (in != null) {
				in.close();
			}
		}
		logger.info("【文件压缩】:压缩文件完成...");
	}

	/**
	 * 
	 * 更加编码读取文件
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 上午11:58:56
	 */
	public static BufferedReader getBufferedReader(String fileName, String encoding) throws IOException {
		InputStream is = null;
		BufferedReader bf = null;
		Reader reader = null;
		try {
			is = new FileInputStream(new File(fileName));
			if (StringTool.isEmpty(encoding)) {
				reader = new InputStreamReader(is);
			} else {
				reader = new InputStreamReader(is, encoding);
			}
			bf = new BufferedReader(reader);
			return bf;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 
	 * 根据编码读取文件
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws IOException
	 * @date 2016年10月20日 下午12:03:38
	 */
	public static InputStreamReader getInputStreamReader(String fileName, String encoding) throws IOException {
		InputStream is = null;
		InputStreamReader isr = null;
		try {
			is = new FileInputStream(new File(fileName));
			if (StringTool.isEmpty(encoding)) {
				isr = new InputStreamReader(is);
			} else {
				isr = new InputStreamReader(is, encoding);
			}
			return isr;
		} catch (FileNotFoundException e) {
			logger.error("文件未找到...", e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			logger.error("文件不支持编码...", e);
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 
	 * 根据编码写问文件
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午12:05:23
	 */
	public static BufferedWriter getBufferedWriter(String fileName, String encoding) throws IOException {
		OutputStream os = null;
		BufferedWriter bw = null;
		Writer writer = null;
		try {
			os = new FileOutputStream(new File(fileName));
			if (StringTool.isEmpty(encoding)) {
				writer = new OutputStreamWriter(os);
			} else {
				writer = new OutputStreamWriter(os, encoding);
			}
			bw = new BufferedWriter(writer);
		} catch (FileNotFoundException e) {
			logger.error("文件未找到...", e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			logger.error("文件不支持编码...", e);
			throw e;
		} finally {
			if (os != null) {
				os.close();
			}
		}
		return bw;
	}

}
