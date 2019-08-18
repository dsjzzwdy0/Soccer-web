/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @Result.java   
 * @Package com.loris.soccer.model.basecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.model.base;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.bean.AutoIdEntity;
import com.loris.soccer.model.MatchResult.ResultType;

/**   
 * @ClassName:  Result.java   
 * @Description: 比赛结果数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class Result extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ResultType result;		//比赛结果
	protected Integer homegoal;			//主队进球数
	protected Integer clientgoal;		//客队进球数
	
	public Result()
	{
	}
	
	public Result(String score)
	{
		this.setScore(score);
	}
	
	/**
	 * @return the result
	 */
	public ResultType getResultType()
	{
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResultType(ResultType result)
	{
		this.result = result;
	}
	/**
	 * @return the homegoal
	 */
	public Integer getHomegoal()
	{
		return homegoal;
	}
	/**
	 * @param homegoal the homegoal to set
	 */
	public void setHomegoal(Integer homegoal)
	{
		this.homegoal = homegoal;
	}
	/**
	 * @return the clientgoal
	 */
	public Integer getClientgoal()
	{
		return clientgoal;
	}
	/**
	 * @param clientgoal the clientgoal to set
	 */
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
		Result other = (Result)obj;
		return result == other.result && homegoal == other.homegoal && clientgoal == other.clientgoal;
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
