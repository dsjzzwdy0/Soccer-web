/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  TeamMapping.java   
 * @Package com.loris.soccer.model.mapping   
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
 * @ClassName:  TeamMapping    
 * @Description: 球队信息映射  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@TableName("soccer_mapping_team")
public class TeamMapping extends Mapping
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String sourcename;
	protected String destname;
	
	public TeamMapping()
	{
	}
	
	public TeamMapping(String sourceid, String sourdename, String sourcefrom, String destid, String destname,
			String sourcedest)
	{
		this.sourceid = sourceid;
		this.sourcename = sourdename;
		this.sourcefrom = sourcefrom;
		this.destid = destid;
		this.destname = destname;
		this.sourcedest = sourcedest;
	}
	
	public String getSourcename()
	{
		return sourcename;
	}
	public void setSourcename(String sourcename)
	{
		this.sourcename = sourcename;
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
		return "TeamMapping [sourcename=" + sourcename + ", destname=" + destname + ", sourceid=" + sourceid
				+ ", sourcefrom=" + sourcefrom + ", destid=" + destid + ", sourcedest=" + sourcedest + ", id=" + id
				+ "]";
	}
}
