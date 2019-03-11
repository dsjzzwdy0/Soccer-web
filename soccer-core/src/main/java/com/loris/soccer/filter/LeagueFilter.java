/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LeagueFilter.java   
 * @Package com.loris.soccer.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.filter;

import com.loris.common.filter.AbstractFilter;
import com.loris.common.filter.Filter;
import com.loris.soccer.model.League;

/**   
 * @ClassName: LeagueFilter   
 * @Description: 联赛过滤器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LeagueFilter extends AbstractFilter<League> implements Filter<League>
{
	/**
	 * Create a new instance of LeagueFilter
	 */
	public LeagueFilter()
	{
	}
	
	public LeagueFilter(League league)
	{
		this.value = league;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.Filter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(League obj)
	{
		if(this.value == null)
		{
			return false;
		}
		return value.equals(obj);
	}
}
