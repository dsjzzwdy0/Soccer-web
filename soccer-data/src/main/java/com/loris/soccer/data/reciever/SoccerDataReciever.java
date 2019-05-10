/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SoccerDataReciever.java   
 * @Package com.loris.soccer.data.reciever   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.reciever;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loris.client.sender.impl.JsonDataReciever;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.service.impl.SoccerDataService;

/**   
 * @ClassName:  SoccerDataReciever    
 * @Description: 数据解析接收工具 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class SoccerDataReciever extends JsonDataReciever
{
	private static Logger logger = Logger.getLogger(SoccerDataReciever.class);
	
	@Autowired
	private SoccerDataService soccerDataService;

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.sender.impl.JsonDataReciever#processJSONObject(com.alibaba.fastjson.JSONObject)
	 */
	@Override
	protected boolean processJSONObject(JSONObject object)
	{
		for (String key : object.keySet())
		{
			processJsonObject(object, key);
		}
		return true;
	}
	
	/**
	 * 处理数据对象
	 * @param object
	 * @param key
	 * @return
	 */
	protected boolean processJsonObject(JSONObject object, String key)
	{
		try
		{
			switch (key)
			{
			case SoccerConstants.SOCCER_DATA_MATCH_LIST:
				List<Match> matchs = getObjectArray(object, key, Match.class);
				logger.info("Match size is : " + matchs.size());
				return true;
			case SoccerConstants.SOCCER_DATA_MATCH_BD_LIST:
				List<MatchBd> matchBds = getObjectArray(object, key, MatchBd.class);
				logger.info("Match size is : " + matchBds.size());
				return true;
			case SoccerConstants.SOCCER_DATA_OP_LIST:
				List<OddsOp> oddsOpList = getObjectArray(object, key, OddsOp.class);
				logger.info("Match size is : " + oddsOpList.size());
				return true;
			case SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST:
				List<MatchResult> resultList = getObjectArray(object, key, MatchResult.class);
				logger.info("Match size is : " + resultList.size());
				return soccerDataService.saveRecord(key, resultList);
			default:
				break;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.warn("Error occured when process Json object, msg: " + e.toString());
		}		
		return false;
	}
	
	/**
	 * 获得对象的列表数据
	 * @param object 数据对象
	 * @param key 关键字
	 * @param clazz 类型
	 * @return
	 */
	protected <T> List<T> getObjectArray(JSONObject object, String key, Class<T> clazz)
	{
		JSONArray array = object.getJSONArray(key);
		List<T> list = array.toJavaList(clazz);
		return list;
	}
	
	/**
	 * 获得数据的对象
	 * @param object
	 * @param key
	 * @param clazz
	 * @return
	 */
	protected <T> T getObject(JSONObject object, String key, Class<T> clazz)
	{
		return object.getObject(key, clazz);
	}
}
