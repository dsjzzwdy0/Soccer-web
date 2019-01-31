/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Match.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:30:27   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.MatchItem;

/**   
 * @ClassName:  Match   
 * @Description: 比赛数据类   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:30:27   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_match")
public class Match extends MatchItem
{
	/***/
	private static final long serialVersionUID = 1L;
	
	protected String mid;				//比赛编号：唯一编号
	protected String homeid;			//主场球队编号
	protected String clientid;			//客场球队编号
	protected String lid;				//联赛编号
	protected String round;				//比赛轮次
	protected String season;			//比赛赛季
	protected Date matchtime;			//比赛时间
	
	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
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
	public Date getMatchtime()
	{
		return matchtime;
	}
	public void setMatchtime(Date matchtime)
	{
		this.matchtime = matchtime;
	}
}
