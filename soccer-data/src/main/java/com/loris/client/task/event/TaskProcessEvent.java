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
package com.loris.client.task.event;

import com.loris.client.task.Task;

/**   
 * @ClassName:  TaskprocessEvent  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TaskProcessEvent
{
	public static enum TaskProcessEventType{
		Success,
		Failed
	}
	
	TaskProcessEventType type;
	Task task;
	
	public TaskProcessEvent(Task task, TaskProcessEventType type)
	{
		this.task = task;
		this.type = type;
	}

	public TaskProcessEventType getType()
	{
		return type;
	}

	public void setType(TaskProcessEventType type)
	{
		this.type = type;
	}

	public Task getTask()
	{
		return task;
	}

	public void setTask(Task task)
	{
		this.task = task;
	}
}
