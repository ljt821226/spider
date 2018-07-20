package com.jszx.spider.platform.module.info;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月18日 下午2:14:03
 * 
 */

public interface BaseInfo {

	// 可修改，用于所有方法分组
	public static final String O_TAG = "API";

	// 不可修改，所有参数名称
	public static final String P_NAME = "condition";
	
	public static final String PAGE_NAME = "page";
	
	public static final String PAGE_DESCRIPTION = "分页条件";

	/**************************** SELECT ********************************/

	// 可修改，方法短注解
	public static final String SELECT_O_SUMMARY = "单条查询";

	// 可修改，方法长注解
	public static final String SELECT_O_DESCRIPTION = "单条查询";

	// 可修改，方法是否隐藏
	public static final boolean SELECT_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String SELECT_O_PATH = "/select";

	// 不可修改，方法路径
	public static final String SELECT_O_METHOD = "get";

	// 可修改，参数注解
	public static final String SELECT_P_DESCRIPTION = "查询条件";

	// 可修改，参数是否隐藏
	public static final boolean SELECT_P_HIDDEN = false;

	/**************************** SELECT_BATCH ********************************/

	// 可修改，方法短注解
	public static final String SELECT_BATCH_O_SUMMARY = "批量查询";

	// 可修改，方法长注解
	public static final String SELECT_BATCH_O_DESCRIPTION = "批量查询";

	// 可修改，方法是否隐藏
	public static final boolean SELECT_BATCH_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String SELECT_BATCH_O_PATH = "/select/batch";

	// 不可修改，方法路径
	public static final String SELECT_BATCH_O_METHOD = "get";

	// 可修改，参数注解
	public static final String SELECT_BATCH_P_DESCRIPTION = "查询条件";

	// 可修改，参数是否隐藏
	public static final boolean SELECT_BATCH_P_HIDDEN = false;

	/******************************* SELECT_PAGE *****************************/

	// 可修改，方法短注解
	public static final String SELECT_PAGE_O_SUMMARY = "分页查询";

	// 可修改，方法长注解
	public static final String SELECT_PAGE_O_DESCRIPTION = "分页查询";

	// 可修改，方法是否隐藏
	public static final boolean SELECT_PAGE_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String SELECT_PAGE_O_PATH = "/select/page";

	// 不可修改，方法路径
	public static final String SELECT_PAGE_O_METHOD = "get";

	// 可修改，参数注解
	public static final String SELECT_PAGE_P_DESCRIPTION = "查询条件";

	// 可修改，参数是否隐藏
	public static final boolean SELECT_PAGE_P_HIDDEN = false;

	/******************************* INSERT *****************************/

	// 可修改，方法短注解
	public static final String INSERT_O_SUMMARY = "单条增加";

	// 可修改，方法长注解
	public static final String INSERT_O_DESCRIPTION = "单条增加";

	// 可修改，方法是否隐藏
	public static final boolean INSERT_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String INSERT_O_PATH = "/insert";

	// 不可修改，方法路径
	public static final String INSERT_O_METHOD = "post";

	// 可修改，参数注解
	public static final String INSERT_P_DESCRIPTION = "增加数据";

	// 可修改，参数是否隐藏
	public static final boolean INSERT_P_HIDDEN = false;

	/******************************* INSERT_BATCH *****************************/

	// 可修改，方法短注解
	public static final String INSERT_BATCH_O_SUMMARY = "批量增加";

	// 可修改，方法长注解
	public static final String INSERT_BATCH_O_DESCRIPTION = "批量增加";

	// 可修改，方法是否隐藏
	public static final boolean INSERT_BATCH_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String INSERT_BATCH_O_PATH = "/insert/batch";

	// 不可修改，方法路径
	public static final String INSERT_BATCH_O_METHOD = "post";

	// 可修改，参数注解
	public static final String INSERT_BATCH_P_DESCRIPTION = "增加数据";

	// 可修改，参数是否隐藏
	public static final boolean INSERT_BATCH_P_HIDDEN = false;

