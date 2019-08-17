/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Enviroment.java   
 * @Package com.loris.soccer.constant   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.constant;

/**   
 * @ClassName:  Enviroment    
 * @Description: 系统环境   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class Enviroment
{
	/** 运行环境变量与数据 */
	public static boolean startJobScheduler = true;
	
	/** 默认的配置数据 */
	public static String defaultCompSettingId = "1011";
	
	/**
	 * 获得系统运行环境变量
	 * @param key
	 * @return
	 */
	public static String getProperty(String key)
	{
		return System.getProperty(key);
	}
}
