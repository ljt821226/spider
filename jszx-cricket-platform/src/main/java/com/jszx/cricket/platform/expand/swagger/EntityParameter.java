package com.jszx.cricket.platform.expand.swagger;

import java.util.Objects;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月9日 上午10:06:12
 * 
 */

public class EntityParameter extends Parameter {

	private String in = "query";

	/**
	 * returns the in property from a EntityParameter instance.
	 *
	 * @return String in
	 **/

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public EntityParameter in(String in) {
		this.in = in;
		return this;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EntityParameter entityParameter = (EntityParameter) o;
		return Objects.equals(this.in, entityParameter.in) && super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(in, super.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class EntityParameter {\n");
		sb.append("    ").append(toIndentedString(super.toString())).append("\n");
		sb.append("    in: ").append(toIndentedString(in)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
