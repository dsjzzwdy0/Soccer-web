/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OpSequence.java   
 * @Package com.loris.soccer.wrapper.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.wrapper.model;

import java.util.ArrayList;
import java.util.List;

/**   
 * @ClassName:  OpSequence.java   
 * @Description: 欧赔数据的列表 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OpSequence
{
	protected String mid;
	protected List<OddsOpValue> list = new ArrayList<>();
	
	public OpSequence()
	{
		
	}
	public OpSequence(String mid)
	{
		this.mid = mid;
	}
	/**
	 * @return the mid
	 */
	public String getMid()
	{
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	/**
	 * @return the list
	 */
	public List<OddsOpValue> getList()
	{
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<OddsOpValue> list)
	{
		this.list = list;
	}
	
	public void add(OddsOpValue value)
	{
		list.add(value);
	}
}
