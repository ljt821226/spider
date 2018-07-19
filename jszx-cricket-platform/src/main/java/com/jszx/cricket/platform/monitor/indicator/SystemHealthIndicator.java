package com.jszx.cricket.platform.monitor.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年4月12日 下午6:06:08
 * 
 */

@Component
public class SystemHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
          return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

      private int check() {
         // 对监控对象的检测操作
    	  return 1;
      }
}