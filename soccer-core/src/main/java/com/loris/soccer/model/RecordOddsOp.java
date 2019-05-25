/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOpRecord.java   
 * @Package com.loris.soccer.wrapper.model   
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

/**
 * @ClassName: OddsOpRecord
 * @Description: 欧赔数据记录
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@TableName("soccer_record_odds_op")
public class RecordOddsOp extends OddsOp
{
	/***/
	private static final long serialVersionUID = 1L;
	
	private Date firsttime;
	private float firstwinodds;
	private float firstdrawodds;
	private float firstloseodds;
	
	public RecordOddsOp()
	{
	}
	
	public RecordOddsOp(OddsOp oddsOp)
	{
		this.addOddsOp(oddsOp);
	}
	
	public Date getFirsttime()
	{
		return firsttime;
	}
	public void setFirsttime(Date firsttime)
	{
		this.firsttime = firsttime;
	}
	public float getFirstwinodds()
	{
		return firstwinodds;
	}
	public void setFirstwinodds(float firstwinodds)
	{
		this.firstwinodds = firstwinodds;
	}
	public float getFirstdrawodds()
	{
		return firstdrawodds;
	}
	public void setFirstdrawodds(float firstdrawodds)
	{
		this.firstdrawodds = firstdrawodds;
	}
	public float getFirstloseodds()
	{
		return firstloseodds;
	}
	public void setFirstloseodds(float firstloseodds)
	{
		this.firstloseodds = firstloseodds;
	}
	
	public void addOddsOp(OddsOp oddsOp)
	{
		if(StringUtils.isBlank(corpid))
		{
			this.setFirstOddsOp(oddsOp);
			this.setLastOddsOp(oddsOp);
			return;
		}
		if(firsttime == null || this.firsttime.getTime() > oddsOp.getOpentime().getTime())
		{
			setFirstOddsOp(oddsOp);
		}
		if(opentime == null || this.opentime.getTime() < oddsOp.getOpentime().getTime())
		{
			setLastOddsOp(oddsOp);
		}
	}
	
	public void setFirstOddsOp(OddsOp oddsOp)
	{
		setBaseInfo(oddsOp);
		this.firstwinodds = oddsOp.getWinodds();
		this.firstdrawodds = oddsOp.getDrawodds();
		this.firstloseodds = oddsOp.getLoseodds();
		this.firsttime = oddsOp.getOpentime();
	}
	
	public void setLastOddsOp(OddsOp oddsOp)
	{
		setBaseInfo(oddsOp);
		this.opentime = oddsOp.getOpentime();
		this.winodds = oddsOp.getWinodds();
		this.drawodds = oddsOp.getDrawodds();
		this.loseodds = oddsOp.getLoseodds();
		this.winkelly = oddsOp.getWinkelly();
		this.drawkelly = oddsOp.getDrawkelly();
		this.losekelly = oddsOp.getLosekelly();
		this.winprob = oddsOp.getWinprob();
		this.drawprob = oddsOp.getDrawprob();
		this.loseprob = oddsOp.getLoseprob();
		this.lossratio = oddsOp.getLossratio();
	}
	
	protected void setBaseInfo(OddsOp oddsOp)
	{
		this.mid = oddsOp.getMid();
		this.corpid = oddsOp.getCorpid();
		this.source = oddsOp.getSource();
	}
	/**
	 * 判断是否相等
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof RecordOddsOp)) return false;
		RecordOddsOp other = (RecordOddsOp) obj;
		return StringUtils.equals(mid, other.mid) &&
				StringUtils.equals(corpid, other.corpid);
	}
}
