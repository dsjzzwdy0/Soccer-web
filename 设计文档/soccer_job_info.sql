INSERT INTO `soccer_job_info` VALUES (1, '足彩网期号数据下载', 'com.loris.soccer.quartz.DataPluginJob', 'SOCCER_DATA_DOWNLOAD', '0 45 13 * * ?', 'issue.zgzcw', 1, '2019-5-2 12:11:51', NULL);
INSERT INTO `soccer_job_info` VALUES (2, '足彩网赔率数据下载', 'com.loris.soccer.quartz.DataPluginJob', 'SOCCER_DATA_DOWNLOAD', '0 45 11,23 * * ?', 'odds.zgzcw', 1, '2019-5-2 12:11:51', NULL);
INSERT INTO `soccer_job_info` VALUES (3, '足彩网联赛页面下载', 'com.loris.soccer.quartz.DataPluginJob', 'SOCCER_DATA_DOWNLOAD', '0 45 18 ? * MON,TUE,THU,FRI', 'league.zgzcw', 1, '2019-5-4 09:40:45', NULL);
INSERT INTO `soccer_job_info` VALUES (4, '足彩网比赛结果数据下载', 'com.loris.soccer.quartz.DataPluginJob', 'SOCCER_DATA_DOWNLOAD', '0 20 16 * * ?', 'result.zgzcw', 1, '2019-5-4 16:17:25', NULL);
