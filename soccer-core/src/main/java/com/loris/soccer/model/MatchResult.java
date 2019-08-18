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
import com.loris.soccer.model.base.Result;

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
public class MatchResult extends Result
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
		
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !StringUtils.equals(getClass().getName(), obj.getClass().getName())) return false;
		MatchResult other = (MatchResult)obj;
		return StringUtils.equals(mid, other.mid);
	}
	
	@Override
	public String toString()
	{
		return "MatchResult [mid=" + mid + ", result=" + result + ", homegoal=" + homegoal + ", clientgoal="
				+ clientgoal + "]";
	}
}
