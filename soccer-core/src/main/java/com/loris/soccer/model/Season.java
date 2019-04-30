/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Season.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName: Season   
 * @Description: 赛季数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_league_season")
public class Season  extends AutoIdEntity
{
	/** Serial Version ID*/
	private static final long serialVersionUID = 1L;

	protected String lid;
	protected String season;
	public String getLid()
	{
		return lid;
	}
	public void setLid(String lid)
	{
		this.lid = lid;
	}
	public String getSeason()
	{
		return season;
	}
	public void setSeason(String season)
	{
		this.season = season;
	}
	
	/**
	 * 判断两个对象是否相等
	 * @param obj 对象数据
	 */
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Season other = (Season) obj;
		return StringUtils.equals(other.lid, lid) && StringUtils.equals(season, other.season);
	}
	@Override
	public String toString()
	{
		return "Season [lid=" + lid + ", season=" + season + "]";
	}
}
