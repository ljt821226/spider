package com.jszx.cricket.platform.expand.cxf.provider;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.cricket.platform.code.ReturnCode;
import com.jszx.cricket.platform.code.SystemCode;
import com.jszx.cricket.platform.code.TransactionCode;
import com.jszx.cricket.platform.module.entity.BaseEntity;
import com.jszx.cricket.platform.module.entity.PageEntity;
import com.jszx.cricket.platform.module.entity.ResponseEntity;
import com.jszx.cricket.platform.tool.EntityTool;
import com.jszx.cricket.platform.tool.JsonTool;
import com.jszx.cricket.platform.tool.PlatformTool;
import com.jszx.cricket.platform.tool.SpringTool;
import com.jszx.cricket.platform.tool.StringTool;

/**
 * 
 * 自定义类型输出类:对restful输出结果进行封装
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月19日 上午10:30:45
 *
 */

@Provider
public class MessageBodyWriterProvider<T> implements MessageBodyWriter<T> {

	private static final Logger logger = LoggerFactory.getLogger(MessageBodyWriterProvider.class);

	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (mediaType.toString().equals(MediaType.APPLICATION_JSON)) {
			return true;
		}
		return false;
	}

	public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		if (type.getSuperclass().equals(FilterInputStream.class)) {
			buildInputStream(t, type, genericType, annotations, mediaType, httpHeaders, entityStream);
		} else {
			buildEntity(t, type, genericType, annotations, mediaType, httpHeaders, entityStream);
		}
	}

	/**
	 * 
	 * 构建流类型转换
	 * 
	 * @param t
	 * @param type
	 * @param genericType
	 * @param annotations
	 * @param mediaType
	 * @param httpHeaders
	 * @param entityStream
	 * @throws IOException
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午10:31:08
	 */
	private void buildInputStream(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
		FilterInputStream in = (FilterInputStream) t;
		byte[] buffer = new byte[1024];
		while (true) {
			int bytesRead = in.read(buffer);
			if (bytesRead == -1)
				break;
			entityStream.write(buffer, 0, bytesRead);
		}
		in.close();
	}

	/**
	 * 
	 * 构建实体类型转换
	 * 
	 * @param t
	 * @param type
	 * @param genericType
	 * @param annotations
	 * @param mediaType
	 * @param httpHeaders
	 * @param entityStream
	 * @throws IOException
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午10:31:19
	 */
	private void buildEntity(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		PageEntity pageEntity = (PageEntity) request.getAttribute(SystemCode.COMMON.PAGE.value());
		request.removeAttribute(SystemCode.COMMON.PAGE.value());
		String rt = buildReturn(pageEntity, t, request);
		logger.info(rt);
		entityStream.write(rt.getBytes(SystemCode.COMMON.ENCODING.value()));
	}

	/**
	 * 
	 * 构建返回信息
	 * 
	 * @param pageEntity
	 * @param t
	 * @param request
	 * @return
	 * @throws IOException
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午10:31:28
	 */

	@SuppressWarnings("unchecked")
	private String buildReturn(PageEntity pageEntity, T t, HttpServletRequest request) throws IOException {
		if (t.getClass().equals(HashMap.class)) {
			Map<String, Object> _map = (Map<String, Object>) t;
			if (_map.containsKey(SystemCode.COMMON.NULL.value())) {
				t = null;
			}
			if (_map.containsKey(SystemCode.COMMON.STRING.value())) {
				String _v = (String) _map.get(SystemCode.COMMON.STRING.value());
				if (StringTool.isEmpty(_v)) {
					t = null;
				} else {
					t = (T) _map.get(SystemCode.COMMON.STRING.value());
				}
			}
		}

		ResponseEntity responseEntity = new ResponseEntity();
		if (EntityTool.isEntity(t)) {
			BaseEntity entity = (BaseEntity) t;
			responseEntity.setData(entity.toMap());
		} else if (t instanceof List) {
			List<?> list = (List<?>) t;
			List<Object> retList = new ArrayList<Object>();
			for (Object item : list) {
				if (EntityTool.isEntity(item)) {
					BaseEntity entity = (BaseEntity) item;
					retList.add(entity.toMap());
				} else {
					retList.add(item);
				}
			}
			responseEntity.setData(retList);
		} else {
			responseEntity.setData(t);
		}
		responseEntity.setCode(ReturnCode.CODE.SUCCESS.value());
		String msg = PlatformTool.getSuccessMessage();
		if (StringTool.isNotEmpty(msg)) {
			responseEntity.setMessage(msg);
		} else {
			responseEntity.setMessage(ReturnCode.MESSAGE.SUCCESS.value());
		}

		// 添加分页标识
		if (pageEntity != null) {
			responseEntity.set("page", pageEntity);
		}

		// 添加事务回调标识
		String path = request.getRequestURI();
		if (path.lastIndexOf(TransactionCode.ACTION.PREPARE.value()) == 0) {
			responseEntity.set(TransactionCode.MARK.LOCALHOST.code(),
					SpringTool.getProperty(TransactionCode.MARK.LOCALHOST.value()));
		}
		return JsonTool.toJson(responseEntity);
	}

}
