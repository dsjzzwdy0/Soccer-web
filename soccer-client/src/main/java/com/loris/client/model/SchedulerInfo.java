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
package com.loris.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.client.scheduler.status.SchedulerStatus;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  Scheduler  
 * @Description: 任务调度器状态信息
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_scheduler_info")
public class SchedulerInfo extends AutoIdEntity
{
	/** */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String sid;
	private String name;
	private int maxActiveTaskThread;
	private int intervaltime;
	private int randTimeSeed;
	private String type;
	private int total;			//总数
	private int leftsize;		//剩余数
	private int state;			//1表示处理完成、0表示创建、2表示暂停
	
	/** 插件数据 */
	@TableField(exist=false)
	private List<String> pluginClasses = new ArrayList<>();
	
	public String getSid()
	{
		return sid;
	}
	public void setSid(String sid)
	{
		this.sid = sid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getMaxActiveTaskThread()
	{
		return maxActiveTaskThread;
	}
	public void setMaxActiveTaskThread(int maxActiveTaskThread)
	{
		this.maxActiveTaskThread = maxActiveTaskThread;
	}
	
	public int getIntervaltime()
	{
		return intervaltime;
	}
	public void setIntervaltime(int intervaltime)
	{
		this.intervaltime = intervaltime;
	}
	public int getRandTimeSeed()
	{
		return randTimeSeed;
	}
	public void setRandTimeSeed(int randTimeSeed)
	{
		this.randTimeSeed = randTimeSeed;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getLeftsize()
	{
		return leftsize;
	}
	public void setLeftsize(int leftsize)
	{
		this.leftsize = leftsize;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	
	public void setStatus(int total, int leftsize, int state)
	{
		this.total = total;
		this.leftsize = leftsize;
		this.state = state;
	}
	
	public void setStatus(SchedulerStatus status)
	{
		this.total = status.getTotal();
	}
	
	public List<String> getPluginClasses()
	{
		return pluginClasses;
	}
	public void setPluginClasses(List<String> pluginClasses)
	{
		this.pluginClasses = pluginClasses;
	}
	
	public void addPluginClass(String className)
	{
		pluginClasses.add(className);
	}
}
