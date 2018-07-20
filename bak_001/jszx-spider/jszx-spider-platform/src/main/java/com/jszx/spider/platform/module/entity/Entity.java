package com.jszx.spider.platform.module.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月3日 下午10:00:20
 * 
 */

public abstract interface Entity extends Serializable {

	public abstract Object get(String key);

	public abstract void set(String key, Object value);

	public abstract boolean getBoolean(String key);

	public abstract byte getByte(String key);

	public abstract char getChar(String key);

	public abstract double getDouble(String key);

	public abstract float getFloat(String key);

	public abstract int getInt(String key);

	public abstract long getLong(String key);

	public abstract short getShort(String key);

	public abstract byte[] getBytes(String key);

	public abstract BigDecimal getBigDecimal(String key);

	public abstract BigInteger getBigInteger(String key);

	public abstract Entity getDataObject(String key);

	public abstract Date getDate(String key);

	public abstract String getString(String key);

	public abstract List<?> getList(String key);

	public abstract void setBoolean(String key, boolean value);

	public abstract void setByte(String key, byte value);

	public abstract void setChar(String key, char value);

	public abstract void setDouble(String key, double value);

	public abstract void setFloat(String key, float value);

	public abstract void setInt(String key, int value);

	public abstract void setLong(String key, long value);

	public abstract void setShort(String key, short value);

	public abstract void setBytes(String key, byte[] value);

	public abstract void setBigDecimal(String key, BigDecimal value);

	public abstract void setBigInteger(String key, BigInteger value);

	public abstract void setDataObject(String key, Entity value);

	public abstract void setDate(String key, Date value);

	public abstract void setString(String key, String value);

	public abstract void setList(String key, List<?> value);

}
