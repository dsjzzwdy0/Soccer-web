/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ZgzcwPageParser.java   
 * @Package com.loris.soccer.data.zgzcw.util   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.util;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.LotteryBdWebPageParser;
import com.loris.soccer.data.zgzcw.parser.base.AbstractZgzcwWebPageParser;

/**   
 * @ClassName: ZgzcwPageParser   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwPageParser
{
	/**
	 * 解析数据
	 * @param page
	 * @return
	 */
	public static TableRecords parseWebPage(WebPage page) throws WebParserException
	{
		AbstractZgzcwWebPageParser pageParser = null;
		switch (page.getType())
		{
		case ZgzcwConstants.PAGE_LOTTERY_BD:
			pageParser = new LotteryBdWebPageParser();
			break;
		
		default:
			break;
		}
				
		if(pageParser == null)
		{
			throw new WebParserException("There are no parser can parse the '" + page.getType() + "'.");
		}		
		return pageParser.parse(page);
	}
}
