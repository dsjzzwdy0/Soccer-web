/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @SeasonFilter.java   
 * @Package com.loris.soccer.data.filtercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.filter;

import java.util.List;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.Season;
import com.loris.soccer.model.view.SeasonInfo;

/**   
 * @ClassName:  SeasonFilter.java   
 * @Description: 赛季数据过滤器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SeasonInfoFilter extends ObjectFilter<SeasonInfo>
{
	/** 轮次数据 */
	private List<? extends Round> rounds = null;
	
	/**
	 * Create a new instance of SeasonInfoFilter
	 */
	public SeasonInfoFilter(List<? extends Round> rounds)
	{
		this.rounds = rounds;
	}
	
	/**
	 * 检测是否可行
	 */
	@Override
	public boolean accept(SeasonInfo seasonInfo)
	{
		if(hasRoundInfo(seasonInfo, rounds))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 检测是否已经下载了赛季轮次比赛数据
	 * @param season 赛季数据
	 * @param rounds 轮次比赛
	 * @return 是否有轮次数据的标志
	 */
	protected boolean hasRoundInfo(Season season, List<? extends Round> rounds)
	{
		for (Round roundInfo : rounds)
		{
			if(roundInfo.isSameSeason(season)) return true;
		}
		return false;
	}
}
