package com.jszx.cricket.platform.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 事务注解类:用于分布式事务控制
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午3:57:59
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransactionLauncher {

	String name() default "";

	String code() default "";

	String value() default "";

}
