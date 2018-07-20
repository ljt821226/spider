package com.jszx.spider.platform.code;

public final class TransactionCode {

	public enum ACTION implements Code<String> {
		PREPARE {
			public String value() {
				return "/tx/prepare";
			}
		},
		SUBMIT {
			public String value() {
				return "/tx/submit";
			}
		},
		ROLLBACK {
			public String value() {
				return "/tx/rollback";
			}
		};
	}

	public enum MARK implements Code<String> {

		TOKEN {
			public String value() {
				return "TX-TOKEN";
			}
		},
		CONDITION {
			public String value() {
				return "condition";
			}
		},
		LOCALHOST {
			public String value() {
				return "system.tx.localhost";
			}

			public String code() {
				return "localhost";
			}
		},
		ROOT {
			public String value() {
				return "TX-ROOT";
			}
		};
	}

	public enum STATUS implements Code<String> {

		SUCCESS {
			public String value() {
				return "SUCCESS";
			}
		},
		UNEXECUTED {
			public String value() {
				return "UNEXECUTED";
			}
		},
		EXECUTED {
			public String value() {
				return "EXECUTED";
			}
		},
		FAILURE {
			public String value() {
				return "FAILURE";
			}
		};
	}

}