	/******************************* UPDATE *****************************/

	// 可修改，方法短注解
	public static final String UPDATE_O_SUMMARY = "单条更新";

	// 可修改，方法长注解
	public static final String UPDATE_O_DESCRIPTION = "单条更新";

	// 可修改，方法是否隐藏
	public static final boolean UPDATE_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String UPDATE_O_PATH = "/update";

	// 不可修改，方法路径
	public static final String UPDATE_O_METHOD = "put";

	// 可修改，参数注解
	public static final String UPDATE_P_DESCRIPTION = "更新数据";

	// 可修改，参数是否隐藏
	public static final boolean UPDATE_P_HIDDEN = false;

	/******************************* UPDATE_BATCH *****************************/

	// 可修改，方法短注解
	public static final String UPDATE_BATCH_O_SUMMARY = "批量更新";

	// 可修改，方法长注解
	public static final String UPDATE_BATCH_O_DESCRIPTION = "批量更新";

	// 可修改，方法是否隐藏
	public static final boolean UPDATE_BATCH_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String UPDATE_BATCH_O_PATH = "/update/batch";

	// 不可修改，方法路径
	public static final String UPDATE_BATCH_O_METHOD = "put";

	// 可修改，参数注解
	public static final String UPDATE_BATCH_P_DESCRIPTION = "更新数据";

	// 可修改，参数是否隐藏
	public static final boolean UPDATE_BATCH_P_HIDDEN = false;

	/******************************* DELETE *****************************/

	// 可修改，方法短注解
	public static final String DELETE_O_SUMMARY = "单条删除";

	// 可修改，方法长注解
	public static final String DELETE_O_DESCRIPTION = "单条删除";

	// 可修改，方法是否隐藏
	public static final boolean DELETE_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String DELETE_O_PATH = "/delete";

	// 不可修改，方法路径
	public static final String DELETE_O_METHOD = "delete";

	// 可修改，参数注解
	public static final String DELETE_P_DESCRIPTION = "删除数据";

	// 可修改，参数是否隐藏
	public static final boolean DELETE_P_HIDDEN = false;

	/******************************* DELETE_BATCH *****************************/

	// 可修改，方法短注解
	public static final String DELETE_BATCH_O_SUMMARY = "批量删除";

	// 可修改，方法长注解
	public static final String DELETE_BATCH_O_DESCRIPTION = "批量删除";

	// 可修改，方法是否隐藏
	public static final boolean DELETE_BATCH_O_HIDDEN = false;

	// 不可修改，方法路径
	public static final String DELETE_BATCH_O_PATH = "/delete/batch";

	// 不可修改，方法路径
	public static final String DELETE_BATCH_O_METHOD = "delete";

	// 可修改，参数注解
	public static final String DELETE_BATCH_P_DESCRIPTION = "删除数据";

	// 可修改，参数是否隐藏
	public static final boolean DELETE_BATCH_P_HIDDEN = false;

	/**
	 * 
	 * 方法前缀
	 * 
	 * @version 1.0
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:41:02
	 *
	 */
	public static enum PREFIX {
		SELECT_,
		SELECT_BATCH_,
		SELECT_PAGE_,
		INSERT_,
		INSERT_BATCH_,
		DELETE_,
		DELETE_BATCH_,
		UPDATE_,
		UPDATE_BATCH_;
	}

	/**
	 * 
	 * 操作属性后缀
	 * 
	 * @version 1.0
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:41:18
	 *
	 */
	public static enum SUFFIX_O {
		O_TAG,
		O_SUMMARY,
		O_DESCRIPTION,
		O_HIDDEN;
	}

	/**
	 * 
	 * 参数属性后缀
	 * 
	 * @version 1.0
	 * @author 2724216806@qq.com
	 * @date 2018年4月20日 上午11:41:36
	 *
	 */
	public static enum SUFFIX_P {
		P_DESCRIPTION,
		P_HIDDEN;
	}

}
