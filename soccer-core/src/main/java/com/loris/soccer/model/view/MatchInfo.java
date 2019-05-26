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
package com.loris.soccer.model.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.MatchResult.ResultType;

/**   
 * @ClassName:  League   
 * @Description: 比赛信息数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_match_info")
public class MatchInfo extends Match
{
	/***/
	private static final long serialVersionUID = 1L;
	
	protected String leaguename;		//联赛名称
	protected String homename;			//主队名称
	protected String homerank;
	protected String clientname;		//客队名称
	protected String clientrank;
	protected ResultType result;		//比赛结果
	protected Integer homegoal;			//主队进球数
	protected Integer clientgoal;		//客队进球数
	
	public String getLeaguename()
	{
		return leaguename;
	}
	public void setLeaguename(String leaguename)
	{
		this.leaguename = leaguename;
	}
	public String getHomename()
	{
		return homename;
	}
	public void setHomename(String homename)
	{
		this.homename = homename;
	}
	public String getClientname()
	{
		return clientname;
	}
	public void setClientname(String clientname)
	{
		this.clientname = clientname;
	}
	/**
	 * @return the result
	 */
	public ResultType getResult()
	{
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(ResultType result)
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
	 * @return the homerank
	 */
	public String getHomerank()
	{
		return homerank;
	}
	/**
	 * @param homerank the homerank to set
	 */
	public void setHomerank(String homerank)
	{
		this.homerank = homerank;
	}
	/**
	 * @return the clientrank
	 */
	public String getClientrank()
	{
		return clientrank;
	}
	/**
	 * @param clientrank the clientrank to set
	 */
	public void setClientrank(String clientrank)
	{
		this.clientrank = clientrank;
	}
	/**
	 * @param clientgoal the clientgoal to set
	 */
	public void setClientgoal(Integer clientgoal)
	{
		this.clientgoal = clientgoal;
	}
	public MatchResult getMatchResult()
	{
		MatchResult r = new MatchResult();
		r.setMid(mid);
		r.setScore(homegoal, clientgoal);
		return r;
	}
}
