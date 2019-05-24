/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchOddsList.java   
 * @Package com.loris.soccer.model.complex   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.complex;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.model.CasinoComp;

/**   
 * @ClassName:  MatchOddsList    
 * @Description: 比赛数据赔率列表类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchOddsList extends ArrayList<MatchOdds>
{
	/***/
	private static final long serialVersionUID = 1L;

	protected String issue;
	protected String type;
	protected String sid;				//配置数据与信息
	protected String sname;				//配置名称
	protected List<CasinoComp> comps = new ArrayList<>();
	protected List<MatchOdds> matchOdds = new ArrayList<>();
	public String getIssue()
	{
		return issue;
	}
	public void setIssue(String issue)
	{
		this.issue = issue;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getSid()
	{
		return sid;
	}
	public void setSid(String sid)
	{
		this.sid = sid;
	}
	public String getSname()
	{
		return sname;
	}
	public void setSname(String sname)
	{
		this.sname = sname;
	}
	public List<CasinoComp> getComps()
	{
		return comps;
	}
	public void setComps(List<CasinoComp> comps)
	{
		this.comps.addAll(comps);
	}
	public List<MatchOdds> getMatchOdds()
	{
		return matchOdds;
	}
	public void setMatchOdds(List<MatchOdds> matchOdds)
	{
		this.matchOdds = matchOdds;
	}
	public void addMatchOdds(MatchOdds matchOdd)
	{
		this.matchOdds.add(matchOdd);
	}
	public MatchOdds getMatchOdds(String mid)
	{
		for (MatchOdds matchOdds : matchOdds)
		{
			if(StringUtils.equals(mid, matchOdds.getMid()))
			{
				return matchOdds;
			}
		}
		return null;
	}
}
