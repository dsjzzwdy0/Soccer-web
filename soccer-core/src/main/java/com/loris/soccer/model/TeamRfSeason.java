/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  League   
 * @Description: 用于表示联赛所属的球队  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_league_season_team")
public class TeamRfSeason extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;

	protected String lid;			//联赛编号
	protected String season;		//赛季编号
	protected String tid;			//球队编号
	public String getLid()
	{
		return lid;
	}
	public void setLid(String lid)
	{
		this.lid = lid;
	}
	public String getSeason()
	{
		return season;
	}
	public void setSeason(String season)
	{
		this.season = season;
	}
	public String getTid()
	{
		return tid;
	}
	public void setTid(String tid)
	{
		this.tid = tid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !(obj instanceof TeamRfSeason)) return false;
		TeamRfSeason other = (TeamRfSeason) obj;
		return StringUtils.equals(tid, other.tid)
				&& StringUtils.equals(lid, other.lid)
				&& StringUtils.equals(season, other.season);
	}	
	@Override
	public String toString()
	{
		return "LeagueRfTeam [lid=" + lid + ", season=" + season + ", tid=" + tid + "]";
	}
}
