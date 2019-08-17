/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  DataList.java   
 * @Package com.loris.soccer.collection.base   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.collection.base;

import java.util.ArrayList;

/**   
 * @ClassName: DataList   
 * @Description: 数据基本模型  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class DataList<T> extends ArrayList<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected boolean overwrite;
	
	public DataList()
	{
		this.overwrite = false;
	}

	public boolean isOverwrite()
	{
		return overwrite;
	}

	public void setOverwrite(boolean overwrite)
	{
		this.overwrite = overwrite;
	}
	
	@Override
	public boolean add(T obj)
	{
		if(this.contains(obj))
			return false;
		return super.add(obj);
	}
}
