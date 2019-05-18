/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  UrlFetchException.java   
 * @Package com.loris.net.exception   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月29日 下午7:11:10   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.exception;

/**   
 * @ClassName:  UrlFetchException   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月29日 下午7:11:10   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class UrlFetchException extends Exception
{
	/***/
	private static final long serialVersionUID = 1L;

	public UrlFetchException()
	{
	}

	public UrlFetchException(String message)
	{
		super(message);
	}

	public UrlFetchException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UrlFetchException(Throwable cause)
	{
		super(cause);
	}
}
