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
import com.loris.soccer.base.bean.AutoIdEntity;

/**   
 * @ClassName:  OddsOp   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:29:11   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_odds_op")
public class OddsOp extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;

	protected float winodds;
	protected float drawodds;
	protected float loseodds;
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
	@Override
	public String toString()
	{
		return "OddsOp [winodds=" + winodds + ", drawodds=" + drawodds + ", loseodds=" + loseodds + ", id=" + id + "]";
	}
}
