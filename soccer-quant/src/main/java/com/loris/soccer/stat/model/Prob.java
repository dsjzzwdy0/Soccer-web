/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @Prob.java   
 * @Package com.loris.soccer.stat.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.model;

/**   
 * @ClassName:  Prob.java   
 * @Description: 胜率指标
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class Prob
{
	protected float winprob;
	protected float drawprob;
	protected float loseprob;
	/**
	 * @return the winprob
	 */
	public float getWinprob()
	{
		return winprob;
	}
	/**
	 * @param winprob the winprob to set
	 */
	public void setWinprob(float winprob)
	{
		this.winprob = winprob;
	}
	/**
	 * @return the drawprob
	 */
	public float getDrawprob()
	{
		return drawprob;
	}
	/**
	 * @param drawprob the drawprob to set
	 */
	public void setDrawprob(float drawprob)
	{
		this.drawprob = drawprob;
	}
	/**
	 * @return the loseprob
	 */
	public float getLoseprob()
	{
		return loseprob;
	}
	/**
	 * @param loseprob the loseprob to set
	 */
	public void setLoseprob(float loseprob)
	{
		this.loseprob = loseprob;
	}
}
