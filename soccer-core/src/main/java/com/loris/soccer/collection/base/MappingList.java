/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MappingList.java   
 * @Package com.loris.soccer.collection.basecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.collection.base;

import com.loris.soccer.model.base.Mapping;

/**   
 * @ClassName:  MappingList.java   
 * @Description: 数据映射列表   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class MappingList<T extends Mapping> extends DataList<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String source;
	protected String dest;

	protected MappingList()
	{
	}
	
	public MappingList(String source, String dest)
	{
		this.source = source;
		this.dest = dest;
	}

	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * @return the dest
	 */
	public String getDest()
	{
		return dest;
	}

	/**
	 * @param dest the dest to set
	 */
	public void setDest(String dest)
	{
		this.dest = dest;
	}
}
