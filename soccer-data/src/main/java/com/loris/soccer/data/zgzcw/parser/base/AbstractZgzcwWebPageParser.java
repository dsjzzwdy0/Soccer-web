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
package com.loris.soccer.data.zgzcw.parser.base;

import org.apache.commons.lang3.StringUtils;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.parser.impl.AbstractWebPageParser;
import com.loris.soccer.constant.SoccerConstants;

/**   
 * @ClassName:  AbstractZgzcwWebPageParser  
 * @Description: 中国足彩网网页解析父类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractZgzcwWebPageParser extends AbstractWebPageParser
{
	/** 接受的类型 */
	protected String acceptType = null;
	
	/**
	 * Create a new instance of AbstractZgzcwWebPageParser
	 * @param acceptType
	 */
	public AbstractZgzcwWebPageParser(String acceptType)
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
		
		if(!SoccerConstants.SOURCE_ZGZCW.equalsIgnoreCase(page.getSource()))
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
