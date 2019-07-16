/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooPageParser.java   
 * @Package com.loris.soccer.data.okooocom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo;

import java.util.HashMap;
import java.util.Map;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.parser.WebPageParser;
import com.loris.common.model.TableRecords;
import com.loris.soccer.data.okooo.parser.OkoooBdPageParser;
import com.loris.soccer.data.okooo.parser.OkoooJcPageParser;
import com.loris.soccer.data.okooo.parser.OkoooOddsOpChildPageParser;
import com.loris.soccer.data.okooo.parser.OkoooOddsOpPageParser;
import com.loris.soccer.data.okooo.parser.OkoooOddsYpChildPageParser;
import com.loris.soccer.data.okooo.parser.OkoooOddsYpPageParser;

/**   
 * @ClassName:  OkoooPageParser.java   
 * @Description: Okooo页面解析工具  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooPageParser
{
	/** 数据解析器列有 */
	private static Map<String, WebPageParser> parsers = new HashMap<>();
	
	static
	{
		parsers.put(OkoooConstants.PAGE_SCORE_BD, new OkoooBdPageParser());
		parsers.put(OkoooConstants.PAGE_SCORE_JC, new OkoooJcPageParser());
		parsers.put(OkoooConstants.PAGE_ODDS_OP, new OkoooOddsOpPageParser());
		parsers.put(OkoooConstants.PAGE_ODDS_YP, new OkoooOddsYpPageParser());
		parsers.put(OkoooConstants.PAGE_ODDS_YP_CHILD, new OkoooOddsYpChildPageParser());
		parsers.put(OkoooConstants.PAGE_ODDS_OP_CHILD, new OkoooOddsOpChildPageParser());
	}
	
	/**
	 * 解析页面数据，返回数据结果列有
	 * @param page 页面数据
	 * @return 数据结果
	 */
	public static TableRecords parseWebPage(WebPage page) throws WebParserException
	{
		WebPageParser parser = parsers.get(page.getType());
		if(parser == null)
		{
			throw new WebParserException("There are no parser can parse the '" + page.getType() + "'.");
		}	
		return parser.parse(page);
	}
}
