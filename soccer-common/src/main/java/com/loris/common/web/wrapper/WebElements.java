/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebConfiguration.java   
 * @Package com.loris.common.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.web.wrapper;

import java.util.ArrayList;

import com.loris.common.web.element.WebElement;

/**   
 * @ClassName: WebElements 
 * @Description: 网络配置信息
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebElements extends ArrayList<WebElement>
{
	/** */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 添加WebElement元素数据
	 * @param id
	 * @param name
	 * @param type
	 * @param value
	 * @param options
	 */
	public void addWebElement(String id, String name, String type, boolean visible, Object value, Object options)
	{
		this.add(new WebElement(id, name, type, visible, value, options));
	}
}
