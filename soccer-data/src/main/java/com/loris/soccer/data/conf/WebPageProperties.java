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
	protected int dayNumOfGetPages = 3;
	
	/** 页面更新的时间 */
	protected Map<String, Long> pageUpdateTime = new HashMap<>();
	
	/** 页面产生新的任务 */
	protected Map<String, Boolean> pageProduceTask = new HashMap<>();
	
	/** 产生页面下载任务 */
	protected Map<String, Boolean> producePage = new HashMap<>();
	
	
	public Long getPageUpdateTime(String type)
	{
		return pageUpdateTime.get(type);
	}
	
	public void setPageUpdateTime(String type, Long time)
	{
		pageUpdateTime.put(type, time);
	}

	public Map<String, Long> getPageUpdateTime()
	{
		return pageUpdateTime;
	}

	public void setPageUpdateTime(Map<String, Long> pageUpdateTime)
	{
		this.pageUpdateTime.putAll(pageUpdateTime);
	}

	public Map<String, Boolean> getPageProduceTask()
	{
		return pageProduceTask;
	}

	public void setPageProduceTask(Map<String, Boolean> pageToProduceTask)
	{
		this.pageProduceTask.putAll(pageToProduceTask);
	}

	public Map<String, Boolean> getProducePage()
	{
		return producePage;
	}

	public void setProducePage(Map<String, Boolean> producePage)
	{
		this.producePage.putAll(producePage);;
	}
	
	/**
	 * 检测是否产生新的任务
	 * @param type 页面类型
	 * @return 是否检测的标志
	 */
	public boolean isPageProduceNewTask(String type)
	{
		if(StringUtils.isBlank(type)) return false;
		Boolean b = pageProduceTask.get(type);
		return b == null ? false : b;
	}
	
	/**
	 * 设置是否产生新的任务
	 * @param type 类型
	 * @param b 标志
	 */
	public void setPageProduceNewTask(String type, boolean b)
	{
		pageProduceTask.put(type, b);
	}
	
	/**
	 * 设置是否产生新的任务
	 * @param type 类型
	 */
	public void setPageProduceNewTask(String type)
	{
		pageProduceTask.put(type, true);
	}
	
	/**
	 * 设置是否产生新的页面
	 * @param type
	 * @param b
	 */
	public void setProducePage(String type)
	{
		producePage.put(type, true);
	}
	
	/**
	 * 设置是否产生新的页面
	 * @param type
	 * @param b
	 */
	public void setProducePage(String type, boolean b)
	{
		producePage.put(type, b);
	}
	
	/**
	 * 是否产生新的页面
	 * @param type 类型
	 * @return 产生的标志
	 */
	public boolean isProducePage(String type)
	{
		if(StringUtils.isBlank(type)) return false;
		Boolean b = producePage.get(type);
		return b == null ? true : b;
	}

	public int getDayNumOfGetPages()
	{
		return dayNumOfGetPages;
	}

	public void setDayNumOfGetPages(int dayNumOfGetPages)
	{
		this.dayNumOfGetPages = dayNumOfGetPages;
	}
	
	public void setWebPageProperties(WebPageProperties properties)
	{
		dayNumOfGetPages = properties.dayNumOfGetPages;
		pageUpdateTime.clear();
		if(properties.getPageUpdateTime().size() > 0)
			pageUpdateTime.putAll(properties.getPageUpdateTime());
		pageProduceTask.clear();
		if(properties.getPageProduceTask().size() > 0)
			pageProduceTask.putAll(properties.getPageProduceTask());
		if(properties.getProducePage().size() > 0)
			producePage.putAll(properties.getProducePage());
	}
}
