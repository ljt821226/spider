package com.jszx.cricket.platform.runner;

import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * 基础任务类：控制任务启动和停止
 * 
 * @author home
 *
 */
public abstract class BaseRunner implements Runnable {

	// 是否阻塞标识
	private boolean blockFlag = false;

	// 是否运行完成
	private boolean stopFlag = false;

	public BaseRunner(boolean blockFlag) {
		this.blockFlag = blockFlag;
	}

	public void run() {
		try {
			do {
				handle();
			} while (blockFlag);
		} finally {
			stop();
		}
	}

	/**
	 * 运行方法
	 */
	public abstract void handle();

	/**
	 * 停止前方法，用于子类重写
	 */
	protected void beforeStop() {
	}

	/**
	 * 停止后方法，用于子类重写
	 */
	protected void afterStop() {
	}

	/**
	 * 停止方法
	 */
	public final void stop() {
		try {
			beforeStop();
			this.blockFlag = false;
			afterStop();
		} finally {
			this.stopFlag = true;
			this.blockFlag = false;
		}
	}

	/**
	 * @return blockFlag
	 */
	public boolean isBlock() {
		return blockFlag;
	}

	public boolean isStop() {
		return stopFlag;
	}

}
