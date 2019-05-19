/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @TeamGrade.java   
 * @Package com.loris.soccer.model.complexcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.complex;

import com.loris.soccer.model.MatchResult.ResultType;
import com.loris.soccer.model.view.MatchInfo;

/**   
 * @ClassName:  TeamGrade.java   
 * @Description: 球队战绩数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TeamGrade
{
	protected String tid;
	protected String name;
	protected int total;
	protected int winnum;
	protected int drawnum;
	protected int losenum;
	
	public TeamGrade()
	{
	}
	
	public TeamGrade(String tid)
	{
		this.tid = tid;
	}
	
	/**
	 * @return the tid
	 */
	public String getTid()
	{
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(String tid)
	{
		this.tid = tid;
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
	 * @return the total
	 */
	public int getTotal()
	{
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total)
	{
		this.total = total;
	}
	/**
	 * @return the winnum
	 */
	public int getWinnum()
	{
		return winnum;
	}
	/**
	 * @param winnum the winnum to set
	 */
	public void setWinnum(int winnum)
	{
		this.winnum = winnum;
	}
	/**
	 * @return the drawnum
	 */
	public int getDrawnum()
	{
		return drawnum;
	}
	/**
	 * @param drawnum the drawnum to set
	 */
	public void setDrawnum(int drawnum)
	{
		this.drawnum = drawnum;
	}
	/**
	 * @return the losenum
	 */
	public int getLosenum()
	{
		return losenum;
	}
	/**
	 * @param losenum the losenum to set
	 */
	public void setLosenum(int losenum)
	{
		this.losenum = losenum;
	}
	
	/**
	 * 加入比赛信息
	 * @param matchInfo
	 */
	public void addMatchInfo(MatchInfo matchInfo)
	{
		ResultType result = matchInfo.getResult();
		if(result == null) return;
		total ++;
		if(result == ResultType.WIN)
		{
			winnum ++;
		}
		else if(result == ResultType.DRAW)
		{
			drawnum ++;
		}
		else
		{
			losenum ++;			
		}
	}
}
