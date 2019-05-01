/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  JobInfo.java   
 * @Package com.loris.common.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  JobInfo    
 * @Description: Job任务的信息  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_job_info")
public class JobInfo extends AutoIdEntity
{
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	protected String jobname;
	protected String classname;
	protected String groupname;
	protected String cronexpression;
	protected String data;
	protected boolean enabled = true;
	protected Date createtime;
	protected Date modifytime;
	
	public JobInfo()
	{
	}
	
	public JobInfo(String jobname, String classname, String groupname, String cronexpression)
	{
		this.jobname = jobname;
		this.classname = classname;
		this.groupname = groupname;
		this.cronexpression = cronexpression;
	}
	
	public String getJobname()
	{
		return jobname;
	}
	public void setJobname(String jobname)
	{
		this.jobname = jobname;
	}
	public String getClassname()
	{
		return classname;
	}
	public void setClassname(String classname)
	{
		this.classname = classname;
	}
	public String getGroupname()
	{
		return groupname;
	}
	public void setGroupname(String groupname)
	{
		this.groupname = groupname;
	}
	public String getCronexpression()
	{
		return cronexpression;
	}
	public void setCronexpression(String cronExpression)
	{
		this.cronexpression = cronExpression;
	}
	public boolean isEnabled()
	{
		return enabled;
	}
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	public Date getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	public Date getModifytime()
	{
		return modifytime;
	}
	public void setModifytime(Date modifytime)
	{
		this.modifytime = modifytime;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
