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
package com.loris.soccer.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  League   
 * @Description: 图标数据
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("soccer_logo")
public class Logo extends AutoIdEntity
{
	public static enum LogoType{
		Team,
		League
	}
	
	/** */
	private static final long serialVersionUID = 1L;
	
	protected String pid;			//实体的类型
	protected String mimetype; 		//图像类型
	protected byte[] images;		//图像数据
	protected String url;
	protected LogoType logotype;
	
	public String getPid()
	{
		return pid;
	}
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	public byte[] getImages()
	{
		return images;
	}
	public void setImages(byte[] images)
	{
		this.images = images;
	}
	public String getMimetype()
	{
		return mimetype;
	}
	public void setMimetype(String mimetype)
	{
		this.mimetype = mimetype;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public LogoType getLogotype()
	{
		return logotype;
	}
	public void setLogotype(LogoType logotype)
	{
		this.logotype = logotype;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		Logo other = (Logo)obj;
		return StringUtils.equals(pid, other.pid)
				&& logotype == other.logotype;
	}
}
