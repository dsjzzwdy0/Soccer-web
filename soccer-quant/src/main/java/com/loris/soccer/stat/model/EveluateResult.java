/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @EveluateResult.java   
 * @Package com.loris.soccer.stat.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.model;

import com.loris.common.util.NumberUtil;

/**   
 * @ClassName:  EveluateResult.java   
 * @Description: 数据评价结果  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class EveluateResult
{
	protected int total;
	protected int eveluatesize;
	protected double winerr;
	protected double drawerr;
	protected double loseerr;
	/**
	 * @return the total
	 */
	public int getTotal()
	{
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total)
	{
		this.total = total;
	}
	/**
	 * @return the eveluatesize
	 */
	public int getEveluatesize()
	{
		return eveluatesize;
	}
	/**
	 * @param eveluatesize the eveluatesize to set
	 */
	public void setEveluatesize(int eveluatesize)
	{
		this.eveluatesize = eveluatesize;
	}
	
	public void addTotal()
	{
		this.total ++;
	}
	
	public void addEveluatesize()
	{
		this.eveluatesize ++;
	}
	
	/**
	 * @return the winerr
	 */
	public double getWinerr()
	{
		return winerr;
	}
	/**
	 * @param winerr the winerr to set
	 */
	public void setWinerr(double winerr)
	{
		this.winerr = winerr;
	}
	/**
	 * @return the drawerr
	 */
	public double getDrawerr()
	{
		return drawerr;
	}
	/**
	 * @param drawerr the drawerr to set
	 */
	public void setDrawerr(double drawerr)
	{
		this.drawerr = drawerr;
	}
	/**
	 * @return the loseerr
	 */
	public double getLoseerr()
	{
		return loseerr;
	}
	/**
	 * @param loseerr the loseerr to set
	 */
	public void setLoseerr(double loseerr)
	{
		this.loseerr = loseerr;
	}
	/**
	 * @return the winerr
	 */
	public double getStdWinerr()
	{
		if(eveluatesize == 0) throw new IllegalArgumentException("The eveluate size is zero.");
		return Math.sqrt(winerr/eveluatesize);
	}
	/**
	 * @return the drawerr
	 */
	public double getStdDrawerr()
	{
		if(eveluatesize == 0) throw new IllegalArgumentException("The eveluate size is zero.");
		return Math.sqrt(drawerr/eveluatesize);
	}
	/**
	 * @return the loseerr
	 */
	public double getStdLoseerr()
	{
		if(eveluatesize == 0) throw new IllegalArgumentException("The eveluate size is zero.");
		return Math.sqrt(loseerr/eveluatesize);
	}
	
	/**
	 * 加入数据
	 * @param winerr
	 * @param drawerr
	 * @param loseerr
	 */
	public void addError(double winerr, double drawerr, double loseerr)
	{
		addEveluatesize();
		this.winerr += Math.pow(winerr, 2);
		this.drawerr += Math.pow(drawerr, 2);
		this.loseerr += Math.pow(loseerr, 2);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if(eveluatesize != 0)
		{
			return "EveluateResult [total=" + total + ", eveluatesize=" + eveluatesize 
				+ ", winerr=" + NumberUtil.formatDouble(2, getStdWinerr()) 
				+ ", drawerr=" + NumberUtil.formatDouble(2, getStdDrawerr())
				+ ", loseerr=" + NumberUtil.formatDouble(2, getStdLoseerr()) + "]";
		}
		else
		{
			return "EveluateResult [total=" + total + ", eveluatesize=" + eveluatesize +  "]";
		}
	}
}
