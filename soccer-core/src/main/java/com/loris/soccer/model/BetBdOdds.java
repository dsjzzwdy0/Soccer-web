/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  BetOdds.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  BetOdds    
 * @Description: 北单投注参数   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_bet_bd_odds")
public class BetBdOdds extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;
	protected String mid;				//比赛编号
	protected String type; 				//北单、竞彩
	protected Date opentime;			//开单时间
	protected float winodds;
	protected float drawodds;
	protected float loseodds;
	protected int rqnum;				//让球数
	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Date getOpentime()
	{
		return opentime;
	}
	public void setOpentime(Date opentime)
	{
		this.opentime = opentime;
	}
	public float getWinodds()
	{
		return winodds;
	}
	public void setWinodds(float winodds)
	{
		this.winodds = winodds;
	}
	public float getDrawodds()
	{
		return drawodds;
	}
	public void setDrawodds(float drawodds)
	{
		this.drawodds = drawodds;
	}
	public float getLoseodds()
	{
		return loseodds;
	}
	public void setLoseodds(float loseodds)
	{
		this.loseodds = loseodds;
	}
	public int getRqnum()
	{
		return rqnum;
	}
	public void setRqnum(int rqnum)
	{
		this.rqnum = rqnum;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj.getClass() != this.getClass()) return false;
		BetBdOdds other = (BetBdOdds) obj;
		if(!StringUtils.equals(mid, other.mid))
			return false;
		if(opentime == other.getOpentime()) return true;
		if(rqnum == other.rqnum && winodds == other.winodds && drawodds == other.drawodds &&
				loseodds == other.loseodds)
			return true;
		return false;
	}
}
