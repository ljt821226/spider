package com.jszx.spider.platform.module.entity;

/**
 * 
 * 系统实体类
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:04:43
 *
 */

public class SystemEntity extends LogEntity {

	private static final long serialVersionUID = 1L;

	// 耗时
	private long totalTime = 0;

	// 开始时间
	private String startTime;

	// 结束时间
	private String endTime;

	// 客户端地址
	private String clientHost;

	// 总占用内存
	private long totalMemory = 0;

	// 空闲内存
	private long freeMemory = 0;

	// 最大内存,-Xmx
	private long maxMemory = 0;

	// 已用内存
	private long usedMemory = 0;

	/**
	 * @return totalTime
	 */
	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            要设置的 totalTime
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            要设置的 startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            要设置的 endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return clientHost
	 */
	public String getClientHost() {
		return clientHost;
	}

	/**
	 * @param clientHost
	 *            要设置的 clientHost
	 */
	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	/**
	 * @return totalMemory
	 */
	public long getTotalMemory() {
		return totalMemory;
	}

	/**
	 * @param totalMemory
	 *            要设置的 totalMemory
	 */
	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	/**
	 * @return freeMemory
	 */
	public long getFreeMemory() {
		return freeMemory;
	}

	/**
	 * @param freeMemory
	 *            要设置的 freeMemory
	 */
	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	/**
	 * @return maxMemory
	 */
	public long getMaxMemory() {
		return maxMemory;
	}

	/**
	 * @param maxMemory
	 *            要设置的 maxMemory
	 */
	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	/**
	 * @return usedMemory
	 */
	public long getUsedMemory() {
		this.usedMemory = this.totalMemory - this.freeMemory;
		return this.usedMemory;
	}

	/**
	 * @param usedMemory
	 *            要设置的 usedMemory
	 */
	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

}
