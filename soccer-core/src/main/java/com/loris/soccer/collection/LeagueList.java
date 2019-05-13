/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LeagueList.java   
 * @Package com.loris.soccer.collection   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.collection;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.model.League;

/**   
 * @ClassName: LeagueList   
 * @Description: 联赛数据列表   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LeagueList extends DataList<League>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeagueList()
	{
	}
	
	public LeagueList(List<League> leagues)
	{
		addAll(leagues);
	}

	/**
	 * 获得联赛数据
	 * @param name
	 * @return
	 */
	public League getLeague(String name)
	{
		if(StringUtils.isBlank(name))
			return null;
		for (League league : this)
		{
			if(StringUtils.equalsIgnoreCase(league.getLid(), name) ||
					StringUtils.equalsIgnoreCase(league.getName(), name))
			{
				return league;
			}
		}
		return null;
	}
	
	@Override
	public boolean addAll(Collection<? extends League> list)
	{
		if(list == null || list.isEmpty())
		{
			return false;
		}
		return super.addAll(list);
	}
}
