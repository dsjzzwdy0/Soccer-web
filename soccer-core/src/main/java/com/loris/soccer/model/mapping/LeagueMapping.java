/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LeagueMapping.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目的
 */
package com.loris.soccer.model.mapping;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.Mapping;

/**   
 * @ClassName:  LeagueMapping    
 * @Description: 联赛的映射  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@TableName("soccer_mapping_league")
public class LeagueMapping extends Mapping
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String sourcename;
	
	protected String destname;
	
	public LeagueMapping()
	{
	}
	
	public LeagueMapping(String sourcelid, String sourdename, String sourcefrom, String destlid, String destname,
			String sourcedest)
	{
		this.sourceid = sourcelid;
		this.sourcename = sourdename;
		this.sourcefrom = sourcefrom;
		this.destid = destlid;
		this.destname = destname;
		this.sourcedest = sourcedest;
	}
	
	public String getSourdename()
	{
		return sourcename;
	}
	public void setSourdename(String sourdename)
	{
		this.sourcename = sourdename;
	}

	public String getDestname()
	{
		return destname;
	}
	public void setDestname(String destname)
	{
		this.destname = destname;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "LeagueMapping [sourcename=" + sourcename + ", destname=" + destname + ", sourceid=" + sourceid
				+ ", sourcefrom=" + sourcefrom + ", destid=" + destid + ", sourcedest=" + sourcedest + ", id=" + id
				+ "]";
	}
}
