/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OddsValue.java   
 * @Package com.loris.soccer.wrapper.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.wrapper.model;

import java.util.Date;

import com.loris.soccer.model.OddsOp;

/**   
 * @ClassName:  OddsValue.java   
 * @Description: 欧赔数据列表  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsOpValue
{
	String corpid;
	Date opentime;
	float[] odds = new float[3];
	
	public OddsOpValue()
	{
	}
	
	public OddsOpValue(OddsOp op)
	{
		this.corpid = op.getCorpid();
		this.opentime = op.getOpentime();
		odds[0] = op.getWinodds();
		odds[1] = op.getDrawodds();
		odds[2] = op.getLoseodds();
	}
	
	/**
	 * @return the corpid
	 */
	public String getCorpid()
	{
		return corpid;
	}
	/**
	 * @param corpid the corpid to set
	 */
	public void setCorpid(String corpid)
	{
		this.corpid = corpid;
	}

	/**
	 * @return the odds
	 */
	public float[] getOdds()
	{
		return odds;
	}

	/**
	 * @param odds the odds to set
	 */
	public void setOdds(float[] odds)
	{
		this.odds = odds;
	}

	/**
	 * @return the opentime
	 */
	public Date getOpentime()
	{
		return opentime;
	}
	/**
	 * @param opentime the opentime to set
	 */
	public void setOpentime(Date opentime)
	{
		this.opentime = opentime;
	}
}
