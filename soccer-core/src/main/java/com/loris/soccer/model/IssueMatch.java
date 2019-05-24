/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  IssueMatch.java   
 * @Package com.loris.soccer.model.base   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.base.MatchItem;

/**   
 * @ClassName:  IssueMatch    
 * @Description: 期号数据   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_match_issue")
public class IssueMatch extends MatchItem
{
	/** */
	private static final long serialVersionUID = 1L;
	
	protected String issue;
	protected String issueno;			//期号
	protected String ordinary;			//排名
	protected Date closetime;			//关闭时间
	protected String type; 				//类型
	protected boolean isdelay;			//是否延迟
	protected Date delaytime;			//延迟时间
	public String getIssue()
	{
		return issue;
	}
	public void setIssue(String issue)
	{
		this.issue = issue;
	}
	public String getIssueno()
	{
		return issueno;
	}
	public void setIssueno(String issueno)
	{
		this.issueno = issueno;
	}
	public String getOrdinary()
	{
		return ordinary;
	}
	public void setOrdinary(String ordinary)
	{
		this.ordinary = ordinary;
	}
	public Date getClosetime()
	{
		return closetime;
	}
	public void setClosetime(Date closetime)
	{
		this.closetime = closetime;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public boolean isIsdelay()
	{
		return isdelay;
	}
	public void setIsdelay(boolean isdelay)
	{
		this.isdelay = isdelay;
	}
	public Date getDelaytime()
	{
		return delaytime;
	}
	public void setDelaytime(Date delaytime)
	{
		this.delaytime = delaytime;
	}
}
