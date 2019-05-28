/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  TeamCapabilityList.java   
 * @Package com.loris.soccer.stat.collection   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.collection;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.stat.model.TeamCapability;

/**   
 * @ClassName:  TeamCapabilityList    
 * @Description: 球队比赛数据列表   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TeamCapabilityList extends DataList<TeamCapability>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获得球队数据
	 * @param lid
	 * @param tid
	 * @return
	 */
	public TeamCapability geTeamCapability(String lid, String tid)
	{
		for(TeamCapability team : this)
		{
			if(StringUtils.equals(team.getLid(), lid) &&
					StringUtils.equals(team.getTid(), tid))
			{
				return team;
			}
		}
		return null;
	}
}
