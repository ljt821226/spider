package com.jszx.cricket.platform.expand.mybatis.interceptor;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月21日 上午10:17:47
 * 
 */

@Intercepts({ @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class) })
public class ParamInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
		PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
		MetaObject metaObject = SystemMetaObject.forObject(parameterHandler);
		Field parameterObjectField=parameterHandler.getClass().getDeclaredField("parameterObject");
		parameterObjectField.setAccessible(true);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("token", "123");
		parameterObjectField.set(parameterHandler, map);
		Object parameterObject=parameterObjectField.get(parameterHandler);
		//		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		 Field boundSqlField = parameterHandler.getClass().getDeclaredField("boundSql");
	        boundSqlField.setAccessible(true);
	        BoundSql boundSql = (BoundSql) boundSqlField.get(parameterHandler);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					String propertyName = parameterMapping.getProperty();
					String propertyName2 = parameterMapping.getProperty();
					Object value;
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
