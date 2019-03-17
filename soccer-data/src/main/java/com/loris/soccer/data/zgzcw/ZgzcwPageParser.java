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
package com.loris.soccer.data.zgzcw;

import java.util.HashMap;
import java.util.Map;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.parser.WebPageParser;
import com.loris.common.model.TableRecords;
import com.loris.soccer.data.zgzcw.parser.CenterPageParser;
import com.loris.soccer.data.zgzcw.parser.CupWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LeagueWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryBdScoreWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryBdWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryJcScoreWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryJcWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsNumWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsOpWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsYpWebPageParser;

/**   
 * @ClassName: ZgzcwPageParser   
 * @Description: 数据解析器的应用类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwPageParser
{
	/** 数据解析器列有 */
	private static Map<String, WebPageParser> parsers = new HashMap<>();
	
	static
	{
		parsers.put(ZgzcwConstants.PAGE_CENTER, new CenterPageParser());
		parsers.put(ZgzcwConstants.PAGE_LEAGUE_LEAGUE, new LeagueWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_LEAGUE_CUP, new CupWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_LOTTERY_JC, new LotteryJcWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_SCORE_JC, new LotteryJcScoreWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_LOTTERY_BD, new LotteryBdWebPageParser());		
		parsers.put(ZgzcwConstants.PAGE_SCORE_BD, new LotteryBdScoreWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_ODDS_OP, new OddsOpWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_ODDS_YP, new OddsYpWebPageParser());
		parsers.put(ZgzcwConstants.PAGE_ODDS_NUM, new OddsNumWebPageParser());		
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
