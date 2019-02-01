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
package com.loris.client.fetcher.setting;

import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**   
 * @ClassName:  FetcherSetting   
 * @Description: 配置内容管理类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SettingFactory
{
	public static final String DEFAULT_NAME = "default";
	
	/** */
	private Map<String, FetcherSetting> settings = new HashMap<String, FetcherSetting>();
	
	/**
	 * Create a single ClientSettings.
	 */
	public SettingFactory()
	{
		FetcherSetting setting = new FetcherSetting();
		setting.setConnectionTimeout(30000);
		setting.setBrowserVersion(BrowserVersion.CHROME);
		setting.setInterval(4000);
		
		settings.put(DEFAULT_NAME, setting);
		
		setting = new FetcherSetting();
		setting.setConnectionTimeout(30000);
		setting.setBrowserVersion(BrowserVersion.FIREFOX_60);
		setting.setInterval(4000);
		settings.put("setting2", setting);
	}
	
	public Map<String, FetcherSetting> getSettings()
	{
		return settings;
	}

	public void setSettings(Map<String, FetcherSetting> settings)
	{
		this.settings = settings;
	}

	/**
	 * 获得配置数据信息
	 * @param name 名称
	 * @return 配置内容
	 */
	public FetcherSetting getFetcherSetting(String name)
	{
		return settings.get(name);
	}
	
	/**
	 * 注册配置信息与内容
	 * @param name
	 * @param setting
	 */
	public void registFetcherSetting(String name, FetcherSetting setting)
	{
		settings.put(name, setting);
	}
	
	/**
	 * 获得默认的配置信息
	 * @return
	 */
	public FetcherSetting getDefaultFetcherSetting()
	{
		return getFetcherSetting(DEFAULT_NAME);
	}
}
