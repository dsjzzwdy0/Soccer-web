/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchStatFilter.java   
 * @Package com.loris.soccer.stat.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.filter;

import java.util.ArrayList;
import java.util.List;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.model.Match;
/**
 * @ClassName: MatchStatFilter
 * @Description: (@Todo)
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class StatMatchFilter extends ObjectFilter<Match>
{
	List<String> acceptLids = new ArrayList<>();
	
	List<String> refuseLids = new ArrayList<>();
	
	
	
	/**
	 * 比赛过滤器，默认为包含
	 * @param match
	 */
	@Override
	public boolean accept(Match match)
	{
		String lid = match.getLid();
		if(acceptLids.contains(lid)) return true;
		if(refuseLids.contains(lid)) return false;
		return true;
	}
}
