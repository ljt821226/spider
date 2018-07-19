package com.jszx.cricket.platform.expand.mybatis.interceptor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.jszx.cricket.platform.module.entity.BaseEntity;
import com.jszx.cricket.platform.tool.EntityTool;

/**
 * 结果集拦截类:对返回结果类型为BaseEntity的操作进行拦截封装
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月20日 下午1:59:58
 * 
 */

@Intercepts({ @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }) })
public class ResultInterceptor implements Interceptor {
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
		Field boundSqlField = resultSetHandler.getClass().getDeclaredField("boundSql");
		boundSqlField.setAccessible(true);
		Field mappedStatementField = resultSetHandler.getClass().getDeclaredField("mappedStatement");
		mappedStatementField.setAccessible(true);
		MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(resultSetHandler);
		List<ResultMap> rms = mappedStatement.getResultMaps();
		Statement statement = (Statement) invocation.getArgs()[0]; // 取得方法的参数Statement
		ResultSet rs = statement.getResultSet(); // 取得结果集
		ResultSetMetaData data = rs.getMetaData();
		ResultMap rm = rms != null && !rms.isEmpty() ? rms.get(0) : null;
		if(rm==null){
			return invocation.proceed();
		}else{
			Class<?> rtnClass = rm.getType();
			if (EntityTool.isEntity(rtnClass)) {
				List<BaseEntity> list = new ArrayList<>();
				while (rs.next()) {
					BaseEntity baseEntity = (BaseEntity) rtnClass.newInstance();
					for (int i = 1; i <= data.getColumnCount(); i++) {
						// //获得指定列的列名
						String columnName = data.getColumnName(i);
						// 获得指定列的列值
						String columnValue = rs.getString(i);
						baseEntity.set(columnName, columnValue);
					}
					list.add(baseEntity);
				}
				rs.close();
				return list;
			} else {
				return invocation.proceed();
			}
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// System.out.println(properties.getProperty("databaseType"));
	}

}
