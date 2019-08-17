/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsYp.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:29:52   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.dict.HandicapDict;
import com.loris.soccer.model.base.OddsValue;

/**   
 * @ClassName:  OddsYp   
 * @Description: 亚盘数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:29:52   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_odds_yp")
public class OddsYp extends OddsValue
{
	/** */
	private static final long serialVersionUID = 1L;
	
	protected Float handicap;			//让球数据
	
	public OddsYp()
	{
	}
	public OddsYp(String mid)
	{
		this.mid = mid;
	}
	public Float getHandicap()
	{
		return handicap;
	}
	public void setHandicap(Float handicap)
	{
		this.handicap = handicap;
	}
	public String getHandicapName()
	{
		return HandicapDict.getHandicapName(handicap);
	}
	@Override
	public String toString()
	{
		return "OddsYp [handicap=" + handicap + ", mid=" + mid + ", corpid=" + corpid + ", opentime=" + opentime
				+ ", winodds=" + winodds + ", loseodds=" + loseodds + ", winkelly=" + winkelly + ", losekelly="
				+ losekelly + ", winprob=" + winprob + ", loseprob=" + loseprob + ", lossratio=" + lossratio
				+ ", source=" + source + ", id=" + id + "]";
	}
}
