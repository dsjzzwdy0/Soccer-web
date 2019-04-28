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
 * @ClassName:  MatchResult   
 * @Description: 比赛的比分数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_match_result")
public class MatchResult extends AutoIdEntity
{
	public static enum ResultType{
		WIN,      	//胜
		DRAW,		//平
		LOSE		//负
	}
	/***/
	private static final long serialVersionUID = 1L;

	protected String mid;
	protected ResultType result;		//比赛结果
	protected Integer homegoal;			//主队进球数
	protected Integer clientgoal;		//客队进球数
	
	public MatchResult()
	{
	}
	
	public MatchResult(String mid, String score)
	{
		this.mid = mid;
		setScore(score);
	}
	
	public MatchResult(String mid, int homegoal, int clientgoal)
	{
		this.mid = mid;
		setScore(homegoal, clientgoal);
	}
	
	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	public ResultType getResult()
	{
		return result;
	}
	public void setResult(ResultType result)
	{
		this.result = result;
	}
	public Integer getHomegoal()
	{
		return homegoal;
	}
	public void setHomegoal(Integer homegoal)
	{
		this.homegoal = homegoal;
	}
	public Integer getClientgoal()
	{
		return clientgoal;
	}
	public void setClientgoal(Integer clientgoal)
	{
		this.clientgoal = clientgoal;
	}
	
	public void setScore(Integer homegoal, Integer clientgoal)
	{
		this.homegoal = homegoal;
		this.clientgoal = clientgoal;
		if(homegoal > clientgoal)
		{
			result = ResultType.WIN;
		}
		else if(homegoal == clientgoal)
		{
			result = ResultType.DRAW;
		}
		else
		{
			result = ResultType.LOSE;
		}
	}
	
	public void setScore(String score)
	{
		String[] str = score.split(":");	
		if(str.length != 2)
		{
			return;
		}
		try
		{
			int homegoal = Integer.parseInt(str[0]);
			int clientgoal = Integer.parseInt(str[1]);
			setScore(homegoal, clientgoal);
		}
		catch(Exception e)
		{
			//Do nothing
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !StringUtils.equals(getClass().getName(), obj.getClass().getName())) return false;
		MatchResult other = (MatchResult)obj;
		return StringUtils.equals(mid, other.mid);
	}
	
	/**
	 * 检测是否是一个比赛结果数据
	 * @param score 比赛结果字符串
	 * @return 是否的标志
	 */
	public static boolean validateScore(String score)
	{
		String[] str = score.split(":");	
		if(str.length != 2)
		{
			return false;
		}
		try
		{
			Integer.parseInt(str[0]);
			Integer.parseInt(str[1]);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
