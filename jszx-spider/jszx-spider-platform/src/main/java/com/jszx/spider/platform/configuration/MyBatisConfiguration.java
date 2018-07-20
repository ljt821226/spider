package com.jszx.spider.platform.configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.jszx.spider.platform.code.SystemCode;
import com.jszx.spider.platform.expand.mybatis.cache.RedisCache;
import com.jszx.spider.platform.expand.mybatis.interceptor.PageInterceptor;
import com.jszx.spider.platform.expand.mybatis.interceptor.ParamInterceptor;
import com.jszx.spider.platform.expand.mybatis.interceptor.ResultInterceptor;
import com.jszx.spider.platform.expand.mybatis.type.CommonTypeHandler;
import com.jszx.spider.platform.expand.mybatis.wrapper.EntityWrapperFactory;
import com.jszx.spider.platform.module.entity.DatabaseEntity;
import com.jszx.spider.platform.properties.HttpProperties;
import com.jszx.spider.platform.properties.MybatisProperties;
import com.jszx.spider.platform.properties.OpenapiProperties;
import com.jszx.spider.platform.properties.SwitcherProperties;
import com.jszx.spider.platform.tool.SpringTool;
import com.jszx.spider.platform.tool.StringTool;

/**
 * 
 * MyBatis配置类:配置MyBatis基本属性
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:09:48
 *
 */

@Configuration
//@ConditionalOnExpression("${jszx.cosmos.switcher.database:true}")
//@EnableConfigurationProperties(MybatisProperties.class)
//@AutoConfigureOrder(2)
public class MyBatisConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);

	private static final String XML = "classpath*:com/jszx/**/dao/*Dao.xml";

	private static final String DAO = "com.jszx.**.dao";

	private static final String ENTITY = "com.jszx.**.entity";
	
	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	private  MybatisProperties mybatisProperties;

	@Autowired
	private SwitcherProperties switcherProperty;

	@Bean("sqlSessionFactoryBean")
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
		try {
			LogFactory.useSlf4jLogging();
			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			bean.setObjectWrapperFactory(new EntityWrapperFactory());
			bean.setDataSource(dataSource);
			DatabaseEntity dbEntity = new DatabaseEntity();
			buildDataBaseEntity(dataSource, dbEntity);
			// 实体扫描
			bean.setTypeAliasesPackage(getModel());
			bean.setConfiguration(buildConfigutation());
			// 设置类型转换
			bean.setTypeHandlers(new TypeHandler<?>[] { new CommonTypeHandler() });
			// 添加XML目录
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			bean.setMapperLocations(resolver.getResources(getXml()));
			// 分页插件
			bean.setPlugins(new Interceptor[] { new PageInterceptor(dbEntity), new ResultInterceptor() });
			return bean.getObject();
		} catch (Exception e) {
			logger.error("Mybatis初始化异常，请检查...", e);
			throw new RuntimeException(e);
		}
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		msc.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		msc.setBasePackage(getDao());
		msc.afterPropertiesSet();
		return msc;
	}

	/**
	 * 
	 * 构建Configuration
	 * 
	 * @param dbEntity
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月16日 下午4:10:06
	 */
	private org.apache.ibatis.session.Configuration buildConfigutation() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCallSettersOnNulls(true);
		configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
//		if (true) {
//			configuration.setCacheEnabled(true);
//			configuration.setMapUnderscoreToCamelCase(true);
//			configuration.addCache(new RedisCache("_MYBATIS_"));
//		}
		return configuration;
	}

	/**
	 * 
	 * 设置数据库信息
	 * 
	 * @param dataSource
	 * @param dbEntity
	 * @throws SQLException
	 * @author 2724216806@qq.com
	 * @date 2018年3月16日 下午4:10:16
	 */
	private void buildDataBaseEntity(DataSource dataSource, DatabaseEntity dbEntity) throws SQLException {
		Connection conn = null;
		try {
			if (1==1) {
				conn = dataSource.getConnection();
				DatabaseMetaData dm = conn.getMetaData();
				dbEntity.setName(dm.getDatabaseProductName().toLowerCase());
				dbEntity.setVersion(dm.getDatabaseProductVersion().toLowerCase());
			} else {
				dbEntity.setName("null");
				dbEntity.setVersion("null");
			}
			System.setProperty(SystemCode.DATABASE.JSON.name(), dbEntity.toJson());
			System.setProperty(SystemCode.DATABASE.NAME.name(), dbEntity.getName());
			System.setProperty(SystemCode.DATABASE.VERSION.name(), dbEntity.getVersion());
			logger.info("[platform]:数据库信息设置：name=" + dbEntity.getName() + ";version=" + dbEntity.getVersion());
		} catch (Exception e) {
			logger.error("[platform]:数据库信息设置异常，请检查...", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	private String getXml() {
		StringBuilder sb = new StringBuilder();
		sb.append(XML);
		if(mybatisProperties!=null){
			build(sb, mybatisProperties.getXml());
		}
		return sb.toString();
	}

	private String getDao() {
		StringBuilder sb = new StringBuilder();
		sb.append(DAO);
		String abc=SpringTool.getProperty("jszx.cricket.mybatis.dao");
		if(mybatisProperties!=null){
			build(sb, mybatisProperties.getDao());
		}
		return sb.toString();
	}

	private String getModel() {
		StringBuilder sb = new StringBuilder();
		sb.append(ENTITY);
		if(mybatisProperties!=null){
			build(sb, mybatisProperties.getEntity());
		}
		return sb.toString();
	}

	private StringBuilder build(StringBuilder sb, String[] values) {
		if (values != null && values.length > 0) {
			for (String value : values) {
				sb.append(",");
				sb.append(value);
			}
		}
		return sb;
	}
}
