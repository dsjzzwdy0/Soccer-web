/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchMapping.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目的
 */
package com.loris.soccer.model.mapping;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.Mapping;

/**   
 * @ClassName:  MatchMapping    
 * @Description: 比赛编号的映射  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@TableName("soccer_mapping_match")
public class MatchMapping extends Mapping
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Date matchtime;
	
	/**
	 * create a new instance of MatchMapping
	 */
	public MatchMapping()
	{
	}
	
	/**
	 * Create the MatchMapping
	 * @param sourceid
	 * @param sourcefrom
	 * @param destid
	 * @param sourcedest
	 */
	public MatchMapping(String sourceid, String sourcefrom, String destid, String sourcedest, Date matchtime)
	{
		this.sourceid = sourceid;
		this.sourcefrom = sourcefrom;
		this.destid = destid;
		this.sourcedest = sourcedest;
		this.matchtime = matchtime;
	}
	
	/**
	 * @return the matchtime
	 */
	public Date getMatchtime()
	{
		return matchtime;
	}

	/**
	 * @param matchtime the matchtime to set
	 */
	public void setMatchtime(Date matchtime)
	{
		this.matchtime = matchtime;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MatchMapping [sourceid=" + sourceid + ", sourcefrom=" + sourcefrom + ", destid=" + destid
				+ ", sourcedest=" + sourcedest + ", matchtime=" + matchtime + "]";
	}
}
