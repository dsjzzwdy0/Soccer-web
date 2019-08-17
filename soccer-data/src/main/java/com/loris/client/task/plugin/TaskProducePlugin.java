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
package com.loris.client.task.plugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEventListener;

/**   
 * @ClassName:  TaskProducePlugin  
 * @Description: 任务生产插件类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface TaskProducePlugin extends TaskPlugin
{	
	/**
	 * 检测是否已经初始化
	 * @return 是否初始化的标志
	 */
	boolean isInitialized();
	
	/**
	 * 加入任务事件监听器
	 * @param listener
	 */
	void addTaskEventListener(TaskEventListener listener);
	
	/**
	 * 删除任务事件监听器
	 * @param listener
	 */
	void removeTaskEventListner(TaskEventListener listener);
	
	/**
	 * 加入任务事件监听器队列
	 * @param listeners
	 */
	void addTaskEventListeners(List<TaskEventListener> listeners);
	
	/**
	 * 清除任务事件监听列表
	 */
	void clearTaskEventListners();
	
	/**
	 * 生成新加入的生产任务
	 * @param taskVector 任务容器
	 */
	void produce(TaskPluginContext context) throws IOException, SQLException;
}
