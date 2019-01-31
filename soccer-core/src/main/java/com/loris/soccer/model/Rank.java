/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Rank.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午9:48:26   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import java.sql.Date;

import com.loris.soccer.bean.AutoIdEntity;

/**   
 * @ClassName:  Rank   
 * @Description: 球队排名类，仅限于联赛比赛类型、国际足联等信息
 * @author: 东方足彩
 * @date:   2019年1月28日 下午9:48:26   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class Rank extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;
	
	protected String lid;				//联赛编号
	protected String season;			//赛季
	protected String round;				//轮次
	protected String teamid;			//球队编号
	protected Integer rank;				//排名
	protected Integer gamenum;			//比赛场次
	protected Integer score;			//总积分
	protected Integer wingoal;			//进球数
	protected Integer losegoal;			//失球数
	protected Date ranktime;			//排名时间
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
	public String getTeamid()
	{
		return teamid;
	}
	public void setTeamid(String teamid)
	{
		this.teamid = teamid;
	}
	public Integer getRank()
	{
		return rank;
	}
	public void setRank(Integer rank)
	{
		this.rank = rank;
	}
	public Integer getGamenum()
	{
		return gamenum;
	}
	public void setGamenum(Integer gamenum)
	{
		this.gamenum = gamenum;
	}
	public Integer getScore()
	{
		return score;
	}
	public void setScore(Integer score)
	{
		this.score = score;
	}
	public Integer getWingoal()
	{
		return wingoal;
	}
	public void setWingoal(Integer wingoal)
	{
		this.wingoal = wingoal;
	}
	public Integer getLosegoal()
	{
		return losegoal;
	}
	public void setLosegoal(Integer losegoal)
	{
		this.losegoal = losegoal;
	}
	public Date getRanktime()
	{
		return ranktime;
	}
	public void setRanktime(Date ranktime)
	{
		this.ranktime = ranktime;
	}
}
