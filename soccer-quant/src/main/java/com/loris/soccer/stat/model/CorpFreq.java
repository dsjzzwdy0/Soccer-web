/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  CorpFreq.java   
 * @Package com.loris.soccer.stat.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**
 * @ClassName: CorpFreq
 * @Description: (@Todo)
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@TableName("soccer_stat_corp_freq")
public class CorpFreq extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String corpid;
	protected String name;
	protected String keyvalue;
	protected String type;
	protected int total;
	protected int freq;
	protected String params;
	
	public CorpFreq()
	{
	}
	
	public CorpFreq(String corpid)
	{
		this.corpid = corpid;
	}
	
	public String getCorpid()
	{
		return corpid;
	}
	public void setCorpid(String corpid)
	{
		this.corpid = corpid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the keyvalue
	 */
	public String getKeyvalue()
	{
		return keyvalue;
	}

	/**
	 * @param keyvalue the keyvalue to set
	 */
	public void setKeyvalue(String keyvalue)
	{
		this.keyvalue = keyvalue;
	}

	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getFreq()
	{
		return freq;
	}
	public void setFreq(int freq)
	{
		this.freq = freq;
	}
	public String getParams()
	{
		return params;
	}
	public void setParams(String params)
	{
		this.params = params;
	}
	
	public void addFreq()
	{
		freq ++;
	}
	public void addFreq(int freq)
	{
		this.freq += freq;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "CorpFreq [corpid=" + corpid + ", name=" + name + ", key=" + keyvalue + ", type=" + type + ", total=" + total
				+ ", freq=" + freq + ", params=" + params + "]";
	}
}
