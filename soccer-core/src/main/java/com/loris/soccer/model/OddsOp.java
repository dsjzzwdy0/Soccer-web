/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOp.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:29:11   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.OddsValue;

/**   
 * @ClassName:  OddsOp   
 * @Description: 欧赔数据   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:29:11   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_odds_op")
public class OddsOp extends OddsValue implements Cloneable
{
	/***/
	private static final long serialVersionUID = 1L;

	protected float drawodds;
	protected float drawkelly;
	protected float drawprob;
	
	/**
	 * Create a new instance of OddsOp
	 */
	public OddsOp()
	{
	}
	
	public OddsOp(String mid)
	{
		this.mid = mid;
	}

	public float getDrawodds()
	{
		return drawodds;
	}

	public void setDrawodds(float drawodds)
	{
		this.drawodds = drawodds;
	}

	public float getDrawkelly()
	{
		return drawkelly;
	}

	public void setDrawkelly(float drawkelly)
	{
		this.drawkelly = drawkelly;
	}

	public float getDrawprob()
	{
		return drawprob;
	}

	public void setDrawprob(float drawprob)
	{
		this.drawprob = drawprob;
	}

	@Override
	public String toString()
	{
		return "OddsOp[mid = " + mid + ", winodds=" + winodds + ", drawodds=" + drawodds  + ", loseodds=" + loseodds 
				  + ", winprob=" + winprob + ", drawprob=" + drawprob+ ", loseprob=" + loseprob + "]";
	}
}
