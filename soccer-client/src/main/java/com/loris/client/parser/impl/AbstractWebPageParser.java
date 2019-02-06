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

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.loris.client.exception.WebParserException;
import com.loris.client.page.WebPage;
import com.loris.client.parser.WebPageParser;
import com.loris.common.wrapper.TableRecords;

/**   
 * @ClassName:  AbstractWebPageParser  
 * @Description: 基础数据解析器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractWebPageParser implements WebPageParser
{	
	/**
	 * 是否能够被解析
	 * @param page 网页页面
	 * @return 是否能够被解析的标志
	 */
	protected boolean accept(WebPage page) throws WebParserException
	{
		return true;
	}
	
	/**
	 * 解析网页数据页面
	 * @see com.loris.client.parser.WebPageParser#parse(com.loris.client.page.WebPage)
	 */
	@Override
	public TableRecords parse(WebPage page) throws WebParserException
	{
		if(page == null)
		{
			throw new WebParserException("The page is null, failed to parse.");
		}
		
		if(!accept(page))
		{
			throw new WebParserException("The page '" + page.getType() + "' is not be accepted by this Parser '" + getClass().getName() + "'");
		}
		
		if(StringUtils.isAllEmpty(page.getContent()))
		{
			throw new WebParserException("The page content is empty, there are no content bo be parsed.");
		}
		
		Document document = Jsoup.parse(page.getContent());
		if(document == null)
		{
			throw new WebParserException("Error occured when parse the page content, not invalid html document.");
		}
		
		TableRecords results = new TableRecords();
		return parse(page, document, results);		
	}
	
	/**
	 * 解析网页页面中的数据
	 * @param page
	 * @param document
	 * @return
	 * @throws WebParserException
	 */
	protected abstract TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException;
}
