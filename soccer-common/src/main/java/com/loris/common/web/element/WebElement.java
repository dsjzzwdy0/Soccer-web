/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebElement.java   
 * @Package com.loris.common.web.element   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.web.element;

/**   
 * @ClassName: WebElement   
 * @Description: 网络上的HTML元素定义，用于自定义的配置 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebElement
{
	public final static String WEB_ELEMENT_INPUT = "input";
	public final static String WEB_ELEMENT_SELECT = "select";
	public final static String WEB_ELEMENT_DATE = "date";
	public final static String WEB_ELEMENT_CHECKBOX = "checkbox";
	public final static String WEB_ELEMENT_RADIO = "radio";
	
	private String id;
	private String name;
	private String type;
	private Object value;
	private Object options;
	
	/**
	 * 
	 */
	public WebElement()
	{
	}
	
	public WebElement(String id, String name, String type, Object value, Object options)
	{
		this.id = id;
		this.name =name;
		this.type = type;
		this.value = value;
		this.options = options;
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
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Object getValue()
	{
		return value;
	}
	public void setValue(Object value)
	{
		this.value = value;
	}
	public Object getOptions()
	{
		return options;
	}
	public void setOptions(Object options)
	{
		this.options = options;
	}
}
