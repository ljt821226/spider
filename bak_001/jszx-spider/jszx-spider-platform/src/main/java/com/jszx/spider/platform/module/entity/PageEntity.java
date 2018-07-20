package com.jszx.spider.platform.module.entity;

/**
 * 
 * 分页实体类
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:05:55
 *
 */
public class PageEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 起始页数
	private int start = 1;

	// 每页查询数量
	private int size = 15;

	// 总条数
	private int total = -1;

	// 是否计算总条数
	private Boolean count = true;

	/**
	 * @return start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start
	 *            要设置的 start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            要设置的 size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            要设置的 total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return count
	 */
	public Boolean getCount() {
		return count;
	}

	/**
	 * @param count
	 *            要设置的 count
	 */
	public void setCount(Boolean count) {
		this.count = count;
	}

}
