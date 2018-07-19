package com.jszx.cricket.platform.code;

public interface ErrorCode {

	public enum CODE implements Code<Integer> {
		NULL {// 为空错误
			public Integer value() {
				return 101;
			}
		},
		TYPE {// 类型错误
			public Integer value() {
				return 102;
			}
		},
		FORMAT {// 格式错误
			public Integer value() {
				return 103;
			}
		},
		LENGTH {// 长度错误
			public Integer value() {
				return 104;
			}
		},
		REPEAT {// 重复错误
			public Integer value() {
				return 105;
			}
		},
		AUTH {// 认证错误
			public Integer value() {
				return 106;
			}
		},
		LIMIT {// 时限错误
			public Integer value() {
				return 107;
			}
		},
		NOTFOUND {// 未发现错误
			public Integer value() {
				return 108;
			}
		};

	}

}
