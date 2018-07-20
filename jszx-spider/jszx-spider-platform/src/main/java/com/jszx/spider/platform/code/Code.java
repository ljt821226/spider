package com.jszx.spider.platform.code;

public interface Code<T> {

	public String name();

	public default T value() {
		return null;
	}

	public default String code() {
		return name();
	}
}
