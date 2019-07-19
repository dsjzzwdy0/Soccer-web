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

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  TeamMapping    
 * @Description: 球队信息映射  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TeamMapping extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String sourceid;
	protected String sourcename;
	protected String sourcefrom;
	protected String destid;
	protected String destname;
	protected String sourcedest;
	public String getSourceid()
	{
		return sourceid;
	}
	public void setSourceid(String sourceid)
	{
		this.sourceid = sourceid;
	}
	public String getSourcename()
	{
		return sourcename;
	}
	public void setSourcename(String sourcename)
	{
		this.sourcename = sourcename;
	}
	public String getSourcefrom()
	{
		return sourcefrom;
	}
	public void setSourcefrom(String sourcefrom)
	{
		this.sourcefrom = sourcefrom;
	}
	public String getDestid()
	{
		return destid;
	}
	public void setDestid(String destid)
	{
		this.destid = destid;
	}
	public String getDestname()
	{
		return destname;
	}
	public void setDestname(String destname)
	{
		this.destname = destname;
	}
	public String getSourcedest()
	{
		return sourcedest;
	}
	public void setSourcedest(String sourcedest)
	{
		this.sourcedest = sourcedest;
	}
}
