/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsJsonOpPageParser.java   
 * @Package com.loris.soccer.data.zgzcw.parser   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractZgzcwJsonPageParser;

/**   
 * @ClassName:  OddsJsonOpPageParser    
 * @Description: 欧赔的数据解析器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsJsonOpPageParser extends AbstractZgzcwJsonPageParser
{
	/**
	 * @param acceptType
	 */
	public OddsJsonOpPageParser()
	{
		super(ZgzcwConstants.PAGE_JSON_ODDS_OP);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractJsonPageParser#parse(com.loris.client.model.WebPage, com.alibaba.fastjson.JSONObject, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Object jsonObject, TableRecords results) throws WebParserException
	{
		//JSONArray array = null;
		return null;
	}
}
