-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+08:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `a_description` varchar(255) NOT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (6,'春季电影节','活动期间购买部分电影可得一张优惠券',10,'2019-06-15 00:00:00','2019-06-28 00:00:00'),(11,'品质联盟','活动期间购买夏目友人帐可得一张优惠券',19,'2019-06-15 00:00:00','2019-06-28 00:00:00'),(16,'回馈活动','活动期间购买所有电影都可得一张优惠券',28,'2019-06-15 00:00:00','2019-06-28 00:00:00');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_movie`
--

DROP TABLE IF EXISTS `activity_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_movie` (
  `activity_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_movie`
--

LOCK TABLES `activity_movie` WRITE;
/*!40000 ALTER TABLE `activity_movie` DISABLE KEYS */;
INSERT INTO `activity_movie` VALUES (6,10),(6,11),(6,16),(11,10);
/*!40000 ALTER TABLE `activity_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (10,'春季电影节活动赠送','春季电影节优惠券',20,5,'2019-06-15 00:00:00','2019-06-28 00:00:00'),(19,'品质联盟活动赠送','品质联盟优惠券',30,8,'2019-06-15 00:00:00','2019-06-28 00:00:00'),(28,'影院回馈老客户','回馈优惠券',50,10,'2019-06-15 00:00:00','2019-06-28 00:00:00');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user`
--

DROP TABLE IF EXISTS `coupon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_user` (
	`coupon_id` int(11) NOT NULL,
  	`user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user`
--

LOCK TABLES `coupon_user` WRITE;
/*!40000 ALTER TABLE `coupon_user` DISABLE KEYS */;
INSERT INTO `coupon_user` VALUES (10,15),(10,15),(10,15),(19,15),(19,15),(28,15),(28,15);
/*!40000 ALTER TABLE `coupon_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hall`
--

DROP TABLE IF EXISTS `hall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `column` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'1号厅',10,5),(2,'2号厅',12,8);
/*!40000 ALTER TABLE `hall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `screen_writer` varchar(255) DEFAULT NULL,
  `starring` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES
(10,'http://n.sinaimg.cn/translate/640/w600h840/20190312/ampL-hufnxfm4278816.jpg','大森贵弘 /伊藤秀樹','','神谷浩史 /井上和彦 /高良健吾 /小林沙苗 /泽城美雪','动画',NULL,NULL,120,'2019-04-14 14:54:31','夏目友人帐','在人与妖怪之间过着忙碌日子的夏目，偶然与以前的同学结城重逢，由此回忆起了被妖怪缠身的苦涩记忆。此时，夏目认识了在归还名字的妖怪记忆中出现的女性·津村容莉枝。和玲子相识的她，现在和独子椋雄一同过着平稳的生活。夏目通过与他们的交流，心境也变得平和。但这对母子居住的城镇，却似乎潜伏着神秘的妖怪。在调查此事归来后，寄生于猫咪老师身体的“妖之种”，在藤原家的庭院中，一夜之间就长成树结出果实。而吃掉了与自己形状相似果实的猫咪老师，竟然分裂成了3个',0),
(11,'http://img5.mtime.cn/pi/2019/01/25/100539.36032012_1000X1000.jpg','安娜·波顿',NULL,'布利·拉尔森','动作/冒险/科幻',NULL,NULL,120,'2019-04-16 14:55:31','惊奇队长','漫画中的初代惊奇女士曾经是一名美国空军均情报局探员，暗恋惊奇先生。。。',0),
(12,'http://img31.mtime.cn/pi/2014/02/27/185241.96916925_1000X1000.jpg','彭浩翔','麦曦茵 /彭浩翔','杨千嬅 /余文乐','爱情','香港','粤语',104,'2019-04-16 14:57:31','志明与春娇','自2007年1月1日开始，香港实施室内全面禁烟，烟民都被迫从办公室走至后巷抽烟。自此，来自不同背景职业的抽烟族，在后巷开辟了一个打诨聊天、结交朋友的特殊场所，其中一对男女──张志明与余春娇，亦因抽烟而在肮脏又狭窄的后巷中..',0),
(13,'http://img31.mtime.cn/pi/2014/03/07/080410.67492765_1000X1000.jpg','宫崎骏','宫崎骏 /唐纳德·休伊特','岛本须美','动画 /家庭','日本','日语',86,'2019-04-16 14:52:31','龙猫','小月的母亲生病住院了，父亲带着她与四岁的妹妹小梅到乡间的居住 。她们对那里的环境都感到十分新奇，也发现了很多有趣的事情。她们遇 到了很多小精灵，她们来到属于她们的环境中，看到了她们世界中很多的 奇怪事物，更与一只大大胖胖的龙猫成为了朋友。龙猫与小精灵们利用他 们的神奇力量，为小月与妹妹带来了很多神奇的景观，令她们大开眼界。 妹妹小梅常常挂念生病中的母亲，嚷着要姐姐带着她去看母亲，但小月拒绝了。小梅竟然自己前往，不料途中迷路了，小月只好寻找她的龙猫及小精灵朋友们帮助。',0),
(14,'http://img31.mtime.cn/pi/2014/03/01/154400.60153419_1000X1000.jpg','周杰伦','杜致朗 /周杰伦','周杰伦','爱情','中国台湾','中文',101,'2019-04-18 13:23:15','不能说的秘密','高中生叶湘伦(周杰伦饰)出身单亲家庭，并且在父亲(黄秋生饰)任教的学校就读。而在父亲的耳濡目染下，他热爱音乐并且琴艺过人。某日，班上来了一位同样喜爱弹琴的新同学路小雨(桂纶美饰)，投缘的两人形影不离，情感也日渐加温，然而小雨总是相当神秘，还常弹奏一首未曾问世，但优美动听的曲子。',1),
(15,'http://img5.mtime.cn/pi/2019/03/29/095506.37108934_1000X1000.jpg','安东尼·罗素 /乔·罗素','克里斯托弗·马库斯 /斯蒂芬·麦克菲利','托尼·斯塔克','动作','美国','英语',111,'2019-04-16 15:00:24','复仇者联盟4','《复仇者联盟3：无限战争》的毁灭性事件过后，宇宙由于疯狂泰坦灭霸的行动而变得满目疮痍。无论前方将遭遇怎样的后果，复仇者联盟都必须在剩余盟友的帮助下再一次集结，以逆转灭霸的所作所为，彻底恢复宇宙的秩序。',0),
(16,'http://img5.mtime.cn/pi/2019/02/21/114527.94394192_1000X1000.jpg','林孝谦','abcˆ','陈意涵','爱情','大陆',NULL,123,'2019-04-18 13:23:15','比悲伤更悲伤的故事','唱片制作人张哲凯(刘以豪)和王牌作词人宋媛媛(陈意涵)相依为命，两人自幼身世坎坷只有彼此为伴，他们是亲人、是朋友，也彷佛是命中注定的另一半。父亲罹患遗传重症而被母亲抛弃的哲凯，深怕自己随时会发病不久人世，始终没有跨出友谊的界线对媛媛展露爱意。眼见哲凯的病情加重，他暗自决定用剩余的生命完成他们之间的终曲，再为媛媛找个可以托付一生的好男人。这时，事业有 成温柔体贴的医生(张书豪)适时的出现让他成为照顾媛媛的最佳人选，二人按部就班发展着关系。一切看似都在哲凯的计划下进行。然而，故事远比这里所写更要悲伤......',1),
(19,'http://img5.mtime.cn/pi/2019/03/20/135422.70751443_1000X1000.jpg','乔什·库雷','安德鲁·斯坦顿 /史黛芬妮·福尔松','汤姆·汉克斯','动画','美国','英语',90,'2019-06-21 00:00:00','玩具总动员4','当邦妮将所有玩具带上房车家庭旅行时，胡迪与伙伴们将共同踏上全新的冒险之旅，领略房间外面的世界有多广阔，甚至偶遇老朋友牧羊女。在多年的独自闯荡中，牧羊女已经变得热爱冒险，不再只是一个精致的洋娃娃。正当胡迪和牧羊女发现彼此对玩具的使命的意义大相径庭时，他们很快意识到更大的威胁即将到来。',0),
(20,'http://img5.mtime.cn/pi/2019/04/23/181428.76045477_1000X1000.jpg','西蒙·金伯格','西蒙·金伯格 /约翰·拜恩','索菲·特纳','动作 /冒险 /科幻','美国','英语',114,'2019-06-06 00:00:00','X战警','影片剧情围绕X战警中最受欢迎成员之一的琴·葛蕾展开，讲述她逐渐转化为黑凤凰的故事。在一次危及生命的太空营救行动中，琴被神秘的宇宙力量击中，成为最强大的变种人。此后琴·葛蕾不仅要设法掌控日益增长、极不稳定的力量，更要与自己内心的恶魔抗争，她的失控让整个X战警大家庭分崩离析，也让整个星球陷入毁灭的威胁之中。',0),
(21,'http://img5.mtime.cn/pi/2019/05/08/095140.63331061_1000X1000.jpg','安东·梅格尔季切夫','尼古拉·库利科夫 /安德烈·库烈奇克',' 弗拉基米尔·马什科夫','剧情 /运动','俄罗斯','英语',120,'2019-06-13 00:00:00','绝杀慕尼黑 ','电影根据体育历史中著名的传奇真实事件改编，讲述了1972年慕尼黑奥运会篮球决赛中，前苏联篮球队打败了保持了36年全胜纪录的美国队的传奇故事。在决赛结束前三秒钟，美国队以一分优势领先。美国队已经开始提前庆祝比赛的胜利了，因为他们相信比赛结果已成定局。然而，一位来自苏联的不知名篮球教练，率领着艰难、困苦、贫穷中的苏联国家队，比赛结果发生逆转，整个篮球历史也发生了改变。',0);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_like`
--

DROP TABLE IF EXISTS `movie_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_like` (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`movie_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_like`
--

LOCK TABLES `movie_like` WRITE;
/*!40000 ALTER TABLE `movie_like` DISABLE KEYS */;
INSERT INTO `movie_like` VALUES (10,12,'2019-03-25 02:40:19'),(11,1,'2019-03-22 09:38:12'),(11,2,'2019-03-23 09:38:12'),(11,3,'2019-03-22 08:38:12'),(12,1,'2019-03-23 09:48:46'),(12,3,'2019-03-25 06:36:22'),(14,1,'2019-03-23 09:38:12'),(16,12,'2019-03-23 15:27:48');
/*!40000 ALTER TABLE `movie_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule`VALUES
(20,1,10,'2019-06-23 10:00:00','2019-06-23 12:00:00',20.5),
(69,1,10,'2019-06-21 15:20:00','2019-06-21 17:20:00',35),
(70,2,10,'2019-06-21 15:20:00','2019-06-21 17:20:00',35),
(71,2,11,'2019-06-22 12:00:00','2019-06-22 14:00:00',35),
(72,1,11,'2019-06-22 12:00:00','2019-06-22 14:00:00',35),
(73,1,10,'2019-06-21 13:00:00','2019-06-21 15:00:00',35),
(74,2,12,'2019-06-21 14:00:00','2019-06-21 16:00:00',35),
(75,1,15,'2019-06-21 10:00:00','2019-06-21 12:00:00',53),
(76,2,11,'2019-06-21 11:00:00','2019-06-21 13:00:00',36),
(77,1,13,'2019-06-21 08:00:00','2019-06-21 09:26:00',32),
(78,2,13,'2019-06-21 17:00:00','2019-06-21 18:26:00',32),
(79,1,21,'2019-06-19 11:00:00','2019-06-19 13:00:00',32),
(80,1,21,'2019-06-20 11:00:00','2019-06-20 13:00:00',32),
(81,2,19,'2019-06-22 17:00:00','2019-06-22 19:00:00',32),
(82,1,19,'2019-06-22 17:00:00','2019-06-22 19:00:00',32),
(83,1,20,'2019-06-19 17:00:00','2019-06-19 19:00:00',32),
(84,2,20,'2019-06-19 17:10:00','2019-06-19 19:10:00',32);


/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
						`id` int(11) NOT NULL AUTO_INCREMENT,
                        `user_id` int(11) DEFAULT NULL,
                        `schedule_id` int(11) DEFAULT NULL,
                        `column_index` int(11) DEFAULT NULL,
                        `row_index` int(11) DEFAULT NULL,
                        `state` tinyint(4) DEFAULT 0,
                        `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        `paymentMode` tinyint(4) DEFAULT -1,
                        `couponId` int(11) DEFAULT 0,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `level` tinyint(4) DEFAULT 1,
  `ticket_consumption` double DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'testname','123456',1,0),(3,'test','123456',1,0),(5,'test1','123456',1,0),(7,'test121','123456',1,0),(8,'root','123456',3,0),(10,'roottt','123123',1,0),(12,'zhourui','123456',1,0),(13,'abc123','abc123',1,0),(15,'dd','123',1,0),(16,'AzureXH','441402026ok',1,0),(17,'manager','123456',4,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES (1,7);
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card`
--

DROP TABLE IF EXISTS `vip_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT 0,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (1,15,1,200,'2019-06-15 00:00:00');
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund`
--

DROP TABLE IF EXISTS `refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund` (
  `id` int(11)  NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `is_vip` tinyint(4) NOT NULL,
  `free_time` VARCHAR(255) DEFAULT NULL,
  `false_time` VARCHAR(255) DEFAULT NULL,
  `start_time` VARCHAR(255) NOT NULL,
  `end_time` VARCHAR(255) NOT NULL,
  `penalty` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund`
--

LOCK TABLES `refund` WRITE;
/*!40000 ALTER TABLE `refund` DISABLE KEYS */;
INSERT INTO `refund` VALUES (1,'退票策略1',1,'06:00','00:15','05:00','06:00',0.10),
							(2,'退票策略1',1,'06:00','00:15','03:00','05:00',0.20),
							(3,'退票策略1',1,'06:00','00:15','01:00','03:00',0.30), 
							(4,'退票策略1',1,'06:00','00:15','00:15','01:00',0.40), 
							(5,'退票策略2',0,'23:00','00:30','12:00','23:00',0.10),
							(6,'退票策略2',0,'23:00','00:30','06:00','12:00',0.25),
							(7,'退票策略2',0,'23:00','00:30','03:00','06:00',0.40),
							(8,'退票策略2',0,'23:00','00:30','00:30','03:00',0.50);
/*!40000 ALTER TABLE `refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_service`
--

DROP TABLE IF EXISTS `vip_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_service` (
                        `id` int(11)  NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        `price` DOUBLE NOT NULL,
                        `discount_req` INT (11) NOT NULL,
                        `discount_res` INT (11) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_service`
--

LOCK TABLES `vip_service` WRITE;
/*!40000 ALTER TABLE `vip_service` DISABLE KEYS */;
INSERT INTO `vip_service` VALUES (1,'普通会员卡',25,200,30),(2,'贵宾会员卡',70,300,50),(3,'至尊会员卡',100,450,80);
/*!40000 ALTER TABLE `vip_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_charge`
--

DROP TABLE IF EXISTS `vip_charge`;
CREATE TABLE `vip_charge` (
						`id` int(11) NOT NULL AUTO_INCREMENT,
                        `user_id` int(11) DEFAULT NULL,
                        `card_id` int(11) DEFAULT NULL,
                        `charge_amount` float NOT NULL,
                        `discount_amount` float DEFAULT 0,
                        `charge_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

--
-- Dumping events for database 'cinema'
--

--
-- Dumping routines for database 'cinema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-24 21:20:52
