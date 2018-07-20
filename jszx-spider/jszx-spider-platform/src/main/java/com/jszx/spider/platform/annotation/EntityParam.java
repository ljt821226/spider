package com.jszx.spider.platform.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 实体参数注解:用于获取数据转为BaseEntity类型
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午3:56:41
 *
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityParam {

	String value() default "condition";

}
