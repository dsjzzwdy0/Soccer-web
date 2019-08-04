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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loris.common.bean.AutoIdEntity;
import com.loris.soccer.constant.SoccerConstants;

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
	
	@TableField(exist=false)
	protected List<CasinoComp> casinoComps = new ArrayList<>();
	
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
		setCasinoCompsFromParams(params);
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
	
	/**
	 * 更新博彩公司信息
	 * @param comp
	 */
	public void updateCasinoComp(CasinoComp comp)
	{
		for (CasinoComp casinoComp : casinoComps)
		{
			if(casinoComp.equals(comp))
			{
				casinoComp.setId(comp.getId());
				casinoComp.setCorpid(comp.getCorpid());
				casinoComp.setName(comp.getName());
				casinoComp.setSource(comp.getSource());
				casinoComp.setType(comp.getType());
				casinoComp.setIsmain(comp.isIsmain());
			}
		}
	}
	
	public void addCasinoComp(CasinoComp comp)
	{
		addCasinoCompInfo(comp.getCorpid(), comp.getType(), comp.getSource());
	}
	
	public void addCasinoCompInfo(String corpid, String type, String source)
	{
		if(params == null) params = "";
		params += type + "=" + corpid + ",source=" + source + ";";
	}
	
	/**
	 * 获得博彩公司列表
	 * @return
	 */
	public List<CasinoComp> getCasinoComps()
	{
		if(casinoComps.size() == 0)
		{
			setCasinoCompsFromParams(params);
		}		
		return casinoComps;
	}
	
	/**
	 * 从参数中获取博彩公司的数据
	 * @param params
	 */
	private void setCasinoCompsFromParams(String params)
	{
		if(StringUtils.isEmpty(params))
		{
			return;
		}
		
		String[] strings = params.split(";");
		for (String string : strings)
		{
			String[] str = string.split(",");
			int len = str.length;
			
			//按照每一个字段域给赋值
			CasinoComp comp = new CasinoComp();
			for(int i = 0; i < len; i ++)
			{
				String[] fields = str[i].split("=");
				if(fields == null || fields.length < 2)
					continue;
				fields[0] = fields[0].trim();
				fields[1] = fields[1].trim();
				if(StringUtils.equalsAnyIgnoreCase(fields[0], SoccerConstants.ODDS_TYPE_OP) ||
						StringUtils.equalsAnyIgnoreCase(fields[0], SoccerConstants.ODDS_TYPE_YP))
				{
					comp.setType(fields[0].toLowerCase());
					comp.setCorpid(fields[1]);
				}
				else if(StringUtils.equalsAnyIgnoreCase(fields[0], "source"))
				{
					comp.setSource(fields[1]);
				}
			}
			//如果都为空，则不加入
			if(StringUtils.isEmpty(comp.getType()) || StringUtils.isEmpty(comp.getCorpid()))
			{
				continue;
			}
			//默认的数据来源
			if(StringUtils.isEmpty(comp.getSource()))
			{
				comp.setSource(SoccerConstants.SOURCE_ZGZCW);
			}
			casinoComps.add(comp);
		}
	}
	
	/**
	 * 获得欧赔公司列表
	 * @return 欧赔公司
	 */
	@JsonIgnore
	public List<CasinoComp> getOpComps()
	{
		return getComps(SoccerConstants.ODDS_TYPE_OP);
	}
	
	/**
	 * 获得欧赔公司ID数据的列表
	 * @return
	 */
	 @JsonIgnore
	public List<String> getOpCorpIds()
	{
		return getCorpIds(SoccerConstants.ODDS_TYPE_OP);
	}
	 
	/**
	 * 获得来盘公司的数据列表
	 * @return
	 */
	@JsonIgnore
	public List<CasinoComp> getYpComps()
	{
		return getComps(SoccerConstants.ODDS_TYPE_YP);
	}
	 
	/**
	 * 获得亚盘公司ID数据的列表
	 * @return
	 */
    @JsonIgnore
	public List<String> getYpCorpIds()
	{
		return getCorpIds(SoccerConstants.ODDS_TYPE_YP);
	}
	
    /**
     * 按照类型对数据类型集合处理
     * @param type
     * @return
     */
    @JsonIgnore
	public List<String> getCorpIds(String type)
	{
		List<String> ids = new ArrayList<>();
		for (CasinoComp casinoComp : casinoComps)
		{
			if(StringUtils.isEmpty(type) || StringUtils.equals(type, casinoComp.getType()))
			{
				if(!ids.contains(casinoComp.getCorpid()))
					ids.add(casinoComp.getCorpid());
			}
		}
		return ids;
	}
    
    /**
     * 按照类型对公司的数据进行过滤
     * @param type
     * @return
     */
    @JsonIgnore
    public List<CasinoComp> getComps(String type)
    {
    	List<CasinoComp> comps = new ArrayList<>();
    	for (CasinoComp casinoComp : casinoComps)
		{
    		if(StringUtils.isEmpty(type) || StringUtils.equals(type, casinoComp.getType()))
    		{
    			if(!comps.contains(casinoComp))
    				comps.add(casinoComp);
    		}
		}
    	return comps;
    }
}
