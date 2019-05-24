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

import org.apache.commons.lang3.StringUtils;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  League   
 * @Description: 赔率基本数据   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsValue extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;

	protected String mid;				//比赛编号
	protected String corpid;			//博彩公司
	protected Date opentime;			//开盘时间
	protected float winodds;
	protected float loseodds;
	protected float winkelly;
	protected float losekelly;
	protected float winprob;
	protected float loseprob;
	protected float lossratio;
	protected String source;			//数据来源
	
	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	public String getCorpid()
	{
		return corpid;
	}
	public void setCorpid(String corpid)
	{
		this.corpid = corpid;
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
	public float getLoseodds()
	{
		return loseodds;
	}
	public void setLoseodds(float loseodds)
	{
		this.loseodds = loseodds;
	}
	public float getWinkelly()
	{
		return winkelly;
	}
	public void setWinkelly(float winkelly)
	{
		this.winkelly = winkelly;
	}
	public float getLosekelly()
	{
		return losekelly;
	}
	public void setLosekelly(float losekelly)
	{
		this.losekelly = losekelly;
	}
	public float getWinprob()
	{
		return winprob;
	}
	public void setWinprob(float winprob)
	{
		this.winprob = winprob;
	}
	public float getLoseprob()
	{
		return loseprob;
	}
	public void setLoseprob(float loseprob)
	{
		this.loseprob = loseprob;
	}
	public float getLossratio()
	{
		return lossratio;
	}
	public void setLossratio(float lossratio)
	{
		this.lossratio = lossratio;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj.getClass() != this.getClass()) return false;
		OddsValue value = (OddsValue) obj;
		return StringUtils.equals(mid, value.mid) 
				&& StringUtils.equals(corpid, value.corpid)
				&& opentime == value.getOpentime();
	}
}
