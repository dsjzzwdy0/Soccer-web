/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchFilter.java   
 * @Package com.loris.soccer.data.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.filter;

import java.util.Date;

import com.loris.common.filter.ObjectFilter;
import com.loris.common.util.ToolUtil;
import com.loris.soccer.model.base.BaseMatch;

/**   
 * @ClassName:  MatchFilter    
 * @Description: 比赛过滤器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchOddsFilter extends ObjectFilter<BaseMatch>
{
	/**  */
	protected int numOfDayHasOdds = 7;
	
	/**
	 * Create a new instance of MatchOddsFilter 
	 */
	public MatchOddsFilter(int numOfDayHasOdds)
	{
		this.numOfDayHasOdds = numOfDayHasOdds;
	}
	
	/**
	 * 检测是否通过数据下载
	 * @param match 比赛
	 * @param return 是否下载的标志
	 */
	@Override
	public boolean accept(BaseMatch match)
	{
		if(ToolUtil.isEmpty(match) || ToolUtil.isEmpty(match.getMatchtime()))
		{
			return false;
		}
		Date matchTime = match.getMatchtime();
		long timeToMatch = (matchTime.getTime() - System.currentTimeMillis()) / 1000;
		if(timeToMatch > numOfDayHasOdds * 24 * 3600) return false;
		return true;
	}
}
