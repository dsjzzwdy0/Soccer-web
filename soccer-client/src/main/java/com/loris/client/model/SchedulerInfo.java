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
import com.loris.common.web.element.WebElement;
import com.loris.common.web.wrapper.WebElements;
import com.loris.common.web.wrapper.WebElementsWrapper;

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
public class SchedulerInfo extends UUIDEntity implements WebElementsWrapper
{
	/** */
	private static final long serialVersionUID = 1L;
	
	public static final String PLUGIN_BEAN = "bean";
	public static final String PLUGIN_CLASS = "class";
	
	public static final String separator = ";";
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
			if(!name.startsWith(PLUGIN_BEAN))
			{
				plugins += PLUGIN_BEAN + ":";
			}
			plugins += name;
		}
		else if(type.equalsIgnoreCase(PLUGIN_CLASS))
		{
			if(!name.startsWith(PLUGIN_CLASS))
			{
				plugins += PLUGIN_CLASS + ":";
			}
			plugins += name;
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
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object) return true;
		if(!(object instanceof SchedulerInfo)) return false;
		SchedulerInfo other = (SchedulerInfo) object;
		if(StringUtils.equals(id, other.id)) return true;
		return StringUtils.equals(name, name);
	}

	/**
	 * 包装
	 *  (non-Javadoc)
	 * @see com.loris.common.web.wrapper.WebElementsWrapper#wrapToWebElements()
	 */
	@Override
	public WebElements wrapToWebElements()
	{
		List<WebElement.Option<Integer, Integer>> threadsOptions = new ArrayList<>();
		threadsOptions.add(new WebElement.Option<Integer, Integer>(1, 1));
		threadsOptions.add(new WebElement.Option<Integer, Integer>(2, 2));
		threadsOptions.add(new WebElement.Option<Integer, Integer>(3, 3));
		threadsOptions.add(new WebElement.Option<Integer, Integer>(4, 4));
		threadsOptions.add(new WebElement.Option<Integer, Integer>(5, 5));
		List<WebElement.Option<Long, Long>> intervalOptions = new ArrayList<>();
		intervalOptions.add(new WebElement.Option<Long, Long>(2000L, 2000L));
		intervalOptions.add(new WebElement.Option<Long, Long>(3000L, 3000L));
		intervalOptions.add(new WebElement.Option<Long, Long>(4000L, 4000L));
		intervalOptions.add(new WebElement.Option<Long, Long>(5000L, 5000L));
		intervalOptions.add(new WebElement.Option<Long, Long>(6000L, 6000L));
		intervalOptions.add(new WebElement.Option<Long, Long>(7000L, 7000L));
		
		WebElements elements = new WebElements();
		elements.addWebElement("sid", "唯一标识", "", false, id, null);
		elements.addWebElement("name", "名称", "", true, name, null);
		elements.addWebElement("type", "类型", "", true, type, null);
		elements.addWebElement("plugins", "插件内容", "", true, plugins, null);
		elements.addWebElement("maxActiveTaskThread", "线程数", "select", true, maxActiveTaskThread, threadsOptions);
		elements.addWebElement("intervaltime", "线程等待时间", "select", true, intervaltime, intervalOptions);
		elements.addWebElement("randTimeSeed", "随机时间", "input", true, randTimeSeed, null);
		return elements;
	}
}
