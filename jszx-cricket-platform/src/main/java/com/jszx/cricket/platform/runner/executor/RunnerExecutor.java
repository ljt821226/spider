package com.jszx.cricket.platform.runner.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务管理类:提供执行、删除等操作
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年12月28日 下午5:35:31
 * 
 */

public class RunnerExecutor {

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	/**
	 * 
	 * 关闭池
	 * 
	 * @author lv_juntao@uisftech.com
	 * @date 2017年12月28日 下午5:44:24
	 */
	public synchronized static void shutdown() {
		if (executorService != null) {
			executorService.shutdownNow();
		}

	}

	/**
	 * 
	 * 执行任务
	 * 
	 * @param runner
	 * @author lv_juntao@uisftech.com
	 * @date 2017年12月28日 下午5:44:10
	 */
	public static void execute(Runnable runner) {
		if (executorService.isShutdown()) {
			return;
		}
		executorService.execute(runner);
	}

}
