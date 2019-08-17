/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  HttpSender.java   
 * @Package com.loris.client.sender   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.sender;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.loris.common.web.wrapper.Rest;

/**   
 * @ClassName:  HttpSender    
 * @Description: 向服务器发送数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface DataSender
{
	/**
	 * 发送数据
	 * @param records
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	<T> Rest send(Map<String, T> records) throws IOException, HttpException;
	
	/**
	 * 向服务器提交列表数据
	 * @param key 关键字
	 * @param records 内容
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	<T> Rest send(String key, Map<String, T> records) throws IOException, HttpException;
}
