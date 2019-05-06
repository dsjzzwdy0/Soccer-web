/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @RoundInfoFilter.java   
 * @Package com.loris.soccer.data.filtercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.filter.object;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.view.RoundInfo;

/**   
 * @ClassName:  RoundInfoFilter.java   
 * @Description: 比赛轮次数据的过滤器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class RoundInfoFilter extends ObjectFilter<RoundInfo>
{
	
	/**
	 * 检测数据页面
	 */
	@Override
	public boolean accept(RoundInfo roundInfo)
	{
		if(StringUtils.equals(SoccerConstants.LEAGUE_TYPE_LEAGUE, roundInfo.getLeaguetype()))
		{
			return true;
		}
		else 
		{
			return false;			
		}
	}
}
