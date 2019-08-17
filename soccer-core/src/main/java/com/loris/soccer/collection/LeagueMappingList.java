/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @LeagueMappingList.java   
 * @Package com.loris.soccer.collectioncom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.collection;

import java.util.List;

import com.loris.soccer.collection.base.MappingList;
import com.loris.soccer.model.League;
import com.loris.soccer.model.mapping.LeagueMapping;

/**   
 * @ClassName:  LeagueMappingList.java   
 * @Description: 联赛映射数据表  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class LeagueMappingList extends MappingList<LeagueMapping>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a new instance of LeagueMappingList
	 */
	protected LeagueMappingList()
	{
	}
	
	/**
	 * 创建一个LeagueMappingList对象
	 * @param source 数据来源
	 * @param dest 数据目标
	 */
	public LeagueMappingList(String source, String dest)
	{
		super(source, dest);
	}
	
	/**
	 * 创建一个LeagueMappingList对象
	 * @param source 数据来源
	 * @param dest 数据目标
	 * @param mappings 映射数据
	 */
	public LeagueMappingList(String source, String dest, List<LeagueMapping> mappings)
	{
		this(source, dest);
		if(mappings != null && mappings.size() > 0)
		{
			this.addAll(mappings);
		}
	}
	
	/**
	 * 添加联赛数据映射
	 * @param source
	 * @param dest
	 */
	public void addMapping(League source, League dest)
	{
		this.addMapping(source.getLid(), source.getName(), dest.getLid(), dest.getName());
	}
	
	/**
	 * 添加联赛数据映射
	 * @param srclid
	 * @param srcname
	 * @param destlid
	 * @param destname
	 */
	public void addMapping(String srclid, String srcname, String destlid, String destname)
	{
		add(new LeagueMapping(srclid, srcname, source, destlid, destname, dest));
	}
}
