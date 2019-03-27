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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.loris.client.task.context.TaskPluginContext;
import com.loris.common.util.DateUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.filter.MatchOddsFilter;
import com.loris.soccer.data.filter.ZgzcwWebPageFilter;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;

/**   
 * @ClassName: ZgzcwOddsDataPlugin   
 * @Description: 中国足彩网赔率数据更新与下载 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class ZgzcwOddsDataPlugin extends ZgzcwBasePlugin
{
	/**
	 * Create a new instance of ZgzcwOddsDataPlugin
	 * @param name
	 */
	public ZgzcwOddsDataPlugin()
	{
		super("赔率数据更新");
		webPageConf = WebPageProperties.getDefault();
	}
	
	/**
	 * 初始化任务产生器
	 * 
	 * @param context 插件任务运行环境
	 * @throws IOException 在任务产生过程中出现异常
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		super.initialize(context);
		
		List<String> types = new ArrayList<>();
		types.add(ZgzcwConstants.PAGE_ODDS_OP);
		types.add(ZgzcwConstants.PAGE_ODDS_YP);
		types.add(ZgzcwConstants.PAGE_ODDS_NUM);
		ZgzcwWebPageFilter filter = new ZgzcwWebPageFilter(webPageConf);
		filter.setSource(ZgzcwConstants.SOURCE_ZGZCW);
		filter.setStart(DateUtil.getFirstDateBefore(new Date(), - webPageConf.getDayNumOfGetPages()));
		filter.setPageTypes(types);	
		webPageFilter = filter;
		webPageFilter.initialize();
		
		registFilter(SoccerConstants.SOCCER_DATA_MATCH, new MatchOddsFilter(webPageConf.getNumDayOfHasOdds(), 
				webPageConf.getDayNumOfGetPages()));
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
