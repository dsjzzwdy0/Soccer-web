/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractProducePlugin.java   
 * @Package com.loris.soccer.data.zgzcw.plugin   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.plugin.zgzcw.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicWebPageTaskProducePlugin;
import com.loris.common.filter.DateFilter;
import com.loris.common.filter.Filter;
import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.executor.HttpCommonWebPageExecutor;
import com.loris.soccer.model.base.BaseMatch;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwConstants;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwPageCreator;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwPageParser;
import com.loris.soccer.service.LeagueService;

/**
 * @ClassName: AbstractProducePlugin
 * @Description: 下载数据创建任务插件
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public abstract class AbstractProducePlugin extends BasicWebPageTaskProducePlugin
{
	private static Logger logger = Logger.getLogger(AbstractProducePlugin.class);

	@Autowired
	protected HttpCommonWebPageExecutor httpCommonExecutor;
	
	@Autowired
	protected LeagueService leagueService;

	/**
	 * 产生任务程序
	 * 
	 * @param context
	 *            插件运行环境
	 */
	@Override
	public abstract void produce(TaskPluginContext context) throws IOException, SQLException;

	/**
	 * 建立比赛的数据下载任务
	 * 
	 * @param match
	 *            比赛
	 */
	protected void createMatchDataTask(MatchItem matchItem, boolean hasOp, boolean hasYp, boolean hasNum)
	{
		Map<String, String> params = new HashMap<>();
		params.put(SoccerConstants.NAME_FIELD_MID, matchItem.getMid());

		// 欧赔数据下载
		if (hasOp)
		{
			createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params));
		}

		// 亚盘数据下载
		if (hasYp)
		{
			createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_YP, params));
		}

		// 大小球数据下载
		if (hasNum)
		{
			createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_NUM, params));
		}
	}

	/**
	 * 创建比赛数据下载任务
	 * 
	 * @param matchItems
	 */
	protected void createMatchTasks(List<? extends BaseMatch> matchItems, Filter<Date> filter)
	{
		for (BaseMatch matchItem : matchItems)
		{
			if (filter == null || filter.accept(matchItem.getMatchtime()))
			{
				createMatchDataTask(matchItem, true, true, true);
			}
		}
	}

	/**
	 * 从数据网页初始化下载数据任务
	 * @param context 插件运行环境
	 * @param page
	 * @param filter
	 * @return
	 * @throws IOException
	 * @throws WebParserException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	protected<T> boolean initializeFromWebPage(TaskPluginContext context, WebPage page, Filter<T> filter)
			throws IOException, WebParserException, HostForbiddenException, UrlFetchException
	{
		logger.info("Starting get the data from : " + page.getUrl());
		if (!httpCommonExecutor.execute(context, page))
		{
			logger.info("Error when HttpCommonExecutor execute: " + page.getUrl());
			return false;
		}
		
		//解析数据结果
		TableRecords records = ZgzcwPageParser.parseWebPage(page);
		if(records == null)
		{
			logger.info("Error when parse the page: " + page.getUrl());
			return false;
		}
		
		//通过不同的页面进行数据处理
		switch (page.getType())
		{
		case ZgzcwConstants.PAGE_LOTTERY_BD:
			MatchItemList matchItems = (MatchItemList) records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST);
			if(matchItems == null || matchItems.size() <= 0)
			{
				logger.info("There are no MatchItems in the page: " + page.getUrl());
				return false;
			}
			logger.info("There are " + matchItems.size() + " matches in the list.");
			this.createMatchTasks(matchItems, (DateFilter)filter);
			return true;
		case ZgzcwConstants.PAGE_CENTER:
			
		default:
			return false;
		}
	}
}
