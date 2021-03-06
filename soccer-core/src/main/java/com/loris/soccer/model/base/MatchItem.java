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
package com.loris.soccer.model.base;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  League   
 * @Description: 比赛元素数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchItem extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;
	
	protected String mid;				//比赛编号：唯一编号
	protected Date matchtime;			//比赛时间
	
	public MatchItem()
	{
	}
	
	public MatchItem(String mid)
	{
		this.mid = mid;
	}

	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	public Date getMatchtime()
	{
		return matchtime;
	}

	public void setMatchtime(Date matchtime)
	{
		this.matchtime = matchtime;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !StringUtils.equals(getClass().getName(), obj.getClass().getName())) return false;
		MatchItem other = (MatchItem)obj;
		return StringUtils.equals(mid, other.mid);
	}
}
