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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loris.client.sender.impl.JsonDataReciever;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.Season;
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
	
	/** 数据类型 */
	static Map<String, Class<?>> typeClasses = new HashMap<>();
	
	static {
		typeClasses.put(SoccerConstants.SOCCER_DATA_LEAGUE_LIST, League.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_LEAGUE_SEASON_LIST, Season.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, Match.class);		
		typeClasses.put(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST, MatchBd.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST, MatchBd.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST, MatchResult.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_ODDS_OP_LIST, OddsOp.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_ODDS_YP_LIST, OddsYp.class);
		typeClasses.put(SoccerConstants.SOCCER_DATA_ODDS_NUM_LIST, OddsNum.class);
	}

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
			Object value = null;
			Class<?> clazz = typeClasses.get(key);
			if(clazz == null)
			{
				logger.warn("The key '" + value + "' class type is not registerd, there will be no data processed.");
				return false;
			}
			
			switch (key)
			{
			case SoccerConstants.SOCCER_DATA_LEAGUE:
				value = getObject(object, key, clazz);
				break;
			default:
				value = getObjectArray(object, key, clazz);
				break;
			}
			
			if(value == null)
			{
				logger.warn("The key '" + value + "' is null, there will be no data processed.");
				return false;
			}
			
			return soccerDataService.saveRecord(key, value);
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
