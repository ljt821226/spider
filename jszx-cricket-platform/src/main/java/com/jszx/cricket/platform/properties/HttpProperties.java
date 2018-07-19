package com.jszx.cricket.platform.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jszx.cricket.http")
public class HttpProperties {

	private boolean launch = false;

	private boolean switcher = false;

	private int port = 80;

	public boolean isLaunch() {
		return launch;
	}

	public void setLaunch(boolean launch) {
		this.launch = launch;
	}

	public boolean isSwitcher() {
		return switcher;
	}

	public void setSwitcher(boolean switcher) {
		this.switcher = switcher;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
