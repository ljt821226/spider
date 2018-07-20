package com.jszx.spider.platform.tool;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.ibatis.javassist.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.spider.platform.exception.EntityException;
import com.jszx.spider.platform.module.entity.BaseEntity;

public class EntityTool {

	private static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);

	private static final String ORACLE_CLOB = "oracle.sql.CLOB";

	private static final String ORACLE_TIMESTAMP = "oracle.sql.TIMESTAMP";

	/**
	 * 
	 * 判断类是否属于实体类
	 * 
	 * @param clazz
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月21日 上午10:18:23
	 */
	public static boolean isEntity(Class<?> clazz) {
		Class<?> parent = clazz;
		boolean flag = false;
		while (true) {
			if (parent == null || parent.equals(Object.class)) {
				break;
			}
			if (parent.equals(BaseEntity.class)) {
				flag = true;
				break;
			} else {
				parent = parent.getSuperclass();
			}
		}
		return flag;
	}

	/**
	 * 
	 * 判断对象是否为实体对象
	 * 
	 * @param object
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月21日 上午10:18:47
	 */
	public static boolean isEntity(Object object) {
		return object instanceof BaseEntity;
	}

	/**
	 * 
	 * 从实体中拷贝值
	 * 
	 * @param targetEntity：目标实体
	 * @param sourceEntity：源实体
	 * @author lv_juntao@uisftech.com
	 * @date 2016年12月1日 下午2:33:56
	 */
	public static void copy(BaseEntity targetEntity, BaseEntity sourceEntity) {
		try {
			if (targetEntity == null || sourceEntity == null) {
				return;
			}
			Field[] sourceFields = EntityTool.getFields(sourceEntity.getClass());
			Field[] targetFields = EntityTool.getFields(targetEntity.getClass());
			for (Field sourceField : sourceFields) {
				String sourceFileName = sourceField.getName();
				for (Field targetField : targetFields) {
					String targetFileName = targetField.getName();
					if (sourceFileName.equals(targetFileName)) {
						if (Modifier.isFinal(sourceField.getModifiers())
								|| Modifier.isStatic(sourceField.getModifiers())) {
							continue;
						}
						PropertyDescriptor pd = new PropertyDescriptor(sourceFileName, sourceEntity.getClass());
						Method rM = pd.getReadMethod();
						Object value = rM.invoke(sourceEntity);
						if (value != null) {
							targetEntity.set(sourceFileName, rM.invoke(sourceEntity));
						}
					}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("转换异常，请检查...", e);
			}
		}
	}

	/**
	 * 
	 * 获取类和父类的所有属性
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年11月30日 下午5:01:33
	 */
	public static Field[] getFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		if (clazz.equals(BaseEntity.class)) {
			return list.toArray(new Field[list.size()]);
		}

		// 获取所有父类
		List<Class<?>> parentList = new ArrayList<Class<?>>();
		Class<?> parent = clazz.getSuperclass();
		while (!parent.equals(BaseEntity.class)) {
			parentList.add(parent);
			parent = parent.getSuperclass();
		}

		// 获取父类属性
		for (int i = parentList.size() - 1; i >= 0; i--) {
			Field[] parentFields = parentList.get(i).getDeclaredFields();
			for (Field field : parentFields) {
				if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				list.add(field);
			}
		}

		// 获取自身属性
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			list.add(field);
		}
		return list.toArray(new Field[list.size()]);
	}

	// public static void toMap(BaseEntity entity) {
	// try {
	// Field[] fields = entity.getClass().getDeclaredFields();
	// for (Field field : fields) {
	// String fieldName = field.getName();
	// if (Modifier.isFinal(field.getModifiers()) ||
	// Modifier.isStatic(field.getModifiers())) {
	// continue;
	// }
	// PropertyDescriptor pd = new PropertyDescriptor(fieldName,
	// entity.getClass());
	// Method rM = pd.getReadMethod();
	// entity.put(fieldName, rM.invoke(entity));
	// }
	// } catch (Exception e) {
	// if (logger.isErrorEnabled()) {
	// logger.error("转换异常，请检查...", e);
	// }
	// }
	// }

	private static String buildError(Object value, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("[类型转换异常]：");
		sb.append("值类型\"");
		sb.append(value.getClass().getName());
		sb.append("\"");
		sb.append("不能转换为");
		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append("，请检查...");
		return sb.toString();
	}

	public static int buildInt(Object value) {
		try {
			if (value instanceof Number) {
				return ((Number) value).intValue();
			}

			if (value instanceof String) {
				return Integer.parseInt((String) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "int"), e);
		}
		return 0;

	}

	public static String buildString(Object value) {
		try {
			if (value == null) {
				return null;
			}

			if (value instanceof String) {
				return ((String) value);
			}

			if ((value instanceof Number) || (value instanceof Boolean) || (value instanceof Character)) {
				return String.valueOf(value);
			}

			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'T'H':'mm':'ss.S'Z'");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				return sdf.format((Date) value);
			}

			if (value instanceof byte[]) {
				return new String((byte[]) value);
			}

			if (ORACLE_CLOB.equals(value.getClass().getName())) {
				return (String) ClassTool.reflectValue(value, "stringValue");
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "String"), e);
		}
		return null;
	}

	public static boolean buildBoolean(Object value) {
		try {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			}

			if (value instanceof String) {
				return Boolean.valueOf((String) value).booleanValue();
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "boolean"), e);
		}
		return false;
	}

	public static byte buildByte(Object value) {
		try {
			if (value instanceof Number) {
				return ((Number) value).byteValue();
			}

			if (value instanceof String) {
				return Byte.parseByte((String) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "byte"), e);
		}
		return 0;
	}

	public static BigDecimal buildBigDecimal(Object value) {
		try {
			if (value instanceof BigDecimal) {
				return ((BigDecimal) value);
			}

			if (value instanceof BigInteger) {
				return new BigDecimal((BigInteger) value);
			}

			if (value instanceof Long) {
				return BigDecimal.valueOf((Long) value);
			}

			if (value instanceof Number) {
				return BigDecimal.valueOf((Double) value);
			}

			if (value instanceof String) {
				return new BigDecimal((String) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "BigDecimal"), e);
		}
		return null;
	}

	public static byte[] buildBytes(Object value) {
		try {
			if (value instanceof byte[]) {
				return ((byte[]) (byte[]) value);
			}

			if (value instanceof BigInteger) {
				return ((BigInteger) value).toByteArray();
			}

			if (value instanceof String) {
				return ((String) value).getBytes("UTF-8");
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "bytes"), e);
		}
		return null;
	}

	public static char buildChar(Object value) {
		try {
			if (value instanceof Character) {
				return ((Character) value).charValue();
			}

			if (value instanceof String) {
				return ((String) value).charAt(0);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "char"), e);
		}
		return '\0';
	}

	public static Date buildDate(Object value) {
		try {
			if (value == null || ("").equals(value)) {
				return null;
			}

			if (value instanceof String) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				return sdf.parse(value.toString() + " 00:00:00");
			}

			if (value instanceof Date) {
				return ((Date) value);
			}

			if (value instanceof Long) {
				return new Date(((Long) value).longValue());
			}

			if (value instanceof Integer) {
				return new Date(Long.valueOf(value.toString()));
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "Date"), e);
		}
		return null;
	}

	public static double buildDouble(Object value) {
		try {
			if (value instanceof Number) {
				return ((Number) value).doubleValue();
			}

			if (value instanceof String) {
				return Double.parseDouble((String) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "double"), e);
		}
		return 0.0D;
	}

	public static float buildFloat(Object value) {
		try {
			if (value instanceof Number) {
				return ((Number) value).floatValue();
			}

			if (value instanceof String) {
				return Float.parseFloat((String) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "float"), e);
		}
		return 0.0F;
	}

	public static long buildLong(Object value) {
		try {
			if (value instanceof Number) {
				return ((Number) value).longValue();
			}

			if (value instanceof String) {
				return Long.parseLong((String) value);
			}

			if (value instanceof Date) {
				return ((Date) value).getTime();
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "long"), e);
		}
		return 0L;
	}

	public static short buildShort(Object value) {
		try {
			if (value instanceof Number) {
				return ((Number) value).shortValue();
			}

			if (value instanceof String) {
				return Short.parseShort((String) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "short"), e);
		}
		return 0;
	}

	public static BigInteger buildBigInteger(Object value) {
		try {
			if (value == null) {
				return null;
			}

			if (value instanceof BigInteger) {
				return ((BigInteger) value);
			}

			if (value instanceof BigDecimal) {
				return ((BigDecimal) value).toBigInteger();
			}

			if (value instanceof Number) {
				return BigInteger.valueOf(((Number) value).longValue());
			}

			if (value instanceof String) {
				return new BigInteger((String) value);
			}

			if (value instanceof byte[]) {
				return new BigInteger((byte[]) (byte[]) value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "BigInteger"), e);
		}
		return null;
	}

	public static Timestamp buildTimestamp(Object value) {
		try {
			if (value == null) {
				return null;
			}

			if (value instanceof Timestamp) {
				return (Timestamp) value;
			}

			if (value instanceof String) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(value.toString());
				return new Timestamp(date.getTime());
			}

			if (value instanceof Long) {
				return new Timestamp(Long.valueOf(value.toString()));
			}

			if (ORACLE_TIMESTAMP.equals(value.getClass().getName())) {
				return (Timestamp) ClassTool.reflectValue(value, "timestampValue");
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "Timestamp"), e);
		}
		return null;
	}

	public static BaseEntity buildEntity(Class<?> clazz, Object value) {
		try {
			if (value instanceof BaseEntity) {
				return (BaseEntity) value;
			}

			if (value instanceof String) {
				return (BaseEntity) JsonTool.toEntity(value.toString(), clazz);
			}

			if (value instanceof Map) {
				BaseEntity baseEntity = new BaseEntity();
				// baseModel.putAll((Map<String, Object>) value);
				return baseEntity;
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "Model"), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> buildMap(Class<?> clazz, Object value) {
		try {
			if (value instanceof String) {
				return JsonTool.toMap(value.toString());
			}

			if (value instanceof Map) {
				return (Map<String, Object>) value;
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "Map"), e);
		}
		return null;
	}

	public static List<?> buildList(Class<?> clazz, Object value, Type genericType) {
		try {
			if (value instanceof List) {
				return (List<?>) value;
			}

			if (value instanceof String) {
				Class<?> paramClass = ClassTool.analyzeGeneric(genericType);
				return JsonTool.toList(value.toString(), paramClass);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "List"), e);
		}
		return null;
	}

	public static Object buildValue(Class<?> clazz, Object value, Type genericType) {
		try {
			String name = clazz.getName();
			if ("java.lang.Byte".equals(name) || "byte".equals(name)) {
				return buildByte(value);
			}

			if ("java.lang.Double".equals(name) || "double".equals(name)) {
				return buildDouble(value);
			}

			if ("java.lang.Float".equals(name) || "float".equals(name)) {
				return buildFloat(value);
			}

			if ("java.lang.Integer".equals(name) || "int".equals(name)) {
				return buildInt(value);
			}

			if ("java.lang.Long".equals(name) || "long".equals(name)) {
				return buildLong(value);
			}

			if ("java.lang.Short".equals(name) || "short".equals(name)) {
				return buildShort(value);
			}

			if ("char".equals(name)) {
				return buildChar(value);
			}

			if ("java.util.Date".equals(name)) {
				return buildDate(value);
			}

			if ("java.math.BigDecimal".equals(name)) {
				return buildBigDecimal(value);
			}

			if ("java.math.BigInteger".equals(name)) {
				return buildBigInteger(value);
			}

			if ("java.lang.String".equals(name)) {
				return buildString(value);
			}

			if ("java.sql.Timestamp".equals(name)) {
				return buildTimestamp(value);
			}

			if ("java.lang.Boolean".equals(name)) {
				return buildBoolean(value);
			}

			if (isEntity(clazz)) {
				return buildEntity(clazz, value);
			}

			if ("java.util.Map".equals(name) || "java.util.HashMap".equals(name)) {
				return buildMap(clazz, value);
			}

			if ("java.util.List".equals(name) || "java.util.ArrayList".equals(name)) {
				return buildList(clazz, value, genericType);
			}

		} catch (Exception e) {
			throw new EntityException(buildError(value, "buildValue"), e);
		}
		return value;

	}

	/**
	 * 
	 * 转换数据库特殊类型为java标准类型
	 * 
	 * @param value
	 * @return
	 * @author 2724216806@qq.com
	 * @ @date 2018年3月18日 下午3:13:27
	 */
	public static Object transformValue(Object value) {
		try {
			if (value == null || ("").equals(value)) {
				return value;
			}

			String className = value.getClass().getName();
			if (ORACLE_TIMESTAMP.equals(className)) {
				return buildTimestamp(value);
			}
			if (ORACLE_CLOB.equals(className)) {
				return buildString(value);
			}
		} catch (Exception e) {
			throw new EntityException(buildError(value, "transformValue"), e);
		}
		return value;

	}

	/**
	 * 获取实体的属性
	 * 
	 * @param clazz
	 * @param filedName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			for (Class<?> superClass = clazz; superClass != BaseEntity.class; superClass = superClass.getSuperclass()) {
				Field field = superClass.getDeclaredField(fieldName);
				if (field != null) {
					return field;
				} else {
					continue;
				}
			}
		} catch (NoSuchFieldException | SecurityException e) {
			return null;
		}
		return null;

	}

}
