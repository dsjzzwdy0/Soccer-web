/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  TeamCapability.java   
 * @Package com.loris.soccer.stat.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.model;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  TeamCapability    
 * @Description: 球队实力  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TeamCapability extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String lid;					//联赛编号
	private String tid;					//球队编号
	private String name;				//球队名称
	private float capability;			//球队实力
	private int matchnum;
	private float wingoal;
	private float losegoal;
	
	public TeamCapability()
	{
		matchnum = 0;
	}
	
	public TeamCapability(String lid, String tid, String name)
	{
		this();
		this.lid = lid;
		this.tid = tid;
		this.name = name;
	}
	
	public String getLid()
	{
		return lid;
	}
	public void setLid(String lid)
	{
		this.lid = lid;
	}
	public String getTid()
	{
		return tid;
	}
	public void setTid(String tid)
	{
		this.tid = tid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public float getCapability()
	{
		return capability;
	}
	public void setCapability(float capability)
	{
		this.capability = capability;
	}
	public void addCapability(float value)
	{
		this.capability += value;
		this.matchnum ++;
	}

	/**
	 * @return the wingoal
	 */
	public float getWingoal()
	{
		return wingoal;
	}

	/**
	 * @param wingoal the wingoal to set
	 */
	public void setWingoal(float wingoal)
	{
		this.wingoal = wingoal;
	}
	
	public void addWingoal(float wingoal)
	{
		this.wingoal += wingoal;
	}

	/**
	 * @return the losegoal
	 */
	public float getLosegoal()
	{
		return losegoal;
	}

	/**
	 * @param losegoal the losegoal to set
	 */
	public void setLosegoal(float losegoal)
	{
		this.losegoal = losegoal;
	}
	
	public void addLosegoal(float losegoal)
	{
		this.losegoal += losegoal;
	}
	
	public void addGoal(float wingoal, float losegoal)
	{
		this.wingoal += wingoal;
		this.losegoal += losegoal;
	}

	public int getMatchnum()
	{
		return matchnum;
	}

	public void setMatchnum(int matchnum)
	{
		this.matchnum = matchnum;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "TeamCapability [lid=" + lid + ", tid=" + tid + ", name=" + name + ", capability=" + capability
				+ ", matchnum=" + matchnum + ", wingoal=" + wingoal + ", losegoal=" + losegoal + "]";
	}
}
