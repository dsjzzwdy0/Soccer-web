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
package com.loris.soccer.data.zgzcw.producer;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.loris.client.task.plugin.BasicTaskProducePlugin;
import com.loris.soccer.processor.HttpTaskProcessor;

/**   
 * @ClassName:  League   
 * @Description: 今日足彩数据下载产生器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class ZgzcwIssueProducePlugin extends BasicTaskProducePlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwIssueProducePlugin.class);
		
	@Autowired
	HttpTaskProcessor httpCommonPlugin;
	
	@Override
	public void initialize()throws IOException
	{
		logger.info(httpCommonPlugin.getName());
	}
}
