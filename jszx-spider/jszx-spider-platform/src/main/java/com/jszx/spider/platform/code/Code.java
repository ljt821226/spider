package com.jszx.spider.platform.code;

public interface Code<T> {

	/**
	 * 获取名称
	 * @return
	 */
	public String name();

	public default T value() {
		return null;
	}

	public default String code() {
		return name();
	}
}
