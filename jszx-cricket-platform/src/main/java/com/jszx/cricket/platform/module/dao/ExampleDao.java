package com.jszx.cricket.platform.module.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jszx.cricket.platform.module.entity.ExampleEntity;
import com.jszx.cricket.platform.module.entity.PageEntity;

/**
 * Dao实例类:提供基本操作方法示例
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年4月20日 下午3:46:12
 * 
 */

@Repository("com.jszx.cricket.platform.dao.example")
public interface ExampleDao {

	/**
	 * 
	 * 单条查询
	 * 
	 * @param entity
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:53:50
	 */
	public ExampleEntity select(ExampleEntity entity);

	/**
	 * 
	 * 批量查询
	 * 
	 * @param entity
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:54:02
	 */
	public ExampleEntity[] selectBatch(ExampleEntity entity);

	/**
	 * 
	 * 分页查询
	 * 
	 * @param entity
	 * @param page
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:54:16
	 */
	public ExampleEntity[] selectPage(@Param("entity") ExampleEntity entity, @Param("page") PageEntity page);

	/**
	 * 
	 * 单条插入
	 * 
	 * @param entity
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:54:26
	 */
	public int insert(ExampleEntity entity);

	/**
	 * 
	 * 批量插入
	 * 
	 * @param entities
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:54:37
	 */
	public int insertBatch(ExampleEntity[] entities);

	/**
	 * 
	 * 单条删除
	 * 
	 * @param entity
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:54:51
	 */
	public int delete(ExampleEntity entity);

	/**
	 * 
	 * 批量删除
	 * 
	 * @param entities
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:55:04
	 */
	public int deleteBatch(ExampleEntity[] entities);

	/**
	 * 
	 * 单条更新
	 * 
	 * @param entity
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:55:15
	 */
	public int update(ExampleEntity entity);

	/**
	 * 
	 * 批量更新
	 * 
	 * @param entities
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月18日 上午11:55:24
	 */
	public int updateBatch(ExampleEntity[] entities);

}
