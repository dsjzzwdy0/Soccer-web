/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchStat.java   
 * @Package com.loris.soccer.statcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.loris.soccer.model.complex.OddsOpRecord;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.service.MatchService;
import com.loris.soccer.service.OddsService;

/**   
 * @ClassName:  MatchStat.java   
 * @Description: 比赛数据的统计分析  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchStat
{
	private static Logger logger = Logger.getLogger(MatchStat.class);
	
	/** 赔率服务 */
	OddsService oddsService;
	
	/** 比赛服务 */
	MatchService matchService;
	
	/**
	 * Create a new instance of MatchStat
	 * @param oddsService
	 * @param matchService
	 */
	public MatchStat(OddsService oddsService, MatchService matchService)
	{
		this.matchService = matchService;
		this.oddsService = oddsService;
	}
	
	/**
	 * 计算联赛的统计分析数据
	 * @param lid 联赛编号
	 * @param start 开始日期
	 * @param end 结束日期
	 */
	public void computeStat(String lid, Date start, Date end)
	{
		
	}
	
	/**
	 * 计算比赛的统计数据
	 * @param mids 比赛编号列表
	 */
	public void computeStat(List<String> mids)
	{
		
	}
	
	/**
	 * 计算冷门比赛的特征
	 * @param probThreshold 概率的阈值
	 * @param start 起始时间
	 * @param end 截止时间
	 */
	public void computeSurpriseMatch(Date start, Date end, float probThreshold)
	{
		List<MatchInfo> matchInfos = matchService.getMatchInfos(start, end, true);
		for (MatchInfo matchInfo : matchInfos)
		{
			logger.info(matchInfo);
			List<OddsOpRecord> records = oddsService.selectOddsOpRecords(matchInfo.getMid());
			if(records == null)
			{
				logger.warn("There are no OddsOpRecord in database: " + matchInfo.getMid());
				continue;
			}
			
			for (OddsOpRecord oddsOpRecord : records)
			{
				logger.info(oddsOpRecord);
			}
		}
	}
	
	/**
	 * 统计博彩公司的频率数据
	 * @param start 开始日期
	 * @param end 结束日期
	 */
	public void computeCorpFreq(Date start, Date end)
	{
		List<MatchInfo> matchInfos = matchService.getMatchInfos(start, end, true);
		
		Map<String, Integer> totalRecords = new HashMap<>();
		for (MatchInfo matchInfo : matchInfos)
		{
			logger.info(matchInfo);
			String lid = matchInfo.getLid();
			
			Integer num = totalRecords.get(lid);
			if(num == null)
			{
				totalRecords.put(lid, 1);
			}
			else
			{
				totalRecords.put(lid, num +1);
			}
		}
		
		int i = 1;
		for (String lid : totalRecords.keySet())
		{
			logger.info(i +++ ": " + totalRecords.get(lid));
		}
	}
}
