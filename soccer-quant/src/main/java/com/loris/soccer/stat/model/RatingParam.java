/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @RatingParam.java   
 * @Package com.loris.soccer.stat.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.model;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  RatingParam.java   
 * @Description: 球队等级参数 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class RatingParam extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;

	protected float homekitty;				//主队奖池数据
	protected float clientkitty;			//客队奖池数据
	protected float goalwinpercent;			//赢球积分比例
	protected float cappownum;				//球队战绩对球队进球奖励系数
	protected boolean usecapratio;			//计算进球时是否采用系数法
	
	public RatingParam()
	{
		this.homekitty = 0.008f;
		this.clientkitty = 0.006f;
		this.goalwinpercent = 0.8f;
		this.cappownum = 1.0f;
		this.usecapratio = true;
	}
	
	/**
	 * @return the homekitty
	 */
	public float getHomekitty()
	{
		return homekitty;
	}
	/**
	 * @param homekitty the homekitty to set
	 */
	public void setHomekitty(float homekitty)
	{
		this.homekitty = homekitty;
	}
	/**
	 * @return the clientkitty
	 */
	public float getClientkitty()
	{
		return clientkitty;
	}
	/**
	 * @param clientkitty the clientkitty to set
	 */
	public void setClientkitty(float clientkitty)
	{
		this.clientkitty = clientkitty;
	}
	/**
	 * @return the goalwinpercent
	 */
	public float getGoalwinpercent()
	{
		return goalwinpercent;
	}
	/**
	 * @param goalwinpercent the goalwinpercent to set
	 */
	public void setGoalwinpercent(float goalwinpercent)
	{
		this.goalwinpercent = goalwinpercent;
	}
	/**
	 * @return the cappownum
	 */
	public float getCappownum()
	{
		return cappownum;
	}
	/**
	 * @param cappownum the cappownum to set
	 */
	public void setCappownum(float cappownum)
	{
		this.cappownum = cappownum;
	}
	/**
	 * @return the usecapratio
	 */
	public boolean isUsecapratio()
	{
		return usecapratio;
	}
	/**
	 * @param usecapratio the usecapratio to set
	 */
	public void setUsecapratio(boolean usecapratio)
	{
		this.usecapratio = usecapratio;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RatingParam [homekitty=" + homekitty + ", clientkitty=" + clientkitty + ", goalwinpercent="
				+ goalwinpercent + ", cappownum=" + cappownum + ", usecapratio=" + usecapratio + "]";
	}
}
