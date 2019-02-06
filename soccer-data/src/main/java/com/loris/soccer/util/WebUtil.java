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
package com.loris.soccer.util;

import java.util.Map;

/**   
 * @ClassName:  League   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebUtil
{
	/**
	 * 组成访问页面地址
	 * @param basicUrl 基础服务地址
	 * @param method 
	 * @param params
	 * @return
	 */
	public static String makeDefaultUrl(String basicUrl, Map<String, String> params)
	{
		if(params == null)
		{
			return basicUrl;
		}
		if (basicUrl.contains("?"))
		{
			basicUrl += "&";
		}
		else
		{
			basicUrl += "?";
		}
		String paramStr = "";
		int i = 0;
		for (String key : params.keySet())
		{
			if (i != 0)
			{
				paramStr += "&";
			}
			paramStr += key + "=" + params.get(key);
			i++;
		}
		basicUrl += paramStr;
		return basicUrl;
	}
}
