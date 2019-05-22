/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @CorpSetting.java   
 * @Package com.loris.soccer.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  CorpSetting.java   
 * @Description: 博彩公司基本配置信息
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_comp_setting")
public class CompSetting extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;
	
	protected String sid;					//编号
	protected String name;					//名称
	protected String user;					//创建用户
	protected Date createtime;				//创建时间
	protected Date modifytime;				//最后修改时间
	protected String source; 				//数据来源
	protected String params = "";   		//配置参数
	
	/**
	 * 创建一个新的配置信息
	 */
	public void create()
	{
		sid = UUID.randomUUID().toString();
		this.createtime = new Date();
		this.modifytime = null;
	}

	/**
	 * @return the sid
	 */
	public String getSid()
	{
		return sid;
	}

	/**
	 * @param sid the sid to set
	 */
	public void setSid(String sid)
	{
		this.sid = sid;
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
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the createtime
	 */
	public Date getCreatetime()
	{
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}

	/**
	 * @return the modifytime
	 */
	public Date getModifytime()
	{
		return modifytime;
	}

	/**
	 * @param modifytime the modifytime to set
	 */
	public void setModifytime(Date modifytime)
	{
		this.modifytime = modifytime;
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
	
	/**
	 * @return the params
	 */
	public String getParams()
	{
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String params)
	{
		this.params = params;
	}

	/**
	 * 设置博彩公司列表
	 * @param comps
	 */
	public void setCasinoComps(List<CasinoComp> comps)
	{
		for (CasinoComp comp: comps)
		{
			addCasinoComp(comp);
		}
	}
	
	public void addCasinoComp(CasinoComp comp)
	{
		addCasinoCompInfo(comp.getCorpid(), comp.getType());
	}
	
	public void addCasinoCompInfo(String corpid, String type)
	{
		if(params == null) params = "";
		params += type + "=" + corpid + ";";
	}
	
	/**
	 * 获得博彩公司列表
	 * @return
	 */
	public List<CasinoComp> getCasinoComps()
	{
		if(StringUtils.isEmpty(params))
			return null;
		String[] strings = params.split(";");
		List<CasinoComp> comps = new ArrayList<>();
		for (String string : strings)
		{
			String[] str = string.split("=");
			if(str == null || str.length != 2)
				continue;
			CasinoComp comp = new CasinoComp();
			comp.setType(str[0]);
			comp.setCorpid(str[1]);
			comps.add(comp);
		}
		return comps;
	}
}
