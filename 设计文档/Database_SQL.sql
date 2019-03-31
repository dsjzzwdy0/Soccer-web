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

create table soccer_logo
(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	pid varchar(10),
	type varchar(10),
	mimetype varchar(15),
	images blob,
	url varchar(100),
	logotype varchar(10),
	PRIMARY KEY (`id`),
	INDEX `index_pid` (`pid`) USING BTREE
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
	INDEX `index_tid` (`tid`) USING BTREE ,
	INDEX `index_name` (`name`) USING BTREE
);

create table soccer_league_season_team(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	lid varchar(10),
	tid varchar(10),
	season varchar(20),
	PRIMARY KEY (`id`),
	INDEX `index_tid` (`tid`) USING BTREE ,
	INDEX `index_lid` (`lid`) USING BTREE
);

create table soccer_league_rank(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	lid varchar(10),
	tid varchar(10),
	type varchar(15),
	season varchar(20),
	round varchar(20),
	rank int,
	gamenum int,
	winnum int,
	drawnum int,
	losenum int,
	score int,
	wingoal int,
	losegoal int,
	ranktime timestamp,
	PRIMARY KEY (`id`),
	INDEX `index_tid` (`tid`) USING BTREE ,
	INDEX `index_lid` (`lid`) USING BTREE,
	INDEX `index_season` (`season`) USING BTREE,
	INDEX `index_round` (`round`) USING BTREE
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

create table soccer_match_jc(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10) NOT NULL,
	issue varchar(10),
	ordinary varchar(5),
	matchtime timestamp,
	closetime timestamp,
	winodds float,
	drawodds float,
	loseodds float,
	opened tinyint(1),
	danguan tinyint(1),
	rqnum int,
	rqopened tinyint(1),
	rqwinodds float,
	rqdrawodds float,
	rqloseodds float,
	isdelayed tinyint(1),
	delaytime timestamp,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
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

create table soccer_odds_yp(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	corpid varchar(10),
	corpname varchar(30),
	opentime long,
	winodds float,
	handicap float,
	loseodds float,
	winkelly float,
	losekelly float,
	winprob float,
	loseprob float,
	lossratio float,
	source varchar(10),
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_corpid` (`corpid`) USING BTREE,
	INDEX `index_soruce` (`source`) USING BTREE
);

create table soccer_odds_num(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	corpid varchar(10),
	corpname varchar(30),
	opentime long,
	winodds float,
	goalnum varchar(15),
	loseodds float,
	winkelly float,
	losekelly float,
	winprob float,
	loseprob float,
	lossratio float,
	source varchar(10),
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_corpid` (`corpid`) USING BTREE,
	INDEX `index_soruce` (`source`) USING BTREE
);

create table soccer_odds_score(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	ordinary varchar(10),
	type varchar(5),
	oddsvalue varchar(240),
	opentime timestamp,
	source varchar(10),
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_type` (`type`) USING BTREE
);

create table soccer_scheduler_info(
	`id`  varchar(50) NOT NULL,
	name varchar(30),
	maxactivetaskthread int,
	intervaltime int,
	randtimeseed int,
	type varchar(20),
	plugins text,
	PRIMARY KEY (`id`)
);

create table soccer_scheduler_status(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	sid varchar(50),
	name varchar(30),
	maxactivetaskthread int,
	intervaltime int,
	randtimeseed int,
	type varchar(20),
	plugins text,
	total int,
	leftsize int,
	state smallint,
	info text,
	createtime timestamp,
	stoptime timestamp,
	finishtime timestamp,
	PRIMARY KEY (`id`)
);

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_match_info`AS 
select `a`.`id` AS `id`,`a`.`mid` AS `mid`,`a`.`matchtime` AS `matchtime`,`a`.`lid` AS `lid`,`d`.`name` AS `leaguename`,`a`.`round` AS `round`,`a`.`season` AS `season`,
	`a`.`homeid` AS `homeid`,`b`.`name` AS `homename`,`a`.`clientid` AS `clientid`,`c`.`name` AS `clientname`,`e`.`result` AS `result`,`e`.`homegoal` AS `homegoal`,
	`e`.`clientgoal` AS `clientgoal` from ((((`soccer_match` `a` left join `soccer_league_team` `b` on((`a`.`homeid` = `b`.`tid`)))
	 left join `soccer_league_team` `c` on((`a`.`clientid` = `c`.`tid`))) left join `soccer_league` `d` on((`a`.`lid` = `d`.`lid`))) 
	left join `soccer_match_result` `e` on((`a`.`mid` = `e`.`mid`))) ;

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_match_jc_info` AS 
select `a`.`id` AS `id`,`a`.`mid` AS `mid`,`b`.`lid` AS `lid`,`b`.`leaguename` AS `leaguename`,`b`.`season` AS `season`,`b`.`round` AS `round`,`b`.`homeid` AS `homeid`,
	`b`.`homename` AS `homename`,`b`.`clientid` AS `clientid`,`b`.`clientname` AS `clientname`,`a`.`issue` AS `issue`,`a`.`ordinary` AS `ordinary`,
	`a`.`matchtime` AS `matchtime`,`a`.`closetime` AS `closetime`,`a`.`winodds` AS `winodds`,`a`.`drawodds` AS `drawodds`,`a`.`loseodds` AS `loseodds`,
	`a`.`opened` AS `opened`,`a`.`danguan` AS `danguan`,`a`.`rqnum` AS `rqnum`,`a`.`rqopened` AS `rqopened`,`a`.`rqwinodds` AS `rqwinodds`,`a`.`rqdrawodds` AS `rqdrawodds`,
	`a`.`rqloseodds` AS `rqloseodds`,`b`.`result` AS `result`,`b`.`homegoal` AS `homegoal`,`b`.`clientgoal` AS `clientgoal`,
	`a`.`isdelayed` AS `isdelayed`,`a`.`delaytime` AS `delaytime` from (`soccer_match_jc` `a` left join `soccer_match_info` `b` on((`a`.`mid` = `b`.`mid`))) ;

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_match_bd_info`AS 
select `a`.`id` AS `id`,`a`.`mid` AS `mid`,`b`.`lid` AS `lid`,`b`.`leaguename` AS `leaguename`,`b`.`season` AS `season`,`b`.`round` AS `round`,
	`b`.`homeid` AS `homeid`,`b`.`homename` AS `homename`,`b`.`clientid` AS `clientid`,`b`.`clientname` AS `clientname`,`a`.`bdno` AS `bdno`,
	`a`.`issue` AS `issue`,`a`.`ordinary` AS `ordinary`,`a`.`matchtime` AS `matchtime`,`a`.`closetime` AS `closetime`,`a`.`winodds` AS `winodds`,
	`a`.`drawodds` AS `drawodds`,`a`.`loseodds` AS `loseodds`,`a`.`rqnum` AS `rqnum`,`a`.`rqopened` AS `rqopened`,
	`b`.`result` AS `result`,`b`.`homegoal` AS `homegoal`,`b`.`clientgoal` AS `clientgoal`,`a`.`isdelayed` AS `isdelayed`,`a`.`delaytime` AS `delaytime`
	 from (`soccer_match_bd` `a` left join `soccer_match_info` `b` on((`a`.`mid` = `b`.`mid`))) ;

