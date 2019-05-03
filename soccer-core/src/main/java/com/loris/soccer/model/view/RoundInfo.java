/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @RoundInfo.java   
 * @Package com.loris.soccer.model.viewcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.view;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.Round;

/**   
 * @ClassName:  RoundInfo.java   
 * @Description: 轮次数据信息   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_league_round_info")
public class RoundInfo extends Round
{
	/** */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String leaguetype;
	protected Date startTime = null;
	protected Date endTime = null;
	protected int matchnum;
	
	public Date getStartTime()
	{
		return startTime;
	}
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	public Date getEndTime()
	{
		return endTime;
	}
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the leaguetype
	 */
	public String getLeaguetype()
	{
		return leaguetype;
	}
	/**
	 * @param leaguetype the leaguetype to set
	 */
	public void setLeaguetype(String leaguetype)
	{
		this.leaguetype = leaguetype;
	}
	/**
	 * @return the matchnum
	 */
	public int getMatchnum()
	{
		return matchnum;
	}
	/**
	 * @param matchnum the matchnum to set
	 */
	public void setMatchnum(int matchnum)
	{
		this.matchnum = matchnum;
	}
	@Override
	public String toString()
	{
		return "RoundInfo [startTime=" + startTime + ", endTime=" + endTime + ", lid=" + lid + ", season=" + season
				+ ", round=" + round + "]";
	}
	
}
