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
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  SchedulerStatus  
 * @Description: 运行状态   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_scheduler_status")
public class SchedulerStatus extends AutoIdEntity
{
	/** */
	private static final long serialVersionUID = 1L;
	
	final public static int STATUS_FINISHED = 1;
	final public static int STATUS_INIT = 0;
	final public static int STATUS_STOP = 2;
	
	protected String sid;
	protected String name;
	protected int maxActiveTaskThread;
	protected int intervaltime;
	protected int randTimeSeed;
	protected String type;
	protected String plugins;
	protected int total;		//总数
	protected int leftsize;		//剩余数
	protected int state;		//1表示处理完成、0表示创建、2表示暂停
	protected String info;		//当前的信息
	protected Date createtime;	//创建时间
	protected Date stoptime;	//停止时间
	protected Date finishtime;	//完成时间
	
	/**
	 * Create a new instance of SchedulerStatus.
	 */
	public SchedulerStatus()
	{
		this.createtime = new Date();
	}
	
	public SchedulerStatus(SchedulerPlugins schedulerInfo)
	{
		this();
		setSchedulerInfo(schedulerInfo);
		this.stoptime = null;
		this.finishtime = null;
	}
	
	public void setSchedulerInfo(SchedulerPlugins info)
	{
		this.sid = info.getId();
		this.intervaltime = info.getIntervaltime();
		this.name = info.getName();
		this.type = info.getType();
		this.maxActiveTaskThread = info.getMaxActiveTaskThread();
		this.randTimeSeed = info.getRandTimeSeed();
		this.plugins = info.getPlugins();
	}
	
	public String getSid()
	{
		return sid;
	}

	public void setSid(String sid)
	{
		this.sid = sid;
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
		setStatus(status.getTotal(),  status.getLeftsize(), status.getState());		
	}
	public Date getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(Date createtime)
	{
		this.stoptime = null;
		this.createtime = createtime;
	}
	public Date getFinishtime()
	{
		return finishtime;
	}
	public void setFinishtime(Date finishtime)
	{
		this.stoptime = null;
		this.finishtime = finishtime;
	}
	public void setFinishtime()
	{
		setFinishtime(new Date());
	}
	
	public void setStoptime()
	{
		setStoptime(new Date());
	}

	public Date getStoptime()
	{
		return stoptime;
	}

	public void setStoptime(Date stoptime)
	{
		this.stoptime = stoptime;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
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

	public String getPlugins()
	{
		return plugins;
	}

	public void setPlugins(String plugins)
	{
		this.plugins = plugins;
	}
	
	/**
	 * 获得任务数据
	 * @return
	 */
	public SchedulerPlugins getSchedulerInfo()
	{
		SchedulerPlugins info = new SchedulerPlugins();
		info.setId(this.sid);
		info.setIntervaltime(intervaltime);
		info.setName(name);
		info.setType(type);
		info.setMaxActiveTaskThread(maxActiveTaskThread);
		info.setRandTimeSeed(randTimeSeed);
		info.setPlugins(plugins);
		return info;
	}
	
	/**
	 * 分解插件信息
	 * @return 插件列表
	 */
	public List<String> getPluginInfos()
	{
		List<String> list = new ArrayList<>();
		String[] strings = plugins.split(SchedulerPlugins.separator);
		for (String string : strings)
		{
			if(StringUtils.isNotBlank(string))
			{
				list.add(string);
			}
		}
		return list;
	}

	@Override
	public String toString()
	{
		return "SchedulerStatus [total=" + total + ", leftsize=" + leftsize + ", createtime=" + createtime + "]";
	}
}
