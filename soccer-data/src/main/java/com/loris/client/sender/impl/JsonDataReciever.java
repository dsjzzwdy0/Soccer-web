/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  JsonDataReciever.java   
 * @Package com.loris.client.sender.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.sender.impl;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loris.client.sender.DataReciever;

/**   
 * @ClassName:  JsonDataReciever    
 * @Description: 数据处理器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class JsonDataReciever implements DataReciever
{
	private static Logger logger = Logger.getLogger(JsonDataReciever.class);
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.sender.DataReciever#recieve(java.lang.String)
	 */
	@Override
	public boolean recieve(String text)
	{
		try
		{
			JSONObject object = JSON.parseObject(text);
			return processJSONObject(object);
		}
		catch (Exception e) 
		{
			logger.warn("Error when process json text, msg: " + e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 后处理JSONObject
	 * @param object 数据对象
	 * @return 是否成功的标志
	 */
	abstract protected boolean processJSONObject(JSONObject object); 
}
