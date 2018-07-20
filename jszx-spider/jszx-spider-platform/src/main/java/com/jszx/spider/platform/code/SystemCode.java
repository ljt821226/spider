package com.jszx.spider.platform.code;

import com.jszx.spider.platform.tool.SpringTool;

public interface SystemCode {

	public enum COMMON implements Code<String> {
		PAGE {
			public String value() {
				return "__pageEntity";
			}
		},
		NULL {
			public String value() {
				return "__NULL";
			}
		},
		STRING {
			public String value() {
				return "__STRING";
			}
		},
		ENCODING {
			public String value() {
				return "UTF-8";
			}
		},
		PACKAGE {
			public String value() {
				return "com.jszx";
			}
		};
	}

	public enum DATABASE implements Code<String> {
		NAME {
			public String value() {
				return System.getProperty("sytem.database.name");
			}
		},
		VERSION {
			public String value() {
				return System.getProperty("sytem.database.version");
			}
		},
		JSON {
			public String value() {
				return System.getProperty("sytem.database.json");
			}
		};
	}
	
	/**
	 * 
	 * 应用常量类
	 * 
	 * @version 1.0
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月11日 上午9:51:20
	 *
	 */
	public enum APPLICATION implements Code<String> {
		NAME {
			public String value() {
				return SpringTool.getProperty("spring.application.name");
			}
		},
		VERSION {
			public String value() {
				return SpringTool.getProperty("spring.application.version");
			}
		},
		PORT {
			public String value() {
				return SpringTool.getProperty("server.port");
			}
		};
	}

	/**
	 * 
	 * @author 2724216806@qq.com
	 *
	 */
	public enum ENVIRONMENT implements Code<String> {
		DEV {
			public String value() {
				return "application-dev.properties";
			}
		},
		UAT {
			public String value() {
				return "application-uat.properties";
			}
		},
		PROD {
			public String value() {
				return "application-prod.properties";
			}
		},
		DEFAULT {
			public String value() {
				return "application.properties";
			}
		},
		CURRENT {
			public String value() {
				return System.getProperty("spring.profiles.active");
			}
		};
	}
	

}
