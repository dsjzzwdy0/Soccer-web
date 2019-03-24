/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchJcInfo.java   
 * @Package com.loris.soccer.model.view   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.model.MatchResult.ResultType;

/**   
 * @ClassName: MatchJcInfo   
 * @Description: 竞彩比赛的数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_match_jc_info")
public class MatchJcInfo extends MatchJc
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String lid;				//联赛编号
	protected String round;				//比赛轮次
	protected String season;			//比赛赛季
	protected String homeid;			//主场球队编号
	protected String clientid;			//客场球队编号
	protected String leaguename;		//联赛名称
	protected String homename;			//主队名称
	protected String clientname;		//客队名称
	protected ResultType result;		//比赛结果
	protected Integer homegoal;			//主队进球数
	protected Integer clientgoal;		//客队进球数
	public String getLid()
	{
		return lid;
	}
	public void setLid(String lid)
	{
		this.lid = lid;
	}
	public String getRound()
	{
		return round;
	}
	public void setRound(String round)
	{
		this.round = round;
	}
	public String getSeason()
	{
		return season;
	}
	public void setSeason(String season)
	{
		this.season = season;
	}
	public String getHomeid()
	{
		return homeid;
	}
	public void setHomeid(String homeid)
	{
		this.homeid = homeid;
	}
	public String getClientid()
	{
		return clientid;
	}
	public void setClientid(String clientid)
	{
		this.clientid = clientid;
	}
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
}
