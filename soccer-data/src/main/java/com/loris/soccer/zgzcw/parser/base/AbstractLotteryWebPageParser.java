/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractLotteryWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.zgzcw.parser.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.model.League;
import com.loris.soccer.service.LeagueService;

/**   
 * @ClassName:  AbstractLotteryWebPageParser  
 * @Description: 开盘数据的解析类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractLotteryWebPageParser extends AbstractZgzcwWebPageParser
{
	/** 日期格式数据 */
	protected static String dateFormat = "\\d{4}[-]\\d{2}[-]\\d{2}";
	
	/** 日期格式 */
	protected static Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}");
	
	/**  */
	protected LeagueList leagues = new LeagueList();
	
	/**
	 * Create a new instance of AbstractLotteryWebPageParser
	 * @param acceptType
	 */
	public AbstractLotteryWebPageParser(String acceptType)
	{
		super(acceptType);
		LeagueService leagueService = ApplicationContextHelper.getBean(LeagueService.class);
		if(leagueService != null)
		{
			leagues.addAll(leagueService.list());
		}
	}
	
	/**
	 * 解析比赛期号
	 * 
	 * @param document
	 * @return issue value.
	 */
	protected String parseIssueElement(Document document)
	{
		Element issueElement = document.selectFirst(".tz-condition .qici #selectissue");
		Elements elements = issueElement.children();
		for (Element element : elements)
		{
			if (element.hasAttr("selected"))
			{
				String issue = element.attr("value");
				return issue;
			}
		}
		return "";
	}
	
	/**
	 *  查询联赛数据
	 * @param name 名称或者编号
	 * @return 联赛数据
	 */
	protected League getLeague(String name)
	{
		return leagues.getLeague(name);
	}
	
	/**
	 * 从一个字符串中解析日期数据
	 * @param str 含有日期的数据
	 * @return 解析的数据
	 */
	public static String parseFirstDateString(String str)
	{
		Matcher matcher = pattern.matcher(str);
		if(matcher.find())
		{
			return matcher.group();
		}
		return null;
	}
	

	/**
	 * 解析球队数据
	 * @param el
	 * @return
	 */
	protected String parseTeamId(Element el)
	{
		Element element = el.selectFirst("a");
		if(element == null)
		{
			return null;
		}
		String url = element.attr("href");
		if(StringUtils.isNotBlank(url))
		{
			return NumberUtil.parseLastIntegerString(url);
		}
		return null;
	}
}


