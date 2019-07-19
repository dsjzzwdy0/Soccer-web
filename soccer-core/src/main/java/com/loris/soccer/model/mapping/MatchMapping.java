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

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  MatchMapping    
 * @Description: 比赛编号的映射  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@TableName("soccer_match_mapping")
public class MatchMapping extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String sourceid;
	protected String sourcefrom;
	protected String destid;
	protected String sourcedest;
	public String getSourceid()
	{
		return sourceid;
	}
	public void setSourceid(String sourceid)
	{
		this.sourceid = sourceid;
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
	public String getSourcedest()
	{
		return sourcedest;
	}
	public void setSourcedest(String sourcedest)
	{
		this.sourcedest = sourcedest;
	}
}
