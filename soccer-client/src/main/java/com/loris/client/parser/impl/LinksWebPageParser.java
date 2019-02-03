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
package com.loris.client.parser.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.page.WebPage;
import com.loris.soccer.wrapper.TableRecords;

/**
 * @ClassName: LinksWebPageParser
 * @Description: 基础网页超链接数据解析器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LinksWebPageParser extends AbstractWebPageParser
{
	/**
	 * 解析网页中的所有超连接数据
	 * 
	 * @see com.loris.client.parser.WebPageParser#parse(com.loris.client.page.WebPage)
	 */
	@Override
	public TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Elements links = ((Element) document).select("a[href]");
		for (Element link : links)
		{
			String linkHref = link.attr("abs:href");
			String linkText = link.text();
			results.put(linkHref, linkText);
		}
		return results;
	}

}
