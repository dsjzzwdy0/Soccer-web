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
package com.loris.soccer.model.base;

import java.util.Date;

/**   
 * @ClassName:  League   
 * @Description: 博彩足球比赛   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class IssueMatch extends BaseMatch
{
	/***/
	private static final long serialVersionUID = 1L;

	protected String issue;					//比赛期号
	protected String ordinary;				//序号
	protected Date closetime;				//投注截止时间
	protected Float winodds;				//胜赔率
	protected Float drawodds;				//平赔率
	protected Float loseodds;				//负赔率
	protected Integer rqnum;				//让球数
	protected boolean rqopened;				//让球是否受注
	protected boolean isdelayed;				//是否延期
	protected Date delaytime;				//延期时间
	
	public String getIssue()
	{
		return issue;
	}
	public void setIssue(String issue)
	{
		this.issue = issue;
	}
	public String getOrdinary()
	{
		return ordinary;
	}
	public void setOrdinary(String ordinary)
	{
		this.ordinary = ordinary;
	}
	public Date getClosetime()
	{
		return closetime;
	}
	public void setClosetime(Date closetime)
	{
		this.closetime = closetime;
	}
	public Float getWinodds()
	{
		return winodds;
	}
	public void setWinodds(Float winodds)
	{
		this.winodds = winodds;
	}
	public Float getDrawodds()
	{
		return drawodds;
	}
	public void setDrawodds(Float drawodds)
	{
		this.drawodds = drawodds;
	}
	public Float getLoseodds()
	{
		return loseodds;
	}
	public void setLoseodds(Float loseodds)
	{
		this.loseodds = loseodds;
	}
	public Integer getRqnum()
	{
		return rqnum;
	}
	public void setRqnum(Integer rqnum)
	{
		this.rqnum = rqnum;
	}
	public boolean isRqopened()
	{
		return rqopened;
	}
	public void setRqopened(boolean rqopened)
	{
		this.rqopened = rqopened;
	}
	public boolean isIsdelayed()
	{
		return isdelayed;
	}
	public void setIsdelayed(boolean isdelayed)
	{
		this.isdelayed = isdelayed;
	}
	public Date getDelaytime()
	{
		return delaytime;
	}
	public void setDelaytime(Date delaytime)
	{
		this.delaytime = delaytime;
	}
	@Override
	public String toString()
	{
		return "IssueMatch [issue=" + issue + ", ordinary=" + ordinary + ", closetime=" + closetime + ", winodds="
				+ winodds + ", drawodds=" + drawodds + ", loseodds=" + loseodds + ", rqnum=" + rqnum + ", rqopened="
				+ rqopened + ", isdelayed=" + isdelayed + ", delaytime=" + delaytime + ", mid=" + mid + "]";
	}
}
