/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooBdScoreParser.java   
 * @Package com.loris.soccer.data.okooo.parsercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.parser;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.parser.base.AbstractOkoooPageParser;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooTeam;

/**   
 * @ClassName:  OkoooBdScoreParser.java   
 * @Description: 解析北单比分数据页面 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooBdScoreParser extends AbstractOkoooPageParser
{
	private static Logger logger = Logger.getLogger(OkoooBdScoreParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooBdScoreParser()
	{
		super(OkoooConstants.PAGE_BD_SCORE);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Elements elements = document.select("#gametablesend .jcmaintable");
		if(elements == null || elements.size() <= 0)
		{
			logger.warn("There are no #gametablesend .jcmaintable in the html content.");
			return null;
		}
		
		String issueno = getIssueNo(document.selectFirst(".danchangtop #SelectLotteryNo"));
		
		DataList<OddsScore> oddsScores = new DataList<>();
		DataList<OkoooIssueMatch> issueMatchs = new DataList<>();
		DataList<OkoooMatch> okoooMatchs = new DataList<>();
		DataList<OkoooTeam> okoooTeams = new DataList<>();
		DataList<OkoooLeague> okoooLeagues = new DataList<>();
		
		oddsScores.setOverwrite(true);
		okoooMatchs.setOverwrite(false);
		issueMatchs.setOverwrite(false);
		okoooTeams.setOverwrite(false);
		okoooLeagues.setOverwrite(false);
		
		results.put(SoccerConstants.SOCCER_DATA_ODDS_SCORE_LIST, oddsScores);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_LIST, okoooMatchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_BD_LIST, issueMatchs);
		results.put(SoccerConstants.SOCCER_DATA_LEAGUE_OKOOO_LIST, okoooLeagues);
		results.put(SoccerConstants.SOCCER_DATA_TEAM_OKOOO_LIST, okoooTeams);
		
		for (Element element : elements)
		{
			parseOddsScores(element, issueno, oddsScores, okoooMatchs, issueMatchs,
					okoooLeagues, okoooTeams);
		}		
		return results;
	}
	
	/**
	 * 解析比分赔率数据列表
	 * @param element 数据元素
	 * @param oddsScores 比分数据赔率
	 */
	protected void parseOddsScores(Element element, String issueno, 
			List<OddsScore> oddsScores,
			List<OkoooMatch> okoooMatchs,
			List<OkoooIssueMatch> issueMatchs,
			List<OkoooLeague> leagues,
			List<OkoooTeam> teams)
	{
		Elements elements = element.select("tbody tr");
		int size = elements.size();
		
		String issue = parseIssue(elements.get(0).selectFirst(".MatchTitle span span"));
		logger.info("Current issue: " + issue);
				
		int matchSize = size / 2;
		for(int i = 0; i < matchSize; i ++)
		{
			Element el1 = elements.get(i * 2 + 1);
			Element el2 = elements.get(i * 2 + 2);
			parseOddsScore(el1, el2, issueno, issue, oddsScores, okoooMatchs, issueMatchs,
					leagues, teams);
		}
	}
	
	/**
	 * 解析比分数据
	 * @param el1
	 * @param el2
	 * @param issue
	 * @param oddsScores
	 */
	protected void parseOddsScore(Element el1, Element el2, String issueno, String issue,
			List<OddsScore> oddsScores,
			List<OkoooMatch> okoooMatchs,
			List<OkoooIssueMatch> issueMatchs,
			List<OkoooLeague> leagues,
			List<OkoooTeam> teams)
	{
		String mid;
		String lid;
		String leaguename;
		String mtime;			//比赛时间
		String ctime;			//关闭时间
		String homeid = "";
		String homename = "";
		String clientid = "";
		String clientname = "";
		String ordinary;
				
		Element element = null;
		Elements matchElements = el1.children();
		
		mid = el1.attr("matchid");
		//联赛数据信息
		element = matchElements.get(0);	
		ordinary = element.selectFirst("span i").text();
		element = element.selectFirst("a");
		leaguename = element.attr("title");
		lid = getLeadueId(element.attr("href"));
		
		//比赛时间与关闭时间
		element = matchElements.get(1);
		mtime = element.attr("title");
		ctime = element.selectFirst(".BuyTime").attr("endtime");
		
		//对阵双方
		element = matchElements.get(2);
		Elements teamElements = element.select("a");
		if(teamElements.size() == 2)
		{
			homeid = teamElements.get(0).attr("attr");
			homename = teamElements.get(0).attr("title");
			clientid = teamElements.get(1).attr("attr");
			clientname = teamElements.get(1).attr("title");
		}
		
		Date matchtime = parseMatchtime(mtime);
		Date closetime = getCloseTime(matchtime, ctime);
		
		OkoooMatch baseMatch = new OkoooMatch();
		OkoooIssueMatch issueMatch = new OkoooIssueMatch();
		OkoooLeague league = new OkoooLeague();
		OkoooTeam homeTeam = new OkoooTeam();
		OkoooTeam clientTeam = new OkoooTeam();
		OddsScore score = new OddsScore();
		
		baseMatch.setMid(mid);
		baseMatch.setLid(lid);
		baseMatch.setHomeid(homename);
		baseMatch.setClientid(clientname);
		baseMatch.setMatchtime(matchtime);
				
		issueMatch.setMid(mid);
		issueMatch.setIssue(issue);
		issueMatch.setIssueno(issueno);
		issueMatch.setOrdinary(ordinary);
		issueMatch.setMatchtime(matchtime);
		issueMatch.setClosetime(closetime);
		issueMatch.setType(SoccerConstants.LOTTERY_BD);
		
		league.setLid(lid);
		league.setName(leaguename);
		
		homeTeam.setTid(homeid);
		homeTeam.setName(homename);
		clientTeam.setId(clientid);
		clientTeam.setName(clientname);
		
		score.setMid(mid);
		score.setSource(SoccerConstants.SOURCE_OKOOO);
		score.setMatchtime(matchtime);
		score.setType(SoccerConstants.LOTTERY_BD);
		score.setOpentime(new Date());
		score.setOrdinary(ordinary);
		
		parseScore(el2, score);
		
		if(score.size() >= 20)
		{
			okoooMatchs.add(baseMatch);
			oddsScores.add(score);
			issueMatchs.add(issueMatch);
			teams.add(homeTeam);
			teams.add(clientTeam);
			leagues.add(league);
		}
	}
	
	/**
	 * 解析比分的赔率数据
	 * @param element 元素
	 * @param score 比分数据
	 * @return 比分的数据内容
	 */
	protected void parseScore(Element element, OddsScore score)
	{
		Elements elements = element.select("p a");
		for (Element el : elements)
		{
			Elements els = el.children();
			if(els != null && els.size() == 2)
			{
				String name = els.get(0).text();
				String value = els.get(1).text();
				
				float odds = NumberUtil.parseFloat(value);
				
				if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value) && odds > 0)
				{
					score.put(name, odds);
				}
			}
		}
	}
	
	/**
	 * 解析当前的数据期号
	 * @param el DOM元素
	 * @return 期号数据
	 */
	private String parseIssue(Element el)
	{
		String str = el.text();
		str = DateUtil.parseFirstDayString(str);
		return str;
	}
	
	/**
	 * 
	 * @param mtime
	 * @return
	 */
	protected Date parseMatchtime(String mtime)
	{
		String str = DateUtil.parseFirstDateString(mtime);
		if(StringUtils.isNotEmpty(str))
		{
			return DateUtil.tryToParseDate(str);
		}
		else
		{
			return DateUtil.tryToParseDate(mtime);
		}
	}
	
	/**
	 * 计算关闭的时间
	 * @param matchtime 比赛时间
	 * @param ctime 关闭的时间
	 */
	@Override
	protected Date getCloseTime(Date matchtime, String ctime)
	{
		if(matchtime == null)
		{
			return null;
		}
		Date closetime = DateUtil.tryToParseDate(DateUtil.getDayString(matchtime) + " " + ctime);
		if(closetime != null && closetime.getTime() > matchtime.getTime())
		{
			closetime = DateUtil.addDayNum(closetime, -1);
		}
		return closetime;
	}

}
