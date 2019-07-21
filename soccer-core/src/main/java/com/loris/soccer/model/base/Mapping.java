/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @Mapping.java   
 * @Package com.loris.soccer.model.basecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.model.base;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  Mapping.java   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class Mapping extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String sourceid;
	protected String sourcefrom;
	protected String destid;
	protected String sourcedest;
	/**
	 * @return the sourceid
	 */
	public String getSourceid()
	{
		return sourceid;
	}
	/**
	 * @param sourceid the sourceid to set
	 */
	public void setSourceid(String sourceid)
	{
		this.sourceid = sourceid;
	}
	/**
	 * @return the sourcefrom
	 */
	public String getSourcefrom()
	{
		return sourcefrom;
	}
	/**
	 * @param sourcefrom the sourcefrom to set
	 */
	public void setSourcefrom(String sourcefrom)
	{
		this.sourcefrom = sourcefrom;
	}
	/**
	 * @return the destid
	 */
	public String getDestid()
	{
		return destid;
	}
	/**
	 * @param destid the destid to set
	 */
	public void setDestid(String destid)
	{
		this.destid = destid;
	}
	/**
	 * @return the sourcedest
	 */
	public String getSourcedest()
	{
		return sourcedest;
	}
	/**
	 * @param sourcedest the sourcedest to set
	 */
	public void setSourcedest(String sourcedest)
	{
		this.sourcedest = sourcedest;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		
		Mapping other = (Mapping)obj;
		return StringUtils.equals(sourceid, other.sourceid) &&
				StringUtils.equals(sourcefrom, other.sourcefrom) &&
				StringUtils.equals(destid, other.destid) &&
				StringUtils.equals(sourcedest, other.sourcedest);
	}
}
