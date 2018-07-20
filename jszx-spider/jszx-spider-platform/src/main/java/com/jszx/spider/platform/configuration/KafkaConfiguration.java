package com.jszx.spider.platform.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * 
 * kafka配置类:启动kafaka
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年3月16日 下午4:09:01
 *
 */

@Configuration
@EnableKafka
@ConditionalOnExpression("${kasaya.kafka.launch:false}")
public class KafkaConfiguration {

}
