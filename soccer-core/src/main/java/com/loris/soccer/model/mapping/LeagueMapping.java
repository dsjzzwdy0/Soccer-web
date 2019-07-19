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
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  LeagueMapping    
 * @Description: 联赛的映射  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@TableName("soccer_league_mapping")
public class LeagueMapping extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String sourcelid;
	protected String sourdename;
	protected String sourcefrom;
	
	protected String destlid;
	protected String destname;
	protected String sourcedest;
	public String getSourcelid()
	{
		return sourcelid;
	}
	public void setSourcelid(String sourcelid)
	{
		this.sourcelid = sourcelid;
	}
	public String getSourdename()
	{
		return sourdename;
	}
	public void setSourdename(String sourdename)
	{
		this.sourdename = sourdename;
	}
	public String getSourcefrom()
	{
		return sourcefrom;
	}
	public void setSourcefrom(String sourcefrom)
	{
		this.sourcefrom = sourcefrom;
	}
	public String getDestlid()
	{
		return destlid;
	}
	public void setDestlid(String destlid)
	{
		this.destlid = destlid;
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
