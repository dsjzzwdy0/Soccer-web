/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchInfoList.java   
 * @Package com.loris.soccer.collection   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.filter.ObjectFilter;
import com.loris.common.util.DateUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.view.MatchInfo;

/**   
 * @ClassName:  MatchInfoList    
 * @Description: 比赛信息数据列表  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchInfoList extends DataList<MatchInfo>
{
	/** Serial value. */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new instance of MatchInfoList
	 */
	public MatchInfoList()
	{
	}
	
	/**
	 * 构造一个数据列表
	 * @param infos
	 */
	public MatchInfoList(List<MatchInfo> infos)
	{
		this.addAll(infos);
	}
	
	/**
	 * 获得比赛数据列表记录
	 * @param round 轮次
	 * @return 比赛数据列表
	 */
	public List<MatchInfo> getMatchInfos(Round round)
	{
		return getMatchInfos(round.getLid(), round.getSeason(), round.getRound());
	}
	
	/**
	 * 获得比赛数据列表记录
	 * @param lid 联赛
	 * @param season 赛季
	 * @param round 轮次
	 * @return 比赛数据列表
	 */
	public List<MatchInfo> getMatchInfos(String lid, String season, String round)
	{
		ObjectFilter<MatchInfo> filter = new ObjectFilter<MatchInfo>()
		{
			String l = lid;
			String s = season;
			String r = round;
			
			@Override
			public boolean accept(MatchInfo match)
			{
				return StringUtils.equals(l, match.getLid()) 
						&& StringUtils.equals(s, match.getSeason())
						&& StringUtils.equals(r, match.getRound());
			}
		};
		
		return getMatchInfo(filter);
	}
	
	/**
	 * 
	 * @param round
	 * @return
	 */
	public List<MatchInfo> getMatchInfoBeforeRound(Round round)
	{
		ObjectFilter<MatchInfo> filter = new ObjectFilter<MatchInfo>()
		{
			Round r = round;
			
			@Override
			public boolean accept(MatchInfo match)
			{
				if(r.compareTo(match.getLid(), match.getSeason(), match.getRound()) > 0)
					return true;
				else return false;
			}
		};
		return getMatchInfo(filter);
	}
	
	/**
	 * 获得比赛列表，在某一时间之前
	 * @param time
	 * @return
	 */
	public List<MatchInfo> getMatchInfoBeforeTime(Date time)
	{
		ObjectFilter<MatchInfo> filter = new ObjectFilter<MatchInfo>()
		{
			@Override
			public boolean accept(MatchInfo match)
			{
				Date matchTime = match.getMatchtime();
				return DateUtil.compareDate(matchTime, time) <= 0;
			}
		};
		return getMatchInfo(filter);
	}
	
	/**
	 * 获得比赛数据
	 * @param start
	 * @param end
	 * @return
	 */
	public List<MatchInfo> getMatchInfoBetween(Date start, Date end)
	{
		ObjectFilter<MatchInfo> filter = new ObjectFilter<MatchInfo>()
		{
			@Override
			public boolean accept(MatchInfo match)
			{
				long t = match.getMatchtime().getTime();
				long st = start.getTime();
				long en = end.getTime();
				return t >= st && t <= en;
			}
		};
		return getMatchInfo(filter);
	}
	
	/**
	 * 获得某一些场次的比赛
	 * @param time 时间
	 * @return 数据集
	 */
	public List<MatchInfo> getMatchInfoAfterTime(Date time)
	{
		ObjectFilter<MatchInfo> filter = new ObjectFilter<MatchInfo>()
		{
			@Override
			public boolean accept(MatchInfo match)
			{
				Date matchTime = match.getMatchtime();
				return DateUtil.compareDate(matchTime, time) >= 0;
			}
		};
		return getMatchInfo(filter);
	}
	
	/**
	 * 获得比赛数据列表
	 * @param filter
	 * @return
	 */
	public List<MatchInfo> getMatchInfo(ObjectFilter<MatchInfo> filter)
	{
		List<MatchInfo> results = new ArrayList<>();
		for(MatchInfo m: this)
		{
			if(filter.accept(m)) results.add(m);
		}
		return results;
	}
	
	/**
	 * 获得轮次数据的列表
	 * @return 轮次数据列表
	 */
	public RoundList getRoundList()
	{
		RoundList rounds = new RoundList();
		for(MatchInfo m: this)
		{
			String lid = m.getLid();
			String season = m.getSeason();
			String r = m.getRound();
			
			Round round = rounds.getRound(lid, season, r);
			if(round != null) continue;
			
			if(StringUtils.isNotEmpty(lid) 
					&& StringUtils.isNotEmpty(season) 
					&& StringUtils.isNotEmpty(r))
			{
				round = new Round(lid, season, r);
				rounds.add(round);
			}
		}
		return rounds;
	}
}
