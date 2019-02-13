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

/**   
 * @ClassName:  SchedulerStatus  
 * @Description: 运行状态   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SchedulerStatus
{
	final public static int STATUS_FINISHED = 1;
	final public static int STATUS_INIT = 0;
	final public static int STATUS_STOP = 2;
	
	private String id;			//唯一编号
	private String name;		//名称
	private int total;			//总数
	private int leftsize;		//剩余数
	private int state;			//1表示处理完成、0表示创建、2表示暂停
	
	public SchedulerStatus()
	{
	}
	
	public SchedulerStatus(String id, String name, int total, int leftsize, int state)
	{
		this.id = id;
		this.name = name;
		this.total = total;
		this.leftsize = leftsize;
		this.state = state;
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
}
