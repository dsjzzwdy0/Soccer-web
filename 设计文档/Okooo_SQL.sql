CREATE TABLE `okooo_league` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`lid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`country`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`continent`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`introduction`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`),
INDEX `index_lid` (`lid`) USING BTREE ,
INDEX `index_name` (`name`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=515
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `okooo_league_team` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`tid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`alias`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`country`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_lid` (`tid`) USING BTREE ,
INDEX `index_name` (`name`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=6730
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `okooo_issue_match` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`mid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`issue`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`issueno`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ordinary`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`matchtime`  timestamp NULL DEFAULT NULL ,
`closetime`  timestamp NULL DEFAULT NULL ,
`isdelay`  tinyint(1) NULL DEFAULT NULL ,
`delaytime`  timestamp NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_mid` (`mid`) USING BTREE ,
INDEX `index_issue` (`issue`) USING BTREE ,
INDEX `index_type` (`type`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `okooo_match` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`mid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`matchtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`lid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`round`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`season`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`homeid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`clientid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_mid` (`mid`) USING BTREE ,
INDEX `index_lid` (`lid`) USING BTREE ,
INDEX `index_season` (`season`) USING BTREE ,
INDEX `index_round` (`round`) USING BTREE ,
INDEX `index_homeid` (`homeid`) USING BTREE ,
INDEX `index_clientid` (`clientid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=38648
ROW_FORMAT=DYNAMIC
;


CREATE TABLE `okooo_odds_op` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`mid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`corpid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`opentime`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`winodds`  float NULL DEFAULT NULL ,
`drawodds`  float NULL DEFAULT NULL ,
`loseodds`  float NULL DEFAULT NULL ,
`winkelly`  float NULL DEFAULT NULL ,
`drawkelly`  float NULL DEFAULT NULL ,
`losekelly`  float NULL DEFAULT NULL ,
`winprob`  float NULL DEFAULT NULL ,
`drawprob`  float NULL DEFAULT NULL ,
`loseprob`  float NULL DEFAULT NULL ,
`lossratio`  float NULL DEFAULT NULL ,
`source`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_mid` (`mid`) USING BTREE ,
INDEX `index_corpid` (`corpid`) USING BTREE ,
INDEX `index_soruce` (`source`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5956438
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `okooo_odds_yp` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`mid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`corpid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`opentime`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`winodds`  float NULL DEFAULT NULL ,
`handicap`  float NULL DEFAULT NULL ,
`loseodds`  float NULL DEFAULT NULL ,
`winkelly`  float NULL DEFAULT NULL ,
`losekelly`  float NULL DEFAULT NULL ,
`winprob`  float NULL DEFAULT NULL ,
`loseprob`  float NULL DEFAULT NULL ,
`lossratio`  float NULL DEFAULT NULL ,
`source`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_mid` (`mid`) USING BTREE ,
INDEX `index_corpid` (`corpid`) USING BTREE ,
INDEX `index_soruce` (`source`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1553714
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `okooo_casino_comp` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`corpid`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ismain`  tinyint(1) NULL DEFAULT NULL ,
`type`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_corpid` (`corpid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=280
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `soccer_mapping_league`(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`sourceid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcename`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcefrom`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`destid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`destname`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcedest`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_sourcelid` (`sourceid`) USING BTREE ,
INDEX `index_source` (`sourcefrom`) USING BTREE ,
INDEX `index_destlid` (`destid`) USING BTREE ,
INDEX `index_dest` (`sourcedest`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `soccer_mapping_match` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`sourceid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcefrom`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`destid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcedest`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`matchtime`  timestamp NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_sourceid` (`sourceid`) USING BTREE,
INDEX `index_source` (`sourcefrom`) USING BTREE, 
INDEX `index_destid` (`destid`) USING BTREE,
INDEX `index_dest` (`sourcedest`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=280
ROW_FORMAT=DYNAMIC
;

CREATE TABLE `soccer_mapping_team` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`sourceid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcename`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcefrom`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`destid`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`destname`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sourcedest`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `index_sourcelid` (`sourceid`) USING BTREE ,
INDEX `index_source` (`sourcefrom`) USING BTREE ,
INDEX `index_destlid` (`destid`) USING BTREE ,
INDEX `index_dest` (`sourcedest`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

