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
package com.loris.client.scheduler.status;

import java.util.Date;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  SchedulerStatus  
 * @Description: 运行状态   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SchedulerStatus extends AutoIdEntity
{
	/** */
	private static final long serialVersionUID = 1L;
	final public static int STATUS_FINISHED = 1;
	final public static int STATUS_INIT = 0;
	final public static int STATUS_STOP = 2;

	private String sid;
	private String name;		//名称
	private int maxActiveTaskThread;
	private int intervaltime;
	private int randTimeSeed;
	private String type;
	private Date createtime;	//创建时间
	private Date stoptime;		//停止时间
	private Date finishtime;	//完成时间
	private int total;			//总数
	private int leftsize;		//剩余数
	private int state;			//1表示处理完成、0表示创建、2表示暂停
	
	public SchedulerStatus()
	{
		this.intervaltime = 200;
		this.randTimeSeed = -100;
		this.maxActiveTaskThread = 5;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
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

	public String getSid()
	{
		return sid;
	}

	public void setSid(String sid)
	{
		this.sid = sid;
	}

	public int getMaxActiveTaskThread()
	{
		return maxActiveTaskThread;
	}

	public void setMaxActiveTaskThread(int maxActiveTaskThread)
	{
		this.maxActiveTaskThread = maxActiveTaskThread;
	}

	public int getIntervaltime()
	{
		return intervaltime;
	}

	public void setIntervaltime(int intervaltime)
	{
		this.intervaltime = intervaltime;
	}

	public int getRandTimeSeed()
	{
		return randTimeSeed;
	}

	public void setRandTimeSeed(int randTimeSeed)
	{
		this.randTimeSeed = randTimeSeed;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	public void setCreatetime()
	{
		this.stoptime = null;
		setCreatetime(new Date());
	}

	public Date getFinishtime()
	{
		return finishtime;
	}

	public void setFinishtime(Date finishtime)
	{
		this.finishtime = finishtime;
	}
	public void setFinishtime()
	{
		setFinishtime(new Date());
	}

	public Date getStoptime()
	{
		return stoptime;
	}

	public void setStoptime(Date stoptime)
	{
		this.stoptime = stoptime;
	}
	
	public void setStoptime()
	{
		setStoptime(new Date());
	}
}
