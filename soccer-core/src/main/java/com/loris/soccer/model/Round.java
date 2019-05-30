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
 * @Description: 轮次比赛的过滤器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_league_round")
public class Round extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String lid;
	protected String season;
	protected String round;
	
	public Round()
	{
	}
	
	public Round(String lid, String season, String round)
	{
		this.lid = lid;
		this.season = season;
		this.round = round;
	}
	
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
	public String getRound()
	{
		return round;
	}
	public void setRound(String round)
	{
		this.round = round;
	}

	/**
	 * 是否同一个赛季的数据
	 * @param seasonInfo
	 * @return
	 */
	public boolean isSameSeason(Season seasonInfo)
	{
		if(StringUtils.equals(lid, seasonInfo.getLid()) && StringUtils.equals(season, seasonInfo.getSeason()))
			return true;
		else 
			return false;
	}
	
	/**
	 * 检测是否为两个相同的数据
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !(obj instanceof Round)) return false;
		Round other = (Round) obj;
		return equals(other.lid, other.season, other.round);
	}
	
	/**
	 * 检测是否相同的数据
	 * @param lid 联赛编号
	 * @param season 赛季
	 * @param round 轮次
	 * @return 是否相同
	 */
	public boolean equals(String lid, String season, String round)
	{
		return StringUtils.equals(lid, this.lid)
				&& StringUtils.equals(season, this.season)
				&& StringUtils.equals(round, this.round);
	}
	
	@Override
	public String toString()
	{
		return "Round [lid=" + lid + ", season=" + season + ", round=" + round + "]";
	}
}
