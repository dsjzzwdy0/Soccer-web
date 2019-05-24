/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractLotteryMatchPageParser.java   
 * @Package com.loris.soccer.data.zgzcw.parser.base   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser.base;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.IssueMatchList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.constant.SoccerConstants;

/**   
 * @ClassName:  AbstractLotteryMatchPageParser    
 * @Description: 比赛数据页面解析器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractLotteryMatchWebPageParser extends AbstractLotteryWebPageParser
{
	protected String lotteryMatchType;
	
	/**
	 * @param acceptType
	 */
	public AbstractLotteryMatchWebPageParser(String acceptType, String lotteryMatchType)
	{
		super(acceptType);
		this.lotteryMatchType = lotteryMatchType;
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String issue = page.getParams().get(SoccerConstants.NAME_FIELD_ISSUE);
		if(StringUtils.isEmpty(issue))
		{
			issue = parseIssueElement(document);
		}
		IssueMatchList lotterMatches = new IssueMatchList();			//投注的比赛数据
		MatchList baseMatchs = new MatchList();							//基本比赛数据
		baseMatchs.setOverwrite(false);
		
		parseMatchList(document, issue, baseMatchs, lotterMatches, results);
		
		if(lotterMatches.size() > 0) results.put(lotteryMatchType, lotterMatches);
		if(baseMatchs.size() >0) results.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, baseMatchs);
		
		return results;
	}
	
	
	/**
	 * 解析比赛数据列表，包含基础比赛数据、北单或竞彩比赛数据
	 * @param document
	 * @param issue
	 * @param matchBds
	 * @param baseMatchs
	 */
	protected abstract void parseMatchList(Document document, String issue, MatchList baseMatchs, 
			IssueMatchList lotterMatches, TableRecords results);
}
