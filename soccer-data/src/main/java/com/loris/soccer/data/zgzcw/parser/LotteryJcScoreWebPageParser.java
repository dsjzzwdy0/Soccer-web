/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LotteryJcScoreWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser;

import org.jsoup.nodes.Document;

import com.loris.client.exception.WebParserException;
import com.loris.client.page.WebPage;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractLotteryScoreWebPageParser;

/**   
 * @ClassName:  LotteryJcScoreWebPageParser  
 * @Description: 竞彩比赛比分数据的解析类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LotteryJcScoreWebPageParser extends AbstractLotteryScoreWebPageParser
{
	/**
	 * Create a new instance of LotteryJcScoreWebPageParser.
	 */
	public LotteryJcScoreWebPageParser()
	{
		super(ZgzcwConstants.PAGE_SCORE_JC);
		
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.page.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.wrapper.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		return null;
	}

}
