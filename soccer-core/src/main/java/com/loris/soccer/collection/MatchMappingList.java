/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchMappingList.java   
 * @Package com.loris.soccer.collectioncom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.collection;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.collection.base.MappingList;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.model.mapping.MatchMapping;

/**   
 * @ClassName:  MatchMappingList.java   
 * @Description: 比赛数据映射列表数据   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class MatchMappingList extends MappingList<MatchMapping>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a new instance of MatchMappingList
	 */
	protected MatchMappingList()
	{
	}
	
	/**
	 * 比赛映射列表数据
	 * @param source 源数据
	 * @param dest 目标数据
	 * @param list 我表
	 */
	public MatchMappingList(String source, String dest, List<MatchMapping> list)
	{
		this(source, dest);
		if(list != null && list.size() > 0)
			this.addAll(list);
	}
	
	/**
	 * Create a instance of MatchMappingList
	 * @param source
	 * @param dest
	 */
	public MatchMappingList(String source, String dest)
	{
		super(source, dest);
	}
	
	/**
	 * 检测是否已经匹配
	 * @param mid 比赛编号
	 * @return 是否存在的标志
	 */
	public boolean isSourceExist(String mid)
	{
		for (MatchMapping map : this)
		{
			if(StringUtils.equals(mid, map.getSourceid()))
				return true;
		}
		return false;
	}
	
	/**
	 * 检测是否已经匹配
	 * @param mid 比赛编号
	 * @return 是否存在的标志
	 */
	public boolean isDestExist(String mid)
	{
		for (MatchMapping map : this)
		{
			if(StringUtils.equals(mid, map.getDestid()))
				return true;
		}
		return false;
	}
	
	/**
	 * 加入比赛映射数据
	 * @param sourceid 比赛编号
	 * @param destid 比赛编号
	 */
	public void addMapping(String sourceid, String destid, Date matchtime)
	{
		add(new MatchMapping(sourceid, source, destid, dest, matchtime));
	}
	
	/**
	 * 添加比赛的映射
	 * @param source 比赛数据
	 * @param dest 比赛数据
	 */
	public void addMapping(MatchItem source, MatchItem dest)
	{
		addMapping(source.getMid(), dest.getMid(), source.getMatchtime());
	}
}
