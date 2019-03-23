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
package com.loris.client.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

/**   
 * @ClassName:  SchedulerStatus  
 * @Description: 运行状态   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_scheduler_status")
public class SchedulerStatus extends SchedulerInfo
{
	/** */
	private static final long serialVersionUID = 1L;
	
	final public static int STATUS_FINISHED = 1;
	final public static int STATUS_INIT = 0;
	final public static int STATUS_STOP = 2;
	
	protected int total;		//总数
	protected int leftsize;		//剩余数
	protected int state;		//1表示处理完成、0表示创建、2表示暂停
	protected Date createtime;	//创建时间
	protected Date stoptime;	//停止时间
	protected Date finishtime;	//完成时间
	
	/**
	 * Create a new instance of SchedulerStatus.
	 */
	public SchedulerStatus()
	{
		this.createtime = new Date();
	}
	
	public SchedulerStatus(SchedulerInfo schedulerInfo)
	{
		this();
		setSchedulerInfo(schedulerInfo);
		this.stoptime = null;
		this.finishtime = null;
	}
	
	public void setSchedulerInfo(SchedulerInfo info)
	{
		this.sid = info.getSid();
		this.intervaltime = info.getIntervaltime();
		this.name = info.getName();
		this.type = info.getType();
		this.maxActiveTaskThread = info.getMaxActiveTaskThread();
		this.randTimeSeed = info.getRandTimeSeed();
		this.plugins = info.getPlugins();
	}
	
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getLeftsize()
	{
		return leftsize;
	}
	public void setLeftsize(int leftsize)
	{
		this.leftsize = leftsize;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	
	public void setStatus(int total, int leftsize, int state)
	{
		this.total = total;
		this.leftsize = leftsize;
		this.state = state;
	}	
	public void setStatus(SchedulerStatus status)
	{
		setStatus(status.getTotal(),  status.getLeftsize(), status.getState());		
	}
	public Date getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(Date createtime)
	{
		this.stoptime = null;
		this.createtime = createtime;
	}
	public Date getFinishtime()
	{
		return finishtime;
	}
	public void setFinishtime(Date finishtime)
	{
		this.stoptime = null;
		this.finishtime = finishtime;
	}
	public void setFinishtime()
	{
		setFinishtime(new Date());
	}
	
	public void setStoptime()
	{
		setStoptime(new Date());
	}

	public Date getStoptime()
	{
		return stoptime;
	}

	public void setStoptime(Date stoptime)
	{
		this.stoptime = stoptime;
	}

	@Override
	public String toString()
	{
		return "SchedulerStatus [total=" + total + ", leftsize=" + leftsize + ", createtime=" + createtime + "]";
	}
}
