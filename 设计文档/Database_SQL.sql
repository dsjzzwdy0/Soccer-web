create table soccer_web_page(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	url varchar(100),
	encoding varchar(10),
	protocol varchar(5),
	host varchar(15),
	port varchar(5),
	method varchar(4),
	type varchar(10),
	source varchar(10),
	httpstatus integer,
	createtime timestamp,
	loadtime timestamp,
	ziptype varchar(5),
	completed tinyint(1),
	plaintext tinyint(1),
	PRIMARY KEY (`id`),
	INDEX `index_url` (`url`) USING BTREE ,
	INDEX `index_host` (`host`) USING BTREE ,
	INDEX `index_type` (`type`) USING BTREE ,
	INDEX `index_source` (`source`) USING BTREE 
);

create table soccer_scheduler_info(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	sid varchar(20),
	name varchar(30),
	maxActiveTaskThread int(11),
	`intervaltime` int(11),
	randTimeSeed int(11),
	type varchar(20),
	total int(11),
	leftsize int(11),
	state smallint,
	createtime timestamp,
	finishtime timestamp,
	PRIMARY KEY (`id`)
);

create table soccer_league(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	lid varchar(10),
	name varchar(30),
	type varchar(10),
	country varchar(30),
	continent varchar(10),
	introduction text,
	PRIMARY KEY (`id`),
	INDEX `index_lid` (`lid`) USING BTREE ,
	INDEX `index_name` (`name`) USING BTREE
);

create table soccer_league_round(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	lid varchar(10),
	season varchar(20),
	round varchar(20),
	PRIMARY KEY (`id`),
	INDEX `index_lid` (`lid`) USING BTREE ,
	INDEX `index_season` (`season`) USING BTREE
);

create table soccer_league_team(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	tid varchar(10),
	name varchar(30),
	country varchar(20),
	PRIMARY KEY (`id`),
	INDEX `index_lid` (`tid`) USING BTREE ,
	INDEX `index_name` (`name`) USING BTREE
);

create table soccer_match_bd(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10) NOT NULL,
	bdno varchar(10),
	issue varchar(10),
	ordinary varchar(5),
	matchtime timestamp,
	closetime timestamp,
	winodds float,
	drawodds float,
	loseodds float,
	rqnum int,
	rqopened tinyint(1),
	isdelayed tinyint(1),
	delaytime timestamp,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_bdno` (`bdno`) USING BTREE,
	INDEX `index_issue` (`issue`) USING BTREE
);


create table soccer_match(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10) NOT NULL,
	matchtime timestamp,
	lid varchar(10),
	round varchar(10),
	season varchar(15),
	homeid varchar(10),
	clientid varchar(10),
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_lid` (`lid`) USING BTREE,
	INDEX `index_season` (`season`) USING BTREE,
	INDEX `index_round` (`round`) USING BTREE,
	INDEX `index_homeid` (`homeid`) USING BTREE,
	INDEX `index_clientid` (`clientid`) USING BTREE
);

create table soccer_match_result
(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	result smallint,
	homegoal smallint,
	clientgoal smallint,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE
);

create table soccer_odds_op(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	corpid varchar(10),
	corpname varchar(30),
	opentime long,
	winodds float,
	drawodds float,
	loseodds float,
	winkelly float,
	drawkelly float,
	losekelly float,
	winprob float,
	drawprob float,
	loseprob float,
	lossratio float,
	source varchar(10),
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_corpid` (`corpid`) USING BTREE,
	INDEX `index_soruce` (`source`) USING BTREE
);

CREATE
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `sys_menu_node`AS 
select `m`.`id` AS `id`,`m`.`url` AS `url`,`rel`.`roleid` AS `roleid` from (`sys_relation` `rel` join `sys_menu` `m` on((`rel`.`menuid` = `m`.`id`))) ;

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `sys_menu_tree`AS 
select `m1`.`id` AS `id`,(case when ((`m2`.`id` = 0) or isnull(`m2`.`id`)) then 0 else `m2`.`id` end) AS `pId`,`m1`.`name` AS `NAME`,(case when ((`m2`.`id` = 0) or isnull(`m2`.`id`)) then 'true' else 'false' end) AS `isOpen` from (`sys_menu` `m1` left join `sys_menu` `m2` on((`m1`.`pcode` = `m2`.`code`))) order by `m1`.`id` ;

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `sys_role_res`AS 
select `m1`.`id` AS `id`,`m1`.`icon` AS `icon`,`m3`.`roleid` AS `roleid`,(case when ((`m2`.`id` = 0) or isnull(`m2`.`id`)) then 0 else `m2`.`id` end) AS `parentId`,`m1`.`name` AS `name`,`m1`.`url` AS `url`,`m1`.`levels` AS `levels`,`m1`.`ismenu` AS `ismenu`,`m1`.`num` AS `num` from ((`soccer`.`sys_menu` `m1` left join `soccer`.`sys_menu` `m2` on((`m1`.`pcode` = `m2`.`code`))) join (select `soccer`.`sys_menu`.`id` AS `ID`,`rela`.`roleid` AS `roleid` from (`soccer`.`sys_menu` left join `soccer`.`sys_relation` `rela` on((`soccer`.`sys_menu`.`id` = `rela`.`menuid`)))) `m3` on((`m1`.`id` = `m3`.`ID`))) where (`m1`.`ismenu` = 1) order by `m1`.`levels`,`m1`.`num` ;

