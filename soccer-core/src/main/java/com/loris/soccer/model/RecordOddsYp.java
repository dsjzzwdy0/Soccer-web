/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @RecordOddsYp.java   
 * @Package com.loris.soccer.modelcom.loris.soccer.model   
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
import com.loris.soccer.dict.HandicapDict;

/**   
 * @ClassName:  RecordOddsYp.java   
 * @Description: 欧赔数据记录   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_record_odds_yp")
public class RecordOddsYp extends OddsYp
{
	/** */
	private static final long serialVersionUID = 1L;
	
	private Date firsttime;
	private float firstwinodds;
	private Float firsthandicap;
	private float firstloseodds;
	/**
	 * @return the firsttime
	 */
	public Date getFirsttime()
	{
		return firsttime;
	}
	/**
	 * @param firsttime the firsttime to set
	 */
	public void setFirsttime(Date firsttime)
	{
		this.firsttime = firsttime;
	}
	/**
	 * @return the firstwinodds
	 */
	public float getFirstwinodds()
	{
		return firstwinodds;
	}
	/**
	 * @param firstwinodds the firstwinodds to set
	 */
	public void setFirstwinodds(float firstwinodds)
	{
		this.firstwinodds = firstwinodds;
	}
	/**
	 * @return the firsthandicap
	 */
	public Float getFirsthandicap()
	{
		return firsthandicap;
	}
	/**
	 * @param firsthandicap the firsthandicap to set
	 */
	public void setFirsthandicap(Float firsthandicap)
	{
		this.firsthandicap = firsthandicap;
	}
	/**
	 * @return the firstloseodds
	 */
	public float getFirstloseodds()
	{
		return firstloseodds;
	}
	/**
	 * @param firstloseodds the firstloseodds to set
	 */
	public void setFirstloseodds(float firstloseodds)
	{
		this.firstloseodds = firstloseodds;
	}
	
	public String getFirstHandicapName()
	{
		return HandicapDict.getHandicapName(firsthandicap);
	}
	public void addOddsOp(OddsYp oddsYp)
	{
		if(StringUtils.isBlank(corpid))
		{
			this.setFirstOddsOp(oddsYp);
			this.setLastOddsOp(oddsYp);
			return;
		}
		if(firsttime == null || this.firsttime.getTime() > oddsYp.getOpentime().getTime())
		{
			setFirstOddsOp(oddsYp);
		}
		if(opentime == null || this.opentime.getTime() < oddsYp.getOpentime().getTime())
		{
			setLastOddsOp(oddsYp);
		}
	}
	
	public void setFirstOddsOp(OddsYp oddsYp)
	{
		setBaseInfo(oddsYp);
		this.firstwinodds = oddsYp.getWinodds();
		this.firsthandicap = oddsYp.getHandicap();
		this.firstloseodds = oddsYp.getLoseodds();
		this.firsttime = oddsYp.getOpentime();
	}
	
	public void setLastOddsOp(OddsYp oddsYp)
	{
		setBaseInfo(oddsYp);
		this.opentime = oddsYp.getOpentime();
		this.winodds = oddsYp.getWinodds();
		this.handicap = oddsYp.getHandicap();
		this.loseodds = oddsYp.getLoseodds();
		this.winkelly = oddsYp.getWinkelly();
		this.losekelly = oddsYp.getLosekelly();
		this.winprob = oddsYp.getWinprob();
		this.loseprob = oddsYp.getLoseprob();
		this.lossratio = oddsYp.getLossratio();
	}
	
	protected void setBaseInfo(OddsYp oddsYp)
	{
		this.mid = oddsYp.getMid();
		this.corpid = oddsYp.getCorpid();
		this.source = oddsYp.getSource();
	}
}
