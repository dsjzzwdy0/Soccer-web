CREATE TABLE `soccer_web_page` (
	`id`  int(11) NOT NULL AUTO_INCREMENT ,
	`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`url`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`encoding`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`protocol`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`host`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`port`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`method`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`source`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`httpstatus`  int(11) NULL DEFAULT NULL ,
	`createtime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
	`loadtime`  timestamp NULL DEFAULT '0000-00-00 00:00:00' ,
	`ziptype`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	`completed`  tinyint(1) NULL DEFAULT NULL ,
	`plaintext`  tinyint(1) NULL DEFAULT NULL ,
	`paramstext`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
	PRIMARY KEY (`id`),
	INDEX `index_url` (`url`) USING BTREE ,
	INDEX `index_host` (`host`) USING BTREE ,
	INDEX `index_type` (`type`) USING BTREE ,
	INDEX `index_source` (`source`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1627
ROW_FORMAT=DYNAMIC
;


create table soccer_scheduler_status(
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

create table soccer_league_season(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	lid varchar(10),
	season varchar(20),
	PRIMARY KEY (`id`),
	INDEX `index_lid` (`lid`) USING BTREE ,
	INDEX `index_season` (`season`) USING BTREE
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

create table soccer_league_rank_latest(
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

create table soccer_bet_bd_odds(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10) NOT NULL,
	type varchar(5),
	opentime timestamp,
	winodds float,
	drawodds float,
	loseodds float,
	rqnum int,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_type` (`type`) USING BTREE
);

create table soccer_bet_jc_odds(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10) NOT NULL,
	type varchar(5),
	opentime timestamp NULL,
	winodds float,
	drawodds float,
	loseodds float,
	isopen tinyint(1),
	isdanguan tinyint(1),
	rqnum int,
	isrqopen tinyint(1),
	rqwinodds float,
	rqdrawodds float,
	rqloseodds float,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_type` (`type`) USING BTREE
);

create table soccer_match_issue(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10) NOT NULL,
	issue varchar(10),
	type varchar(5),
	issueno varchar(10),
	ordinary varchar(5),
	matchtime timestamp NULL,
	closetime timestamp NULL,
	isdelay tinyint(1),
	delaytime timestamp NULL,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE,
	INDEX `index_issue` (`issue`) USING BTREE,
	INDEX `index_type` (`type`) USING BTREE
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
	result varchar(6),
	homegoal smallint,
	clientgoal smallint,
	PRIMARY KEY (`id`),	
	INDEX `index_mid` (`mid`) USING BTREE
);

create table soccer_odds_op(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	corpid varchar(10),
	opentime timestamp,
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
	opentime timestamp,
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
	opentime timestamp,
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

create table soccer_record_odds_op(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	corpid varchar(10),
	firsttime timestamp,
	firstwinodds float,
	firstdrawodds float,
	firstloseodds float,
	opentime timestamp,
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

create table soccer_record_odds_yp(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	mid varchar(10),
	corpid varchar(10),
	firsttime timestamp,
	firstwinodds float,
	firsthandicap float,
	firstloseodds float,
	opentime timestamp,
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
	`id`  int(11) NOT NULL AUTO_INCREMENT,
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

create table soccer_job_info(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	jobname varchar(20),
	classname varchar(100),
	groupname varchar(20),
	cronexpression varchar(200),
	data varchar(100),
	enabled tinyint(1) NULL,
	createtime timestamp NULL,
	modifytime timestamp NULL,
	PRIMARY KEY (`id`),
	INDEX `index_jobname` (`jobname`) USING BTREE
);

create table soccer_stat_corp_freq(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	corpid varchar(20),
	name varchar(50),
	keyvalue varchar(30),
	type varchar(10),
	total int,
	freq int,
	params varchar(100),
	PRIMARY KEY (`id`),
	INDEX `index_corpid` (`corpid`) USING BTREE
);

create table soccer_comp_casino(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	corpid varchar(20),
	name varchar(50),
	ismain tinyint(1),
	type varchar(15),
	source varchar(20),
	PRIMARY KEY (`id`),
	INDEX `index_corpid` (`corpid`) USING BTREE
);

create table soccer_comp_setting(
	`id`  int(11) NOT NULL AUTO_INCREMENT,
	sid varchar(50),
	name varchar(50),
	user varchar(30),
	createtime timestamp NULL,
	modifytime timestamp NULL,
	source varchar(20),
	params text,
	PRIMARY KEY (`id`),
	INDEX `index_sid` (`sid`) USING BTREE
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

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_league_round_info`AS 
select `a`.`id` AS `id`,`a`.`lid` AS `lid`,`c`.`type` AS `leaguetype`,`c`.`name` AS `name`,`a`.`season` AS `season`,`a`.`round` AS `round`,
  `b`.`starttime` AS `starttime`,`b`.`endtime` AS `endtime`,`b`.`matchnum` AS `matchnum` from ((`soccer`.`soccer_league_round` `a` 
  left join (select `soccer`.`soccer_match`.`lid` AS `lid`,`soccer`.`soccer_match`.`season` AS `season`,`soccer`.`soccer_match`.`round` AS `round`,
  min(`soccer`.`soccer_match`.`matchtime`) AS `starttime`,max(`soccer`.`soccer_match`.`matchtime`) AS `endtime`,count(1) AS `matchnum` 
  from `soccer`.`soccer_match` group by `soccer`.`soccer_match`.`lid`,`soccer`.`soccer_match`.`round`) `b` on(((`a`.`lid` = `b`.`lid`) 
  and (`a`.`season` = `b`.`season`) and (`a`.`round` = `b`.`round`)))) 
  left join `soccer`.`soccer_league` `c` on((`a`.`lid` = `c`.`lid`)))


CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_league_season_info`AS 
select `a`.`id` AS `id`,`a`.`lid` AS `lid`,`a`.`season` AS `season`,`b`.`name` AS `name`,`b`.`type` AS `leaguetype` from (`soccer_league_season` `a` 
	left join `soccer_league` `b` on((`a`.`lid` = `b`.`lid`))) ;

CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_match_issue_info`AS 
select `a`.`id` AS `id`,`b`.`lid` AS `lid`,`b`.`leaguename` AS `leaguename`,`b`.`season` AS `season`,`b`.`round` AS `round`,`a`.`mid` AS `mid`,
	`a`.`issue` AS `issue`,`a`.`issueno` AS `issueno`,`a`.`ordinary` AS `ordinary`,`a`.`type` AS `type`,`b`.`homeid` AS `homeid`,
	`b`.`homename` AS `homename`,`b`.`clientid` AS `clientid`,`b`.`clientname` AS `clientname`,`b`.`matchtime` AS `matchtime`,
	`a`.`closetime` AS `closetime`,`a`.`isdelay` AS `isdelay`,`a`.`delaytime` AS `delaytime` from (`soccer_match_issue` `a` left join `soccer_match_info` `b` on((`a`.`mid` = `b`.`mid`))) ;


CREATE 
ALGORITHM=UNDEFINED 
DEFINER=`root`@`localhost` 
SQL SECURITY DEFINER 
VIEW `soccer_league_rank_info`AS 
select `b`.`id` AS `id`,`b`.`lid` AS `lid`,`c`.`name` AS `leaguename`,`a`.`tid` AS `tid`,`d`.`name` AS `teamname`,`a`.`type` AS `type`,`a`.`maxseason` AS `season`,
	`a`.`maxround` AS `round`,`b`.`gamenum` AS `gamenum`,`b`.`rank` AS `rank`,`b`.`winnum` AS `winnum`,`b`.`drawnum` AS `drawnum`,`b`.`losenum` AS `losenum`,
	`b`.`score` AS `score`,`b`.`wingoal` AS `wingoal`,`b`.`losegoal` AS `losegoal`,`b`.`ranktime` AS `ranktime` from 
	(((((select `soccer`.`soccer_league_rank`.`tid` AS `tid`,`soccer`.`soccer_league_rank`.`type` AS `type`,max(`soccer`.`soccer_league_rank`.`season`) AS `maxseason`,
	max(`soccer`.`soccer_league_rank`.`round`+0) AS `maxround` from `soccer`.`soccer_league_rank` group by `soccer`.`soccer_league_rank`.`tid`,`soccer`.`soccer_league_rank`.`type`)) 
	`a` join `soccer`.`soccer_league_rank` `b` on(((`a`.`tid` = `b`.`tid`) and (`a`.`type` = `b`.`type`) and (`a`.`maxseason` = `b`.`season`) and (`a`.`maxround` = `b`.`round`)))) 
	left join `soccer`.`soccer_league` `c` on((`b`.`lid` = `c`.`lid`))) left join `soccer`.`soccer_league_team` `d` on((`a`.`tid` = `d`.`tid`))) ;

DELIMITER $$
CREATE TRIGGER soccer_league_rank_add_trigger AFTER INSERT ON soccer_league_rank FOR EACH ROW 
BEGIN
	SET @count = (SELECT COUNT(*) FROM soccer_league_rank_latest WHERE soccer_league_rank_latest.`tid`=new.tid AND soccer_league_rank_latest.`type`=new.type);
	IF  @count = 0 THEN
		INSERT INTO soccer_league_rank_latest(lid, tid, type, season, round, rank, gamenum, winnum, drawnum, losenum, score, wingoal, losegoal) VALUES 
			(new.lid, new.tid, new.type, new.season, new.round, new.rank, new.gamenum, new.winnum, new.drawnum, new.losenum, new.score, new.wingoal,  new.losegoal);
	ELSE
		UPDATE soccer_league_rank_latest SET 
			season=new.season, round=new.round, rank=new.rank, gamenum=new.gamenum, winnum=new.winnum, drawnum=new.drawnum, losenum=new.losenum, score=new.score, 
			wingoal=new.wingoal, losegoal=new.losegoal
     WHERE tid=new.tid AND type=new.type AND (season < new.season or (season=new.season and round+0 < new.round+0));
	END IF;
END $$
DELIMITER ;