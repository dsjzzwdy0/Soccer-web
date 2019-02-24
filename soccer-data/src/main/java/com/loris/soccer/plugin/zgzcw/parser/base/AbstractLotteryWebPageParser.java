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
package com.loris.soccer.plugin.zgzcw.parser.base;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	protected String dataFormat = "\\d{4}[-]\\d{2}[-]\\d{2}";
	
	
	/**
	 * Create a new instance of AbstractLotteryWebPageParser
	 * @param acceptType
	 */
	public AbstractLotteryWebPageParser(String acceptType)
	{
		super(acceptType);
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
}
