package com.jszx.spider.platform.expand.cxf.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.utils.FormUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.spider.platform.annotation.EntityParam;
import com.jszx.spider.platform.code.SystemCode;
import com.jszx.spider.platform.exception.ValidatorException;
import com.jszx.spider.platform.tool.ClassTool;
import com.jszx.spider.platform.tool.JsonTool;
import com.jszx.spider.platform.tool.StringTool;

/**
 * 
 * 自定义类型转换类:读取BaseEntity类型的参数并进行映射处理
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月19日 上午10:31:46
 *
 */

@Provider
public class MessageBodyReaderProvider implements MessageBodyReader<Object> {

	private static final Logger logger = LoggerFactory.getLogger(MessageBodyReaderProvider.class);

	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		boolean flag = false;
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(EntityParam.class)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		try {
			Message message = JAXRSUtils.getCurrentMessage();
			Exchange exchange = message.getExchange();
			OperationResourceInfo ori = exchange.get(OperationResourceInfo.class);
			Method method = ori.getAnnotatedMethod();
			EntityParam entityParam = getContextParam(method, annotations);
			String p1 = FormUtils.readBody(entityStream, SystemCode.COMMON.ENCODING.value());
			String p2 = (String) message.get(Message.QUERY_STRING);
			// String p3 = inputStream2String(entityStream);
			String p = (p1 + "&" + p2);
			message.setContent(InputStream.class, new ByteArrayInputStream(p.getBytes()));
			Map<String, String> map = analyzeParams(p);
			Object returnObj = null;
			if (map == null || map.size() == 0) {
				return null;
			} else {
				String value = "";
				if (entityParam != null) {
					value = map.get(entityParam.value());
				}
				if (type.isArray()) {
					returnObj = JsonTool.toEntities(value, type.getComponentType());
				} else if (type.equals(List.class)) {
					returnObj = JsonTool.toList(value, ClassTool.analyzeGeneric(genericType));
				} else {
					returnObj = JsonTool.toEntity(value, type);
				}
			}
			// 实体校验
			if (type.equals("abc")) {
				throw new ValidatorException();
			}
			return returnObj;
		} catch (Exception e) {
			logger.error("GatewayInInterceptor异常", e);
			return null;
		}
	}

	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * 
	 * 获取参数的BodyParam注解
	 * 
	 * @param method
	 * @param annotations
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午10:32:00
	 */
	private EntityParam getContextParam(Method method, Annotation[] annotations) {
		EntityParam entityParam = null;
		Parameter[] parameters = method.getParameters();
		for (Parameter parameter : parameters) {
			if (Arrays.deepEquals(parameter.getAnnotations(), annotations)) {
				entityParam = parameter.getAnnotation(EntityParam.class);
				break;
			}
		}
		return entityParam;
	}

	/**
	 * 
	 * 解析URL参数
	 * 
	 * @param queryStr
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午10:32:17
	 */
	private Map<String, String> analyzeParams(String queryStr) {
		try {
			if (StringTool.isEmpty(queryStr)) {
				return null;
			}
			Map<String, String> map = new HashMap<>();
			if (!StringTool.isEmpty(queryStr)) {
				String[] params = queryStr.split("&");
				for (int i = 0; i < params.length; i++) {
					String[] arr = params[i].split("=");
					if (arr.length == 2) {
						String key = arr[0];
						String value = URLDecoder.decode(arr[1], SystemCode.COMMON.ENCODING.value());
						map.put(key, value);
					} else {
						continue;
					}
				}
			}
			return map;
		} catch (Exception e) {
			logger.error("参数解析异常，请检查...", e);
			return null;
		}
	}

}
