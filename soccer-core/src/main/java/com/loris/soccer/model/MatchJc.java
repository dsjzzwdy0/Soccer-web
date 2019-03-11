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

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.IssueMatch;

/**   
 * @ClassName:  League   
 * @Description: 竞彩比赛类数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_match_jc")
public class MatchJc extends IssueMatch
{
	/***/
	private static final long serialVersionUID = 1L;
	
	protected boolean opened;				//正常比分是否受注
	protected Float rqwinodds;				//让球胜赔率
	protected Float rqdrawodds;				//让球平赔率
	protected Float rqloseodds;				//让球负赔率

	public MatchJc()
	{
	}
	
	public MatchJc(String mid)
	{
		this.mid = mid;
	}
	public boolean isOpened()
	{
		return opened;
	}
	public void setOpened(boolean opened)
	{
		this.opened = opened;
	}
	public Float getRqwinodds()
	{
		return rqwinodds;
	}
	public void setRqwinodds(Float rqwinodds)
	{
		this.rqwinodds = rqwinodds;
	}
	public Float getRqdrawodds()
	{
		return rqdrawodds;
	}
	public void setRqdrawodds(Float rqdrawodds)
	{
		this.rqdrawodds = rqdrawodds;
	}
	public Float getRqloseodds()
	{
		return rqloseodds;
	}
	public void setRqloseodds(Float rqloseodds)
	{
		this.rqloseodds = rqloseodds;
	}

	@Override
	public String toString()
	{
		return "MatchJc [opened=" + opened + ", rqwinodds=" + rqwinodds + ", rqdrawodds=" + rqdrawodds + ", rqloseodds="
				+ rqloseodds + ", issue=" + issue + ", ordinary=" + ordinary + ", closetime=" + closetime + ", winodds="
				+ winodds + ", drawodds=" + drawodds + ", loseodds=" + loseodds + ", rqnum=" + rqnum + ", rqopened="
				+ rqopened + ", delayed=" + isdelayed + ", delaytime=" + delaytime + ", mid=" + mid + ", id=" + id + "]";
	}
}
