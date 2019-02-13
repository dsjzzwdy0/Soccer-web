/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  PageInfo.java   
 * @Package com.loris.common.page   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.page;

/**   
 * @ClassName:  PageInfo    
 * @Description: 分页查询数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class PageInfo
{
	int index;
	int pernum;
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;
	}
	public int getPernum()
	{
		return pernum;
	}
	public void setPernum(int pernum)
	{
		this.pernum = pernum;
	}
}
