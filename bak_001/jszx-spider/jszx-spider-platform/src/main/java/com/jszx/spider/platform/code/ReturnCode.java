package com.jszx.spider.platform.code;

public interface ReturnCode {

	public enum MESSAGE implements Code<String> {
		FAILURE {
			public String value() {
				return "操作失败，请联系相关人员进行检查！";
			}
		},
		SUCCESS {
			public String value() {
				return "操作成功！";
			}
		};
	}

	public enum CODE implements Code<Integer> {
		FAILURE {
			public Integer value() {
				return -1;
			}
		},
		SUCCESS {
			public Integer value() {
				return 0;
			}
		};
	}

}
