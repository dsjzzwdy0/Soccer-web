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
public class Pagination
{
	protected int limit;
	protected int offset;
	protected String order;
	protected String sort;
	protected String search;
	public int getLimit()
	{
		if(limit <= 0)
			limit = 5;
		return limit;
	}
	public void setLimit(int limit)
	{
		this.limit = limit;
	}
	public int getOffset()
	{
		return offset;
	}
	public void setOffset(int offset)
	{
		this.offset = offset;
	}
	public String getOrder()
	{
		return order;
	}
	public void setOrder(String order)
	{
		this.order = order;
	}
	public String getSearch()
	{
		return search;
	}
	public void setSearch(String search)
	{
		this.search = search;
	}
	public String getSort()
	{
		return sort;
	}
	public void setSort(String sort)
	{
		this.sort = sort;
	}
	/**
	 * 获得需要显示的数据页序号
	 * 这里以第1页作为起始页
	 * @return 页序号
	 */
	public int getPageIndex()
	{
		if(offset < 0)
			offset = 0;
		return offset / getLimit() + 1;
	}
	@Override
	public String toString()
	{
		return "Pagination [limit=" + limit + ", offset=" + offset + ", order=" + order + ", sort=" + sort + ", search="
				+ search + "]";
	}	
}
