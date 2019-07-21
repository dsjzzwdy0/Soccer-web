/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @TeamMappingList.java   
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
import com.loris.soccer.model.Team;
import com.loris.soccer.model.mapping.TeamMapping;

/**   
 * @ClassName:  TeamMappingList.java   
 * @Description: 球队映射数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class TeamMappingList extends MappingList<TeamMapping>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected TeamMappingList()
	{
	}
	
	public TeamMappingList(String source, String dest)
	{
		super(source, dest);
	}
	
	public TeamMappingList(String source, String dest, List<TeamMapping> list)
	{
		this(source, dest);
		if(list != null && list.size() > 0)
			this.addAll(list);
	}
	
	/**
	 * 添加球队数据映射
	 * @param source
	 * @param dest
	 */
	public void addMapping(Team source, Team dest)
	{
		this.addMapping(source.getTid(), source.getName(), dest.getTid(), dest.getName());
	}
	
	/**
	 * 添加球队数据映射
	 * @param srctid
	 * @param srcname
	 * @param desttid
	 * @param destname
	 */
	public void addMapping(String srctid, String srcname, String desttid, String destname)
	{
		add(new TeamMapping(srctid, srcname, source, desttid, destname, dest));
	}
}
