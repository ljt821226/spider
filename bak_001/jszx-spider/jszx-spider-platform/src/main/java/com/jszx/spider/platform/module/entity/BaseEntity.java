package com.jszx.spider.platform.module.entity;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jszx.spider.platform.exception.EntityException;
import com.jszx.spider.platform.tool.EntityTool;
import com.jszx.spider.platform.tool.JsonTool;

/**
 * 
 * 基础实体类:提取公共方法和属性，增强存取能力
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:53:44
 *
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private final transient Map<String, Object> map = new HashMap<>();

	public BaseEntity() {

	}

	/**
	 * 
	 * 获取map类型
	 * 
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 上午9:44:34
	 */
	public Map<String, Object> toMap() {
		try {
			Field[] fields = EntityTool.getFields(this.getClass());
			for (Field field : fields) {
				String name = field.getName();
				Class<?> type = field.getType();
				PropertyDescriptor pd = new PropertyDescriptor(name, this.getClass());
				Method rM = pd.getReadMethod();
				Object value = rM.invoke(this);
				if (EntityTool.isEntity(type)) {
					BaseEntity entity = (BaseEntity) value;
					map.put(name, entity.toMap());
				} else {
					map.put(name, value);
				}
			}
			return map;
		} catch (Exception e) {
			throw new EntityException("转换异常，请检查...", e);
		}
	}

	/**
	 * 
	 * 获取json字符串
	 * 
	 * @return
	 * @throws EntityException
	 * @author 2724216806@qq.com
	 * @date 2018年3月16日 下午4:53:57
	 */
	public String toJson() {
		try {
			return JsonTool.toJson(toMap());
		} catch (Exception e) {
			throw new EntityException("转换异常，请检查...", e);
		}
	}

	public String toXml() {
		try {
			return JsonTool.toJson(toMap());
		} catch (Exception e) {
			throw new EntityException("转换异常，请检查...", e);
		}
	}

	public void set(Map<String, Object> map) {
		map.forEach((key, value) -> set(key, value));
	}

	public void set(List<Map<String, Object>> values) {
		values.forEach(item -> set(item));
	}

	public Object get(String name) {
		try {
			Field field = EntityTool.getField(this.getClass(), name);
			if (field == null) {
				return map.get(name);
			} else {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), this.getClass());
				Method rM = pd.getReadMethod();
				return rM.invoke(this);
			}
		} catch (Exception e) {
			throw new EntityException("[" + this.getClass().getName() + "." + name + "]:不存在对应的get方法...", e);
		}
	}

	public Object getOrDefault(String name, Object defaultValue) {
		Object obj = get(name);
		if (obj == null || ("").equals(obj)) {
			return defaultValue;
		} else {
			return obj;
		}
	}

	public void set(String name, Object value) {
		try {
			Field field = EntityTool.getField(this.getClass(), name);
			if (field != null) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), this.getClass());
				Method wm = pd.getWriteMethod();
				value = EntityTool.buildValue(field.getType(), value, field.getGenericType());
				wm.invoke(this, value);
			}
			map.put(name, value);
		} catch (Exception e) {
			throw new EntityException("[" + this.getClass().getName() + "." + name + "]:不存在对应的set方法...", e);
		}
	}

	public int getInt(String name) {
		return EntityTool.buildInt(get(name));

	}

	public String getString(String name) {
		return EntityTool.buildString(get(name));
	}

	public boolean getBoolean(String name) {
		return EntityTool.buildBoolean(get(name));
	}

	public BigDecimal getBigDecimal(String name) {
		return EntityTool.buildBigDecimal(get(name));
	}

	public Byte getByte(String name) {
		return EntityTool.buildByte(get(name));
	}

	public Date getDate(String name) {
		return EntityTool.buildDate(get(name));
	}

	public Double getDouble(String name) {
		return EntityTool.buildDouble(get(name));
	}

	public Float getFloat(String name) {
		return EntityTool.buildFloat(get(name));
	}

	public Long getLong(String name) {
		return EntityTool.buildLong(get(name));
	}

	public Short getShort(String name) {
		return EntityTool.buildShort(get(name));
	}

	public char getChar(String name) {
		return EntityTool.buildChar(get(name));
	}

	public BigInteger getBigInteger(String name) {
		return EntityTool.buildBigInteger(get(name));
	}

	public Timestamp getTimestamp(String name) {
		return EntityTool.buildTimestamp(get(name));
	}

	public byte[] getBytes(String name) {
		return EntityTool.buildBytes(get(name));
	}

	public BaseEntity getEntity(String name) {
		return (BaseEntity) get(name);
	}

	public BaseEntity[] getEntities(String name) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<BaseEntity> getList(String name) {
		return (List<BaseEntity>) get(name);
	}

	public int hashCode() {
		return map.hashCode();
	}

	public boolean equals(Object object) {
		return map.equals(object);
	}

	public String toString() {
		return toJson();
	}

}
