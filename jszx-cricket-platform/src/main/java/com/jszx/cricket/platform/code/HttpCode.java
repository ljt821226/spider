package com.jszx.cricket.platform.code;

public interface HttpCode {

	public enum HEADER implements Code<String> {

		ACCESS_CONTROL_ALLOW_ORIGIN {
			public String value() {
				return "*";
			}

			public String code() {
				return "Access-Control-Allow-Origin";
			}
		},
		ACCESS_CONTROL_ALLOW_HEADERS {
			public String value() {
				return "User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken,X-Requested-With,Accept,auth-ticket,auth-token";
			}

			public String code() {
				return "Access-Control-Allow-Headers";
			}
		},
		ACCESS_CONTROL_ALLOW_CREDENTIALS {
			public String value() {
				return "true";
			}

			public String code() {
				return "Access-Control-Allow-Credentials";
			}
		},
		ACCESS_CONTROL_ALLOW_METHODS {
			public String value() {
				return "GET, POST, PUT, DELETE, OPTIONS, HEAD";
			}

			public String code() {
				return "Access-Control-Allow-Methods";
			}
		},
		ACCESS_CONTROL_MAX_AGE {
			public String value() {
				return "1209600";
			}

			public String code() {
				return "Access-Control-Max-Age";
			}
		},
		ACCESS_CONTROL_EXPOSE_HEADERS {
			public String value() {
				return "accesstoken";
			}

			public String code() {
				return "Access-Control-Expose-Headers";
			}
		},
		ACCESS_CONTROL_REQUEST_HEADERS {
			public String value() {
				return "accesstoken";
			}

			public String code() {
				return "Access-Control-Request-Headers";
			}
		},
		EXPIRES {
			public String value() {
				return "-1";
			}

			public String code() {
				return "Expires";
			}
		},
		CACHE_CONTROL {
			public String value() {
				return "no-cache";
			}

			public String code() {
				return "Cache-Control";
			}
		},
		PRAGMA {
			public String value() {
				return "no-cache";
			}

			public String code() {
				return "pragma";
			}
		};

	}

	public enum CONTENT_TYPE implements Code<String> {
		APPLICATION_JSON {
			public String value() {
				return "application/json";
			}
		},
		APPLICATION_XML {
			public String value() {
				return "application/xml";
			}
		},
		APPLICATION_FORM_URLENCODED {
			public String value() {
				return "application/x-www-form-urlencoded";
			}
		},
		MULTIPART_FORM_DATA {
			public String value() {
				return "multipart/form-data";
			}
		},
		APPLICATION_ATOM_XML {
			public String value() {
				return "application/atom+xml";
			}
		},
		APPLICATION_XHTML_XML {
			public String value() {
				return "application/xhtml+xml";
			}
		},
		APPLICATION_SVG_XML {
			public String value() {
				return "application/svg+xml";
			}
		},
		APPLICATION_OCTET_STREAM {
			public String value() {
				return "application/octet-stream";
			}
		},
		TEXT_CSS {
			public String value() {
				return "text/css";
			}
		},
		TEXT_JAVASCRIPT {
			public String value() {
				return "text/javascript";
			}
		},
		TEXT_PLAIN {
			public String value() {
				return "text/plain";
			}
		},
		TEXT_XML {
			public String value() {
				return "text/xml";
			}
		},
		TEXT_HTML {
			public String value() {
				return "text/html";
			}
		};
	}

}
