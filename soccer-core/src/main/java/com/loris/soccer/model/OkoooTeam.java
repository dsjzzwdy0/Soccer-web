/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooTeam.java   
 * @Package com.loris.soccer.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.model;

import com.baomidou.mybatisplus.annotation.TableName;

/**   
 * @ClassName:  OkoooTeam.java   
 * @Description: 澳客球队数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
@TableName("okooo_league_team")
public class OkoooTeam extends Team
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String alias;
	
	public OkoooTeam()
	{
	}

	public OkoooTeam(String tid, String name, String country)
	{
		this.tid = tid;
		this.name = name;
		this.country = country;
	}

	/**
	 * @return the alias
	 */
	public String getAlias()
	{
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias)
	{
		this.alias = alias;
	}
}
