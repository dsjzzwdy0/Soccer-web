/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ParamsException.java   
 * @Package com.loris.common.exception   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.exception;

import java.util.Map;

/**   
 * @ClassName: ParamsException   
 * @Description: 错误的参数  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ParamsException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamsException()
	{
	}
	
	public ParamsException(String info)
	{
		super(info);
	}
	
	public ParamsException(Map<String, String> infos)
	{
		super(infos.toString());
	}
	
}
