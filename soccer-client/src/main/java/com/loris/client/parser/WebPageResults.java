/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.parser;

import java.util.HashMap;

/**   
 * @ClassName:  ParserResults  
 * @Description: 页面解析的结果类，可以是很多的内容，根据需要来进行定义 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebPageResults extends HashMap<String, Object>
{
	/***/
	private static final long serialVersionUID = 1L;

	protected boolean success; 			//解析页面是否成功
	protected String info;				//解析过程中的信息
	
	/**
	 * Create a new instance of ParserResults.
	 */
	public WebPageResults()
	{
		this(true, "Success");
	}
	
	/**
	 * 创建一个解析结果
	 * @param success
	 * @param info
	 */
	public WebPageResults(boolean success, String info)
	{
		this.success = success;
		this.info = info;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	/**
	 * 创建一个失败的结果
	 * @param info
	 * @return
	 */
	public static WebPageResults failure(String info)
	{
		return new WebPageResults(false, info);
	}
	
	/**
	 * 创建一个失败的数据结果
	 * @return
	 */
	public static WebPageResults failure()
	{
		return new WebPageResults(false, "Error result.");
	}
}
