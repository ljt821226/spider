package com.jszx.cricket.platform.expand.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.cricket.platform.code.SystemCode;
import com.jszx.cricket.platform.module.entity.DatabaseEntity;
import com.jszx.cricket.platform.module.entity.PageEntity;

/**
 * 
 * MyBatis分页拦截器:添加自定分页和技术总条数功能
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:13:57
 *
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {

	private DatabaseEntity dbEntity;

	public PageInterceptor(DatabaseEntity dbEntity) {
		this.dbEntity = dbEntity;
	}

	@SuppressWarnings("unchecked")
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
		boundSql.setAdditionalParameter("db", dbEntity);
		// 分页参数作为参数对象parameterObject的一个属性
		String sql = boundSql.getSql();
		Object obj = boundSql.getParameterObject();
		if (obj instanceof Map) {
			Map<String, Object> paramMap = (Map<String, Object>) obj;
			PageEntity pageEntity = getPageEntity(paramMap);

			if (pageEntity == null) {
				return invocation.proceed();
			} else {
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes();
				HttpServletRequest request = attributes.getRequest();
				if (pageEntity.getCount()) {
					int total = queryTotal(mappedStatement, boundSql);
					pageEntity.set("total", total);
					pageEntity.set("count", true);
					request.setAttribute(SystemCode.COMMON.PAGE.value(), pageEntity);
				}

				// 分页计算,对原始Sql追加limit
				int start = (pageEntity.getStart() - 1 == 0) ? 0 : ((pageEntity.getStart() - 1) * pageEntity.getSize());
				metaObject.setValue("delegate.boundSql.sql", buildPageSql(sql, start, pageEntity.getSize()));
				return invocation.proceed();
			}
		} else {
			return invocation.proceed();
		}
	}

	private String buildPageSql(String sql, int start, int pageSize) {
		StringBuilder sb = new StringBuilder();
		switch (dbEntity.getName()) {
		case "mysql":
			buildPageSqlForMysql(sb, sql, start, pageSize);
			break;
		case "oracle":
			buildPageSqlForOracle(sb, sql, start, pageSize);
			break;
		default:
			buildPageSqlForMysql(sb, sql, start, pageSize);
		}
		return sb.toString();
	}

	/**
	 * 
	 * 构建mysql分页语句
	 * 
	 * @param sb
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 下午3:35:40
	 */
	private void buildPageSqlForMysql(StringBuilder sb, String sql, int start, int pageSize) {
		sb.append(sql);
		sb.append(" limit ").append(start).append(",").append(pageSize);
	}

	/**
	 * 
	 * 构建oracle分页语句
	 * 
	 * @param sb
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @author 2724216806@qq.com
	 * @date 2018年3月19日 下午3:36:03
	 */
	private void buildPageSqlForOracle(StringBuilder sb, String sql, int start, int pageSize) {
		start = (start == 0) ? 1 : (start + 1);
		sb.append("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (");
		sb.append(sql);
		sb.append(") A WHERE ROWNUM <").append(start + pageSize);
		sb.append(") WHERE RN >=").append(start);
	}

	/**
	 * 
	 * 复制BoundSql对象
	 * 
	 * @param ms
	 * @param boundSql
	 * @param sql
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月18日 下午12:30:25
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	/**
	 * 
	 * 计算总条数
	 * 
	 * @param mappedStatement
	 * @param boundSql
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月18日 下午12:30:33
	 */
	private int queryTotal(MappedStatement mappedStatement, BoundSql boundSql) throws Exception {
		Connection connection = null;
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			String originalSql = boundSql.getSql().trim();
			Object parameterObject = boundSql.getParameterObject();
			String countSql = getCountSql(originalSql);
			connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
					countBS);
			parameterHandler.setParameters(countStmt);
			rs = countStmt.executeQuery();
			int total = 0;
			if (rs.next()) {
				total = rs.getInt(1);
			}
			return total;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (countStmt != null) {
				countStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * 
	 * 获取分页实体对象
	 * 
	 * @param paramMap
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月18日 上午10:35:04
	 */
	private PageEntity getPageEntity(Map<String, Object> paramMap) {
		PageEntity pageEntity = null;
		try {
			for (Entry<String, Object> entry : paramMap.entrySet()) {
				Object value = entry.getValue();
				if (value.getClass().equals(PageEntity.class)) {
					pageEntity = (PageEntity) value;
					break;
				}
			}
		} catch (Exception e) {
			pageEntity = null;
		}
		return pageEntity;
	}

	/**
	 * 
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月18日 上午10:34:41
	 */
	private String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
	}

	public Object plugin(Object target) {
		// if (target instanceof StatementHandler) {
		// // StatementHandler handler=(StatementHandler) target;
		// // handler.getBoundSql().setAdditionalParameter("db", dbEntity);
		// return Plugin.wrap(target, this);
		// } else {
		// return target;
		// }
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties prop) {
	}

}
