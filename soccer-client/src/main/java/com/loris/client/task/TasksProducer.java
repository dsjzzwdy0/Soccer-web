/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.task;

/**
 * @ClassName: TaskProducer
 * @Description: 任务生成器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface TasksProducer extends Runnable
{
	/**
	 * 获得TaskProducer的名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 是否已经初始化
	 * 
	 * @return
	 */
	boolean isInitialized();

	/**
	 * 初始化任务生成器
	 */
	boolean init();

	/**
	 * 任务运行器
	 */
	@Override
	void run();

	/**
	 * 生成一个任务
	 * 
	 * @return 生成一个需要处理的任务
	 */
	Task popup();

	/**
	 * 加入一个任务
	 * 
	 * @param task
	 */
	void push(Task task);
	
	/**
	 * 总的任务数量
	 * @return 任务总数
	 */
	int total();
	
	/**
	 * 剩余的任务数
	 * @return 剩余任务数
	 */
	int leftSize();
	
	/**
	 * 是否停止处理器
	 * @return 是否停止
	 */
	boolean isStopped();
	
	/**
	 * 是否有更多的任务需要处理
	 * @return 是否有任务的标志
	 */
	boolean hasMoreTask();
	
	/**
	 * 处理下一个任务的间隔时间
	 * @return 间隔时间
	 */
	int interval();
	
	/**
	 * 是否需要加入随机等待时间
	 * @return
	 */
	boolean needRandTime();
	
	/**
	 * 随机时间的种子数
	 * @return 随机时间的种子数
	 */
	int randTimeSeed();
}
