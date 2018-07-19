package com.jszx.cricket.platform.tool;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 
 * Json工具类：提供json与Object之间的相互转换功能
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月8日 下午1:08:04
 *
 */
public class JsonTool {

	private static final String LIST = "LIST";

	private static final String NOT_LIST = "NOT_LIST";

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static XmlMapper xmlMapper = new XmlMapper();

	/**
	 * 
	 * 对象转为json字符串
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月21日 下午12:04:01
	 */
	public static String toJson(Object object) throws IOException {
		return objectMapper.writeValueAsString(object);
	}

	/**
	 * 
	 * json字符串转为列表
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月21日 下午12:03:42
	 */
	public static List<?> toList(String json, Class<?> cls) throws IOException {
		buildJsonNull(json, LIST);
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, cls);
		List<?> list = objectMapper.readValue(json, javaType);
		return list;
	}

	/**
	 * 
	 * json字符串转为Map
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月21日 下午12:03:23
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) throws IOException {
		buildJsonNull(json, NOT_LIST);
		return objectMapper.readValue(json, Map.class);
	}

	/**
	 * 
	 * json字符串转为实体
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @date 2017年9月21日 下午12:02:50
	 */
	public static <T> T toEntity(String json, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		if (!EntityTool.isEntity(clazz)) {
			throw new ClassCastException("非BaseEntity类型，请检查...");
		}
		buildJsonNull(json, NOT_LIST);
		T entity = clazz.newInstance();
		Method method = clazz.getMethod("set", String.class, Object.class);
		Map<String, Object> map = toMap(json);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			method.invoke(entity, entry.getKey(), entry.getValue());
		}
		return entity;
	}

	public static <T> T[] toEntities(String json, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (!EntityTool.isEntity(clazz)) {
			throw new ClassCastException("非BaseEntity类型，请检查...");
		}
		buildJsonNull(json, NOT_LIST);
		List<Map<String, Object>> list = (List<Map<String, Object>>) JsonTool.toList(json, Map.class);
		T[] baseEntities = (T[]) Array.newInstance(clazz, list.size());
		for (int i = 0; i < list.size(); i++) {
			T entity = clazz.newInstance();
			Method method = clazz.getMethod("set", String.class, Object.class);
			baseEntities[i] = entity;
			for (Map.Entry<String, Object> entry : list.get(i).entrySet()) {
				method.invoke(entity, entry.getKey(), entry.getValue());
			}
		}
		return baseEntities;
	}

	/**
	 * 
	 * json字符串转为对象
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月21日 下午12:06:01
	 */
	public static Object toObject(String json, Class<?> cls) throws IOException {
		buildJsonNull(json, NOT_LIST);
		Class<?> _class = cls;
		if (cls.isArray()) {
			_class = cls.getComponentType();
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, _class);
			List<?> list = objectMapper.readValue(json, javaType);
			return list.toArray();
		} else {
			return objectMapper.readValue(json, _class);
		}
	}

	/**
	 * 
	 * 判断字符串是否为json格式
	 * 
	 * @param json
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年12月25日 上午11:27:57
	 */
	public static boolean isJson(String json) {
		try {
			objectMapper.readValue(json, Object.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * json为空时候进行赋值
	 * 
	 * @param json
	 * @param type
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月27日 上午10:08:27
	 */
	private static String buildJsonNull(String json, String type) {
		if (StringTool.isEmpty(json)) {
			if (LIST.equalsIgnoreCase(type)) {
				return "[{}]";
			} else {
				return "{}";
			}
		}
		return json;
	}

}
