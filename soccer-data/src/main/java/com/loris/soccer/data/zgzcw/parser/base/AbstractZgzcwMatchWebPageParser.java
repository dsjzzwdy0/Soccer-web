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
import com.loris.soccer.constant.SoccerConstants;

/**   
 * @ClassName:  League   
 * @Description: 比赛数据页面的解析类,如百家欧赔、亚盘对比、大小对比、比赛历史等   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractZgzcwMatchWebPageParser extends AbstractZgzcwWebPageParser
{
	public final static String dataAttr = "data";
	public final static String compIdAttr = "cid";
	
	
	public AbstractZgzcwMatchWebPageParser(String acceptType)
	{
		super(acceptType);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.zgzcw.parser.base.AbstractZgzcwWebPageParser#accept(com.loris.client.model.WebPage)
	 */
	@Override
	protected boolean accept(WebPage page) throws WebParserException
	{
		if(!super.accept(page))
		{
			return false;
		}
		
		String mid = page.getParams().get(SoccerConstants.NAME_FIELD_MID);
		if(StringUtils.isEmpty(mid))
		{
			throw new WebParserException("Error occured, the Page hasn't set the 'mid' param.");
		}
		
		return true;
	}
}
