/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  BetJcOdds.java   
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

/**   
 * @ClassName:  BetJcOdds    
 * @Description: 竞彩赔率数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_bet_jc_odds")
public class BetJcOdds extends BetBdOdds
{
	/***/
	private static final long serialVersionUID = 1L;
	protected boolean isopen;			//是否开放
	protected boolean isdanguan;		//是否单关
	protected float rqwinodds;
	protected float rqdrawodds;
	protected float rqloseodds;
	protected boolean isrqopen;			//让球是否开放
	
	public boolean isIsopen()
	{
		return isopen;
	}
	public void setIsopen(boolean isopen)
	{
		this.isopen = isopen;
	}
	public boolean isIsdanguan()
	{
		return isdanguan;
	}
	public void setIsdanguan(boolean isdanguan)
	{
		this.isdanguan = isdanguan;
	}
	public float getRqwinodds()
	{
		return rqwinodds;
	}
	public void setRqwinodds(float rqwinodds)
	{
		this.rqwinodds = rqwinodds;
	}
	public float getRqdrawodds()
	{
		return rqdrawodds;
	}
	public void setRqdrawodds(float rqdrawodds)
	{
		this.rqdrawodds = rqdrawodds;
	}
	public float getRqloseodds()
	{
		return rqloseodds;
	}
	public void setRqloseodds(float rqloseodds)
	{
		this.rqloseodds = rqloseodds;
	}
	public boolean isIsrqopen()
	{
		return isrqopen;
	}
	public void setIsrqopen(boolean isrqopen)
	{
		this.isrqopen = isrqopen;
	}
	@Override
	public String toString()
	{
		return "BetJcOdds [isopen=" + isopen + ", isdanguan=" + isdanguan + ", rqwinodds=" + rqwinodds + ", rqdrawodds="
				+ rqdrawodds + ", rqloseodds=" + rqloseodds + ", isrqopen=" + isrqopen + ", mid=" + mid + ", type="
				+ type + ", opentime=" + opentime + ", winodds=" + winodds + ", drawodds=" + drawodds + ", loseodds="
				+ loseodds + ", rqnum=" + rqnum + ", id=" + id + "]";
	}
}
