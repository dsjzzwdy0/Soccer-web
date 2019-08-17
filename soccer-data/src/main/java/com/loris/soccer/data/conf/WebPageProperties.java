/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebPageConf.java   
 * @Package com.loris.soccer.data.conf   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**   
 * @ClassName: WebPageConf   
 * @Description: 页面配置数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebPageProperties
{
	/** 设置WebPageFilter从数据库中获取下载页面的天数 */
	protected int numDayfGetPages = 10;
	
	/** 当前之后的多少天的比赛有赔率 */
	protected int numDayOfHasOdds = 4;
	
	/** 页面更新的时间， 按照秒为单位*/
	protected Map<String, Long> pageUpdateIntervalTime = new HashMap<>();
	
	/** 页面产生新的任务,默认是都不创建 */
	protected Map<String, Boolean> pageProduceNewTask = new HashMap<>();
	
	/** 产生页面下载任务，默认皆为被创建 */
	protected Map<String, Boolean> pageBeCreated = new HashMap<>();
	
	/**
	 * 按照秒为单位
	 * @param type
	 * @return
	 */
	public Long getPageUpdateIntervalTime(String type)
	{
		return pageUpdateIntervalTime.get(type);
	}
	
	public void setPageUpdateIntervalTime(String type, Long time)
	{
		pageUpdateIntervalTime.put(type, time);
	}

	public Map<String, Long> getPageUpdateIntervalTime()
	{
		return pageUpdateIntervalTime;
	}

	public void setPageUpdateTime(Map<String, Long> pageUpdateTime)
	{
		this.pageUpdateIntervalTime.putAll(pageUpdateTime);
	}

	public Map<String, Boolean> getPageProduceNewTask()
	{
		return pageProduceNewTask;
	}

	public void setPageProduceNewTask(Map<String, Boolean> pageToProduceTask)
	{
		this.pageProduceNewTask.putAll(pageToProduceTask);
	}

	public Map<String, Boolean> getPageBeCreated()
	{
		return pageBeCreated;
	}

	public void setPageBeCreated(Map<String, Boolean> pageBeCreated)
	{
		this.pageBeCreated.putAll(pageBeCreated);
	}

	/**
	 * 检测是否产生新的任务
	 * @param type 页面类型
	 * @return 是否检测的标志
	 */
	public boolean isPageProduceNewTask(String type)
	{
		if(StringUtils.isBlank(type)) return false;
		Boolean b = pageProduceNewTask.get(type);
		return b == null ? false : b;
	}
	
	/**
	 * 设置是否产生新的任务
	 * @param type 类型
	 * @param b 标志
	 */
	public void setPageProduceNewTask(String type, boolean b)
	{
		pageProduceNewTask.put(type, b);
	}
	
	/**
	 * 设置是否产生新的任务
	 * @param type 类型
	 */
	public void setPageProduceNewTask(String type)
	{
		pageProduceNewTask.put(type, true);
	}
	
	/**
	 * 设置是否产生新的页面
	 * @param type
	 * @param b
	 */
	public void setProducePage(String type)
	{
		pageBeCreated.put(type, true);
	}
	
	/**
	 * 设置是否产生新的页面
	 * @param type
	 * @param b
	 */
	public void setPageBeCreated(String type, boolean b)
	{
		pageBeCreated.put(type, b);
	}
	
	/**
	 * 是否产生新的页面
	 * @param type 类型
	 * @return 产生的标志
	 */
	public boolean isPageBeCreated(String type)
	{
		if(StringUtils.isBlank(type)) return false;
		Boolean b = pageBeCreated.get(type);
		return b == null ? true : b;
	}

	public int getDayNumOfGetPages()
	{
		return numDayfGetPages;
	}

	public void setDayNumOfGetPages(int dayNumOfGetPages)
	{
		this.numDayfGetPages = dayNumOfGetPages;
	}
	
	public int getNumDayOfHasOdds()
	{
		return numDayOfHasOdds;
	}

	public void setNumDayOfHasOdds(int numDayOfHasOdds)
	{
		this.numDayOfHasOdds = numDayOfHasOdds;
	}

	public void setWebPageProperties(WebPageProperties properties)
	{
		numDayfGetPages = properties.numDayfGetPages;
		numDayOfHasOdds = properties.numDayOfHasOdds;
		pageUpdateIntervalTime.clear();
		if(properties.getPageUpdateIntervalTime().size() > 0)
			pageUpdateIntervalTime.putAll(properties.getPageUpdateIntervalTime());
		pageProduceNewTask.clear();
		if(properties.getPageProduceNewTask().size() > 0)
			pageProduceNewTask.putAll(properties.getPageProduceNewTask());
		pageBeCreated.clear();
		if(properties.getPageBeCreated().size() > 0)
			pageBeCreated.putAll(properties.getPageBeCreated());
	}
}
