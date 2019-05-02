/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ZgzcwMatchResultDataPlugin.java   
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
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.loris.client.task.context.TaskPluginContext;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Round;

/**   
 * @ClassName: ZgzcwMatchResultDataPlugin   
 * @Description: 中国足彩网比赛结果数据的下载管理器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class ZgzcwMatchResultDataPlugin extends ZgzcwBasePlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwMatchResultDataPlugin.class);
	
	/** 每次最大的数据下载量 */
	protected int maxSize = 300;
	
	/** 开始的赛季数据 */
	protected String startSeason = "2018";
	
	/**
	 * Create a new instance of ZgzcwMatchResultDataPlugin
	 */
	public ZgzcwMatchResultDataPlugin()
	{
		this("比赛结果数据下载");
	}

	/**
	 * @param name
	 */
	protected ZgzcwMatchResultDataPlugin(String name)
	{
		super(name);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.ZgzcwBasePlugin#produce(com.loris.client.task.context.TaskPluginContext)
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
		List<Round> rounds = leagueService.getRounds(startSeason, null);
		if(rounds == null || rounds.size() == 0)
		{
			logger.warn("There are no rounds in the database, the ZgzcwMatchResultDataPlugin exit.");
			return;
		}
		
		int i = 1;
		for (Round round : rounds)
		{
			logger.info(i +++ ": " + round);
		}
		
		List<League> leagues = leagueService.list();
		for (League league : leagues)
		{
			logger.info(i +++ ": " + league);
		}
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.data.ZgzcwBasePlugin#registerProcessPageTypes(java.util.List)
	 */
	@Override
	protected void registerProcessPageTypes(List<String> types)
	{
	}
}
