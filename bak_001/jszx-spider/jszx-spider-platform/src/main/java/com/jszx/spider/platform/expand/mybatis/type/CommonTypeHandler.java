package com.jszx.spider.platform.expand.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.ObjectTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.spider.platform.tool.EntityTool;
import com.jszx.spider.platform.tool.JsonTool;


/**
 * 
 * Entity转换类:Entity转为Json字符串
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年3月16日 下午5:14:41
 *
 */

public class CommonTypeHandler extends ObjectTypeHandler {

	private static final Logger logger = LoggerFactory.getLogger(CommonTypeHandler.class);

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
			throws SQLException {

		if (EntityTool.isEntity(parameter) || (parameter instanceof List)) {
			try {
				ps.setObject(i, JsonTool.toJson(parameter));
			} catch (Exception e) {
				logger.error("类型转换失败，请检查...", e);
				ps.setObject(i, parameter);
			}
		} else {
			ps.setObject(i, parameter);
		}
	}

}
