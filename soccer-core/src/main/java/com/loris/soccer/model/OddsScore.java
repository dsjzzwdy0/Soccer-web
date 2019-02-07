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

import com.baomidou.mybatisplus.annotation.TableName;

/**   
 * @ClassName:  League   
 * @Description: 比分数据的赔率
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_odds_score")
public class OddsScore extends MatchResult
{
	/***/
	private static final long serialVersionUID = 1L;

	protected String type;				//类型：竞彩比分、北单比分
	protected Float oddsvalue;			//赔率
	protected String name;				//名称
	protected long opentime;			//开盘时间
	protected String source;			//数据来源: 
	
	public OddsScore()
	{
	}
	
	public OddsScore(String mid)
	{
		this.mid = mid;
	}
	
	public Float getOddsvalue()
	{
		return oddsvalue;
	}
	public void setOddsvalue(Float oddsvalue)
	{
		this.oddsvalue = oddsvalue;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
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

	public long getOpentime()
	{
		return opentime;
	}

	public void setOpentime(long opentime)
	{
		this.opentime = opentime;
	}	
}
