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

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.parser.base.AbstractOkoooPageParser;

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
		return null;
	}

}
