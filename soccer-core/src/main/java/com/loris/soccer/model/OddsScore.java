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
package com.loris.soccer.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.MatchResult.ResultType;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.model.base.Result;


/**
 * @ClassName: League
 * @Description: 比分数据的赔率
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@TableName("soccer_odds_score")
public class OddsScore extends MatchItem
{
	/***/
	private static final long serialVersionUID = 1L;
	
	public static final String ELSE_WIN = "胜其它";
	public static final String ELSE_DRAW = "平其它";
	public static final String ELSE_LOSE = "负其它";

	protected String ordinary;
	protected String type; 				// 类型：竞彩比分、北单比分
	protected String oddsvalue; 		// 赔率列表值
	protected Date opentime; 			// 开盘时间
	protected String source; 			// 数据来源: zgzcw, okooo
	
	@TableField(exist=false)
	protected Map<String, Float> params = new HashMap<>();

	public OddsScore()
	{
	}

	public OddsScore(String mid)
	{
		this.mid = mid;
	}

	public String getOrdinary()
	{
		return ordinary;
	}

	public void setOrdinary(String ordinary)
	{
		this.ordinary = ordinary;
	}

	public String getOddsvalue()
	{
		return oddsvalue;
	}

	public void setOddsvalue(String oddsvalue)
	{
		this.oddsvalue = oddsvalue;
		this.params.clear();
		decodeOddsScore(oddsvalue, params);
	}
	
	public void setOpentime(Date opentime)
	{
		this.opentime = opentime;
	}
	
	public void remove(String name)
	{
		this.params.remove(name);
		this.oddsvalue = encodeOddsScore(params);
	}

	/**
	 * @return the params
	 */
	public Map<String, Float> getParams()
	{
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Float> params)
	{
		this.params = params;
		this.oddsvalue = encodeOddsScore(params);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public Date getOpentime()
	{
		return opentime;
	}
	
	public int size()
	{
		return params.size();
	}
	
	/**
	 * 按照比赛结果获得赔率 
	 * @return
	 */
	public Map<Result, Float> getResultOdds()
	{
		if(params == null || params.size() <= 0) return null;
		Map<Result, Float> results = new HashMap<>();
		for (String score : params.keySet())
		{
			Result result = new Result(score);
			if(result.getResultType() == null)
			{
				setDefaultResult(score, result);
			}
			results.put(result, params.get(score));
		}		
		return results;
	}
	
	/**
	 * 设置默认的比赛结果
	 * @param score
	 * @param result
	 */
	protected void setDefaultResult(String score, Result result)
	{
		if(StringUtils.equals(ELSE_DRAW, score))
		{
			result.setResultType(ResultType.DRAW);
			result.setHomegoal(4);
			result.setClientgoal(4);
		}
		else if(StringUtils.equals(ELSE_WIN, score))
		{
			result.setResultType(ResultType.WIN);
			if(StringUtils.equals(type, SoccerConstants.LOTTERY_BD))
			{
				result.setHomegoal(5);
				result.setClientgoal(1);
			}
			else
			{
				result.setHomegoal(6);
				result.setClientgoal(1);
			}
		}
		else
		{
			result.setResultType(ResultType.LOSE);
			if(StringUtils.equals(type, SoccerConstants.LOTTERY_BD))
			{
				result.setHomegoal(1);
				result.setClientgoal(5);
			}
			else
			{
				result.setHomegoal(1);
				result.setClientgoal(6);
			}
		}
			
	}
	
	/**
	 * 加入数据内容
	 * @param name
	 * @param value
	 */
	public void put(String name, Float value)
	{
		this.params.put(name, value);
		this.oddsvalue = encodeOddsScore(params);
	}
	
	/**
	 * 解析赔率比分的数据
	 * @param text
	 * @param params
	 */
	public static void decodeOddsScore(String text, Map<String, Float> params)
	{
		String[] strs = text.split(";");
		for (String string : strs)
		{
			String[] recs = string.split("=");
			if(recs == null || recs.length != 2)
			{
				continue;
			}
			String name = recs[0];
			String value = recs[1];
			
			if(StringUtils.isEmpty(name) || StringUtils.isEmpty(value))
			{
				continue;
			}
			float odds = NumberUtil.parseFloat(value);
			if(odds <= 0)
			{
				continue;
			}
			
			params.put(name, odds);
		}
	}
	
	/**
	 * 把赔率比分数据编码成字符串表达的格式
	 * @param params 比分数据
	 * @return 字符串数据
	 */
	public static String encodeOddsScore(Map<String, Float> params)
	{
		String text = "";
		for (String name : params.keySet())
		{
			if(StringUtils.isNotEmpty(text))
			{
				text += ";";
			}
			text += name + "=" + params.get(name);
		}
		return text;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || !(obj instanceof OddsScore)) return false;
		OddsScore other = (OddsScore) obj;
		return StringUtils.equals(mid, other.mid)
				&& StringUtils.equals(ordinary, other.ordinary)
				&& StringUtils.equals(type, other.type)
				&& StringUtils.equals(source, other.source) 
				&& DateUtil.equals(opentime, other.opentime);
	}

	@Override
	public String toString()
	{
		return "OddsScore [ordinary=" + ordinary + ", type=" + type + ", oddsvalue=" + oddsvalue + ", opentime="
				+ opentime + ", source=" + source + "]";
	}
}
