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
package com.loris.soccer.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.MatchItem;

/**
 * @ClassName: League
 * @Description: 比分数据的赔率
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@TableName("soccer_odds_score")
public class OddsScore extends MatchItem
{
	/***/
	private static final long serialVersionUID = 1L;

	protected String ordinary;
	protected String type; 				// 类型：竞彩比分、北单比分
	protected String oddsvalue; 		// 赔率列表值
	protected Date opentime; 			// 开盘时间
	protected String source; 			// 数据来源:

	public OddsScore()
	{
	}

	public OddsScore(String mid)
	{
		this.mid = mid;
	}

	public String getOrdinary()
	{
		return ordinary;
	}

	public void setOrdinary(String ordinary)
	{
		this.ordinary = ordinary;
	}

	public String getOddsvalue()
	{
		return oddsvalue;
	}

	public void setOddsvalue(String oddsvalue)
	{
		this.oddsvalue = oddsvalue;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public Date getOpentime()
	{
		return opentime;
	}

	public void setOpentime(Date opentime)
	{
		this.opentime = opentime;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !(obj instanceof OddsScore)) return false;
		OddsScore other = (OddsScore) obj;
		return StringUtils.equals(mid, other.mid)
				&& StringUtils.equals(ordinary, other.ordinary)
				&& StringUtils.equals(type, other.type);
	}

	@Override
	public String toString()
	{
		return "OddsScore [ordinary=" + ordinary + ", type=" + type + ", oddsvalue=" + oddsvalue + ", opentime="
				+ opentime + ", source=" + source + "]";
	}
}
