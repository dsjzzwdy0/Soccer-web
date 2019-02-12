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

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.wrapper.TableRecords;;

/**   
 * @ClassName:  WebPageParser  
 * @Description: 网页解析工具  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface WebPageParser 
{
	String RIGHT_SPLASH = "/";
	
	/**
	 * 数据页面解析接口
	 * @param page 数据页面
	 * @return 数据结果
	 */
	TableRecords parse(WebPage page) throws WebParserException;
}
