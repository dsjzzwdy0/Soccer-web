/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @Page.java   
 * @Package com.loris.common.web.wrappercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.common.web.wrapper;

/**   
 * @ClassName:  Page.java   
 * @Description: 数据分页内容  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class Page
{
	// 每页显示数量
    private int limit;
    
    // 页码
    private int page;
    
    // sql语句起始索引
    private int offset;

	/**
	 * @return the limit
	 */
	public int getLimit()
	{
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	/**
	 * @return the page
	 */
	public int getPage()
	{
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page)
	{
		this.page = page;
	}

	/**
	 * @return the offset
	 */
	public int getOffset()
	{
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Page [limit=" + limit + ", page=" + page + ", offset=" + offset + "]";
	}
}
