/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractLotteryScoreWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser.base;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.OddsScoreList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.model.OddsScore;

/**   
 * @ClassName:  AbstractLotteryScoreWebPageParser  
 * @Description: 竞彩比分数据解析类
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractLotteryScoreWebPageParser extends AbstractLotteryWebPageParser
{	
	protected String type;
	
	/**
	 * @param acceptType
	 */
	public AbstractLotteryScoreWebPageParser(String acceptType, String oddsType)
	{
		super(acceptType);
		this.type = oddsType;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		OddsScoreList oddsScores = new OddsScoreList();
		parseOddsScores(document, oddsScores);
		results.put(SoccerConstants.SOCCER_DATA_ODDS_SCORE_LIST, oddsScores);
		return results;
	}
	
	/**
	 * 解析比分赔率数据
	 * @param document
	 * @param oddsScores
	 */
	protected void parseOddsScores(Document document, List<OddsScore> oddsScores) throws WebParserException
	{
		Elements elements = document.select(".tz-wap #dcc table");
		Date date = new Date();
		for (Element element : elements)
		{
			parseIssueOddsScore(element, date, oddsScores);
		}
	}
	
	/**
	 * 解析某一期的比分数据
	 * @param element
	 * @param oddsScores
	 * @throws WebParserException
	 */
	protected void parseIssueOddsScore(Element element, Date opentime, List<OddsScore> oddsScores) throws WebParserException
	{
		Elements elements = element.select("tr");
		for (Element element2 : elements)
		{
			String id = element2.attr("id");
			if(id.endsWith("_bf"))
			{
				continue;
			}
			if("0".equals(element2.attr("expire")))
			{
				continue;
			}
			
			OddsScore score = new OddsScore();
			score.setOpentime(opentime);
			score.setSource(ZgzcwConstants.SOURCE_ZGZCW);
			score.setType(type);
			
			parseIssueOddsScore(element2, score);
			
			if(StringUtils.isBlank(score.getMid()) || "0".equals(score.getMid()))
			{
				continue;
			}
			oddsScores.add(score);
		}
	}
	
	/**
	 * 解析数据值
	 * @param el
	 * @param score
	 * @throws WebParserException
	 */
	protected void parseIssueOddsScore(Element el, OddsScore score) throws WebParserException
	{
		Elements elements = el.select("td");
		for (Element element : elements)
		{
			if (element.hasClass("wh-1")) // 序号
			{
				String ord = element.selectFirst("i").text();
				score.setOrdinary(ord);
			}
			else if (element.hasClass("wh-2")) // 联赛
			{
			}
			else if (element.hasClass("wh-3")) // 比赛时间和截止时间
			{
			}
			else if (element.hasClass("wh-4")) // 主队信息与排名
			{

			}
			else if (element.hasClass("wh-5")) // 比分
			{

			}
			else if (element.hasClass("wh-6")) // 客队信息与排名
			{
			}
			else if (element.hasClass("wh-8")) // 比赛编号与欧亚盘等
			{
				String mid = element.attr("newPlayid");
				score.setMid(mid); // 比赛的ID编号
			}
			else if (element.hasClass("wh-11")) // 主队信息
			{
			}
		}
		
		String oddsValue = el.selectFirst("input").attr("value");
		score.setOddsvalue(oddsValue);
	}
}
