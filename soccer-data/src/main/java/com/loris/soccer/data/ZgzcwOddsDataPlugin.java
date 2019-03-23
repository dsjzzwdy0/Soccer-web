/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ZgzcwOddsDataPlugin.java   
 * @Package com.loris.soccer.data   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data;

import java.io.IOException;
import java.sql.SQLException;

import com.loris.client.task.context.TaskPluginContext;

/**   
 * @ClassName: ZgzcwOddsDataPlugin   
 * @Description: 中国足彩网赔率数据更新与下载 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwOddsDataPlugin extends ZgzcwBasePlugin
{
	/**
	 * @param name
	 */
	public ZgzcwOddsDataPlugin()
	{
		super("赔率数据下载");
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.ZgzcwBasePlugin#produce(com.loris.client.task.context.TaskPluginContext)
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
	}

}
