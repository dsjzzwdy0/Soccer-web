/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebPageFilter.java   
 * @Package com.loris.soccer.data.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.filter;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;

import com.loris.client.model.WebPage;
import com.loris.common.util.ToolUtil;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.filter.WebPageFilter;
import com.loris.soccer.model.base.MatchItem;

import static com.loris.soccer.data.zgzcw.ZgzcwConstants.*;

/**   
 * @ClassName:  WebPageFilter    
 * @Description: 已经下载的网络页面过滤器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcBasePageFilter extends WebPageFilter
{
	public static final long MINUS_OF_HOUR = 3600000L;	
	
	/** 页面配置信息 */
	protected WebPageProperties webPageConf;
	
	/**
	 * Create a new instance of DownloadedWebPageFilter.
	 */
	public ZgzcBasePageFilter()
	{
		this(WebPageProperties.getDefault());
	}
	
	/**
	 * Create a new instance of DownloadedWebPageFilter.
	 * @param types 数据类型
	 * @param source 数据来源
	 * @param start 开始日期
	 * @param end 结束日期
	 */
	public ZgzcBasePageFilter(WebPageProperties webPageconf)
	{
		this.webPageConf = webPageconf;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.filter.WebPageFilter#accept(com.loris.client.model.WebPage, java.lang.Object)
	 */
	@Override
	public <T> boolean accept(WebPage page, T source)
	{
		String type = page.getType();
		if(!types.contains(type)) return false;		
		if(existWebPages == null || existWebPages.size() == 0) return true;
		
		for (WebPage existPage : existWebPages)
		{
			if(page.equals(existPage) && !needToReload(page, existPage, source))
			{
				return false;
			}
		}		
		return true;
	}
	
	/**
	 * 检测与判断是否需要对页面进行重新下载，需要重新下载的几种条件如下：
	 * 1. 联赛与杯赛首页面： 超过设定的时间，如1天，则需要重新进行下载;
	 * 2. 联赛轮次页面：已经下载的，不进行更新
	 * 3. 赔率页面（含欧赔、亚盘、大小球）：
	 *    第一种情况：比赛已经开始或已经结束的，并且已经下载过的，不需要重新进行下载；未下载过的，需要进行下载
	 *    第二种情况：比赛还未开始的，未下载过的，需要进行下载；下载时间距离当前时间超过设定时间域值的，如2小时，
	 *    则需要重新进行下载。
	 * 
	 * @param page 当前页面
	 * @param existPage 存在的页面
	 * @param source 数据来源
	 * @return 是否存在的标示 
	 */
	protected<T> boolean needToReload(WebPage page, WebPage existPage, T source)
	{
		if(ToolUtil.isEmpty(existPage))
		{
			return true;
		}
		String type = page.getType();
		if(StringUtils.isEmpty(type))
		{
			return true;
		}
		Date loadtime = existPage.getLoadtime();
		if(ToolUtil.isEmpty(existPage) || ToolUtil.isEmpty(loadtime))
		{
			return true;
		}
		
		Long timeThread = webPageConf.getPageUpdateIntervalTime(type);
		if(timeThread == null || timeThread < 10L)		//域值设置小于10秒钟的，需要更新
		{
			return true;
		}
		long curTimeToLoadedTime = (System.currentTimeMillis() - loadtime.getTime()) / 1000;
		if(curTimeToLoadedTime < timeThread) return false;
		switch (type)
		{
		case PAGE_ODDS_YP:
		case PAGE_ODDS_OP:
		case PAGE_ODDS_NUM:
			if(source != null && (source instanceof MatchItem))
			{
				Date matchTime = ((MatchItem)source).getMatchtime();
				if(ToolUtil.isNotEmpty(matchTime))
				{
					return checkNeedToReloadMatchOddsPage(matchTime, loadtime);
				}
			}
			break;
		}
		return true;
	}
	
	/**
	 * 检测是否需要对比赛赔率页面进行更新，计算公式如下：
	 * 时间差值＝比赛时间－当前时间
	 * 时间差值在2天以上的，更新周期为24小时
	 * 时间差值在1~2天的，更新周期为18小时
	 * 时间差值在18~24小时的，更新周期为12小时
	 * 时间差值在12~18小时的，更新周期为6小时
	 * 时间差值在6~12小时的，更新周期为4小时
	 * 时间差值在3~6小时的，更新周期为3小时
	 * 时间差值在2~3小时的，更新周期为2小时
	 * 时间差值在0.3~2小时的，更新周期为1小时
	 * 时间差值在0.3小时以内的，不需要更新
	 * @param matchTime 比赛时间
	 * @param loadTime 最近
	 * @return
	 */
	protected boolean checkNeedToReloadMatchOddsPage(Date matchTime, Date loadTime)
	{
		long currentTime = System.currentTimeMillis();
		double timeDistToMatchTime =((double) (matchTime.getTime() - currentTime)) / MINUS_OF_HOUR;
		double timeDistToLoadTime = ((double)(currentTime - loadTime.getTime())) / MINUS_OF_HOUR;
		
		if(timeDistToMatchTime > 96) return false;
		else if(timeDistToMatchTime > 48) return timeDistToLoadTime > 36;
		else if(timeDistToMatchTime > 36) return timeDistToLoadTime > 24;
		else if(timeDistToMatchTime > 24) return timeDistToLoadTime > 18;
		else if(timeDistToMatchTime > 18) return timeDistToLoadTime > 12;
		else if(timeDistToMatchTime > 12) return timeDistToLoadTime > 6;
		else if(timeDistToMatchTime > 6) return timeDistToLoadTime > 4;
		else if(timeDistToMatchTime > 3) return timeDistToLoadTime > 3;
		else if(timeDistToMatchTime > 2) return timeDistToLoadTime > 2;
		else if(timeDistToMatchTime > 0.3) return timeDistToLoadTime > 1;
		else return false;
	}
}
