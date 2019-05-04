/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @SeasonInfo.java   
 * @Package com.loris.soccer.model.viewcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.Season;

/**   
 * @ClassName:  SeasonInfo.java   
 * @Description: 赛季数据信息   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_league_season_info")
public class SeasonInfo extends Season
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String leaguetype;
	private String name;
	/**
	 * @return the leaguetype
	 */
	public String getLeaguetype()
	{
		return leaguetype;
	}
	/**
	 * @param leagutetype the leaguetype to set
	 */
	public void setLeaguetype(String leagutetype)
	{
		this.leaguetype = leagutetype;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	@Override
	public String toString()
	{
		return "SeasonInfo [leagutetype=" + leaguetype + ", name=" + name + ", lid=" + lid + ", season=" + season
				+ "]";
	}
}
