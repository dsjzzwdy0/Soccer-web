/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @CasinoComp.java   
 * @Package com.loris.soccer.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  CasinoComp.java   
 * @Description: 博彩公司   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_comp_casino")
public class CasinoComp extends AutoIdEntity
{
	/** */
	private static final long serialVersionUID = 1L;
	
	private String corpid; 			// 编号
	private String name; 			// 名称
	private boolean ismain; 		// 是否主流
	private String type; 			// 欧赔公司op，亚盘公司 yp
	private String source; 			// 来源：okooo, zgzcw等
	
	/**
	 * @return the corpid
	 */
	public String getCorpid()
	{
		return corpid;
	}
	/**
	 * @param corpid the corpid to set
	 */
	public void setCorpid(String corpid)
	{
		this.corpid = corpid;
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
	/**
	 * @return the ismain
	 */
	public boolean isIsmain()
	{
		return ismain;
	}
	/**
	 * @param ismain the ismain to set
	 */
	public void setIsmain(boolean ismain)
	{
		this.ismain = ismain;
	}
	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == this)
		{
			return true;
		}
		if(obj == null || !(obj instanceof CasinoComp))
		{
			return false;
		}
		CasinoComp other = (CasinoComp)obj;
		return StringUtils.equals(type, other.getType()) 
				&& StringUtils.equals(corpid, other.getCorpid())
				&& StringUtils.equals(source, other.source);
	}
}
