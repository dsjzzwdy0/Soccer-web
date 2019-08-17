/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @ZgzcwHistoryMatchOddsPlugin.java   
 * @Package com.loris.soccer.datacom.loris.soccer.model   
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
import com.loris.soccer.data.conf.WebPageProperties;

/**   
 * @ClassName:  ZgzcwHistoryMatchOddsPlugin.java   
 * @Description: 历史比赛数据的下载管理器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwHistoryMatchOddsPlugin extends ZgzcwBasePlugin
{
	/**
	 * Create a new instance of ZgzcwHistoryMatchOddsPlugin
	 * @param name
	 * @param webPageConf
	 */
	public ZgzcwHistoryMatchOddsPlugin(String name, WebPageProperties webPageConf)
	{
		super(name, webPageConf);
	}
	
	/**
	 * 
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
	}
}
