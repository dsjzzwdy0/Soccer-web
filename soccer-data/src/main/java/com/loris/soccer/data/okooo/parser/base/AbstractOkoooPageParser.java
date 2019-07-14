/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @AbstractOkoooPageParser.java   
 * @Package com.loris.soccer.data.okooo.parser.basecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.parser.base;

import org.apache.commons.lang3.StringUtils;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.parser.impl.AbstractWebPageParser;
import com.loris.soccer.data.okooo.OkoooConstants;

/**   
 * @ClassName:  AbstractOkoooPageParser.java   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public abstract class AbstractOkoooPageParser extends AbstractWebPageParser
{
	/** 接受的类型 */
	protected String acceptType = null;
	
	/**
	 * Create a new instance of AbstractZgzcwWebPageParser
	 * @param acceptType
	 */
	public AbstractOkoooPageParser(String acceptType)
	{
		this.acceptType = acceptType;
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#accept(com.loris.client.model.WebPage)
	 */
	@Override
	protected boolean accept(WebPage page) throws WebParserException
	{
		if(!super.accept(page))
		{
			return false;
		}
		
		if(!OkoooConstants.SOURCE_OKOOO.equalsIgnoreCase(page.getSource()))
		{
			return false;
		}
		
		if(StringUtils.isBlank(acceptType) || !acceptType.equalsIgnoreCase(page.getType()))
		{
			return false;
		}
		return true;
	}
}
