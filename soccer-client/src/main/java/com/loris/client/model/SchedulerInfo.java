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

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.UUIDEntity;

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
public class SchedulerInfo extends UUIDEntity
{
	/** */
	private static final long serialVersionUID = 1L;
	
	public static final String PLUGIN_BEAN = "bean";
	public static final String PLUGIN_CLASS = "class";
	
	private static final String separator = ";";
	protected String name;
	protected int maxActiveTaskThread;
	protected int intervaltime;
	protected int randTimeSeed;
	protected String type;
	protected String plugins;
	
	public SchedulerInfo()
	{
		this.intervaltime = 200;
		this.randTimeSeed = -100;
		this.maxActiveTaskThread = 5;
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
	public void addPlugin(String name)
	{
		addPlugin("", name);
	}
	public void addPlugin(String type, String name)
	{
		if(StringUtils.isBlank(plugins))
		{
			plugins = "";
		}
		else
		{
			plugins += separator;
		}
		if(type.equalsIgnoreCase(PLUGIN_BEAN))
		{
			plugins += PLUGIN_BEAN + ":" + name;
		}
		else if(type.equalsIgnoreCase(PLUGIN_CLASS))
		{
			plugins += PLUGIN_CLASS + ":" + name;
		}
		else {
			plugins += name;
		}
	}
	
	/**
	 * 分解插件信息
	 * @return 插件列表
	 */
	public List<String> getPluginInfos()
	{
		List<String> list = new ArrayList<>();
		String[] strings = plugins.split(separator);
		for (String string : strings)
		{
			if(StringUtils.isNotBlank(string))
			{
				list.add(string);
			}
		}
		return list;
	}
}
