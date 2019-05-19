/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ZgzcwOddsDataPlugin.java   
 * @Package com.loris.soccer.data   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.loris.client.task.context.TaskPluginContext;
import com.loris.common.filter.Filter;
import com.loris.common.util.DateUtil;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.filter.WebPageFilter;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.model.base.MatchItem;

/**   
 * @ClassName: ZgzcwOddsDataPlugin   
 * @Description: 中国足彩网赔率数据更新与下载 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class ZgzcwOddsDataPlugin extends ZgzcwBasePlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwOddsDataPlugin.class);
	
	/**
	 * Create a new instance of ZgzcwOddsDataPlugin
	 */
	public ZgzcwOddsDataPlugin()
	{
		this(WebPageProperties.getDefault());
	}
	
	/**
	 * Create a new instance of ZgzcwOddsDataPlugin
	 * @param webPageConf
	 */
	public ZgzcwOddsDataPlugin(WebPageProperties webPageConf)
	{
		super("赔率数据更新", webPageConf);
	}
	
	/**
	 * Create a new instance of ZgzcwOddsDataPlugin
	 * @param webPageConf
	 */
	public ZgzcwOddsDataPlugin(WebPageProperties webPageConf, WebPageFilter filter)
	{
		this(webPageConf);
		this.webPageFilter = filter;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.ZgzcwBasePlugin#produce(com.loris.client.task.context.TaskPluginContext)
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
		Date start = new Date();
		Date end = getEndDate(start);	
		MatchItemList matchs = new MatchItemList();
		getMatchItems(matchs, start, end);
		
		if(matchs.size() == 0)
		{
			logger.warn("There are no match item between [" + start + "~" + end + "] in the database, no task produced.");
			return;
		}
		
		Filter<MatchItem> filter = getSourceFilter(SoccerConstants.SOCCER_DATA_MATCH);
		createMatchTasks(matchs, filter);
	}
	
	/**
	 * 从数据库系统中查找比赛数据,该函数将从三个渠道查找数据：
	 * 北单比赛、竞彩比赛、比赛基础数据库
	 * @param matchs 比赛窗口
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	protected void getMatchItems(MatchItemList matchs, Date start, Date end)
	{
		List<Match> baseMatchs = matchService.getMatchs(start, end);
		if(baseMatchs != null && baseMatchs.size() > 0)
			addMatchItems(matchs, baseMatchs);
		
		List<MatchBd> matchBds = matchService.getMatchBds(start, end);
		if(matchBds != null && matchBds.size() > 0)
			addMatchItems(matchs, matchBds);
		
		List<MatchJc> matchJcs = matchService.getMatchJcs(start, end);
		if(matchBds != null && matchJcs.size() > 0)
			addMatchItems(matchs, matchJcs);
	}
	
	/**
	 * 加入比赛数据到列表中
	 * @param matchs 列表容器
	 * @param list 比赛列表
	 */
	protected void addMatchItems(MatchItemList matchs, List<? extends MatchItem> list)
	{
		for (MatchItem matchItem : list)
		{
			if(!matchs.containsMid(matchItem.getMid()))
			{
				matchs.add(matchItem);
			}
		}
	}

	/**
	 * 计算要查找的比赛最后的时间
	 * @param date 日期
	 * @return 日期
	 */
	protected Date getEndDate(Date date)
	{
		int numDayofHasOdds = webPageConf == null ? 0 : webPageConf.getNumDayOfHasOdds();
		numDayofHasOdds = numDayofHasOdds > 0 ? numDayofHasOdds : 3;
		Date end = DateUtil.addDayNum(date, numDayofHasOdds);
		return end;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.ZgzcwBasePlugin#registerProcessPageTypes(java.util.List)
	 */
	@Override
	protected void registerProcessPageTypes(WebPageFilter webPageFilter)
	{
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_ODDS_OP);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_ODDS_YP);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_ODDS_NUM);
	}
}
