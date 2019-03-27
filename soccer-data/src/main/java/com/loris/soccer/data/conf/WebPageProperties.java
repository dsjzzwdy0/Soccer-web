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

import com.loris.soccer.data.zgzcw.ZgzcwConstants;

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
	protected Map<String, Long> pageUpdateTime = new HashMap<>();
	
	/** 页面产生新的任务,默认是都不创建 */
	protected Map<String, Boolean> pageProduceTask = new HashMap<>();
	
	/** 产生页面下载任务，默认皆为被创建 */
	protected Map<String, Boolean> pageBeCreated = new HashMap<>();
	
	/**
	 * 按照秒为单位
	 * @param type
	 * @return
	 */
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
		pageUpdateTime.clear();
		if(properties.getPageUpdateTime().size() > 0)
			pageUpdateTime.putAll(properties.getPageUpdateTime());
		pageProduceTask.clear();
		if(properties.getPageProduceTask().size() > 0)
			pageProduceTask.putAll(properties.getPageProduceTask());
		if(properties.getPageBeCreated().size() > 0)
			pageBeCreated.putAll(properties.getPageBeCreated());
	}
	
	/**
	 * 配置默认的信息
	 * @return
	 */
	public static WebPageProperties getDefault()
	{
		WebPageProperties properties = new WebPageProperties();
		properties.numDayfGetPages = 10;
		properties.numDayOfHasOdds = 4;
		Long oddsUpdateTime = 4 * 60 * 60L;								//赔率页面更新时间：4小时
		Long leagueUpdateTime = 24 * 60 * 60L;							//联赛页面更新时间：1天
		Long realPageUpdateTime = oddsUpdateTime;						//实时页面更新时间：4小时
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_ODDS_OP, oddsUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_ODDS_YP, oddsUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_ODDS_NUM, oddsUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_LEAGUE_LEAGUE, leagueUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_LEAGUE_CUP, leagueUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_LOTTERY_JC, realPageUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_LOTTERY_BD, realPageUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_SCORE_BD, realPageUpdateTime);
		properties.setPageUpdateTime(ZgzcwConstants.PAGE_SCORE_JC, realPageUpdateTime);
		
		properties.setPageProduceNewTask(ZgzcwConstants.PAGE_LEAGUE_LEAGUE, true);
		properties.setPageProduceNewTask(ZgzcwConstants.PAGE_LEAGUE_CUP, true);
		properties.setPageProduceNewTask(ZgzcwConstants.PAGE_LOTTERY_JC, true);
		properties.setPageProduceNewTask(ZgzcwConstants.PAGE_LOTTERY_BD, true);
		properties.setPageProduceNewTask(ZgzcwConstants.PAGE_SCORE_BD, true);
		properties.setPageProduceNewTask(ZgzcwConstants.PAGE_SCORE_JC, true);
		return properties;
	}
}
