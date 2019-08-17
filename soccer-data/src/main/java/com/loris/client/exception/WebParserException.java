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
package com.loris.client.exception;

/**   
 * @ClassName:  WebParserException  
 * @Description: 页面解析过程中出现的错误类，包括：页面内容为空、页面内容不符合规范等 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebParserException extends Exception
{
	/** */
	private static final long serialVersionUID = 1L;

	public WebParserException()
	{
	}

	public WebParserException(String message)
	{
		super(message);
	}

	public WebParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WebParserException(Throwable cause)
	{
		super(cause);
	}
}
