package com.jszx.spider.platform.code;

public interface FileCode {

	public enum UNIT implements Code<String> {
		B {
			public String value() {
				return "B";
			}
		},
		KB {
			public String value() {
				return "KB";
			}
		},
		MB {
			public String value() {
				return "MB";
			}
		},
		GB {
			public String value() {
				return "MB";
			}
		},
		TB {
			public String value() {
				return "TB";
			}
		},
		PB {
			public String value() {
				return "PB";
			}
		},
		EB {
			public String value() {
				return "EB";
			}
		};
	}

}
