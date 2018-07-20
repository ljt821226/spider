package com.jszx.spider.platform.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 框架注解:实现统一注解
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年2月22日 下午11:06:09
 * 
 */

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
public @interface ApplicationStarter {

}
