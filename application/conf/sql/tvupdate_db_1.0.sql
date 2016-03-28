-- MySQL dump 10.11
--
-- Host: localhost    Database: tvupdate
-- ------------------------------------------------------
-- Server version	5.0.88-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) default '',
  `model` varchar(120) default '',
  `description` text,
  `product_category_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `product_index_name` (`name`),
  KEY `product_index_model` (`model`),
  KEY `FKED8DCCEF5E729B6E` (`product_category_id`),
  CONSTRAINT `FKED8DCCEF5E729B6E` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_category` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) default '',
  `parent_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `product_category_index_name` (`name`),
  KEY `FKA0303E4E8BB56392` (`parent_id`),
  CONSTRAINT `FKA0303E4E8BB56392` FOREIGN KEY (`parent_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `product_category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_update`
--

DROP TABLE IF EXISTS `product_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_update` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `product_id` int(11) NOT NULL,
  `product_update_way` varchar(10) collate utf8_bin default '',
  `product_update_file_id` int(11) default NULL,
  `product_update_url` varchar(1024) collate utf8_bin default '',
  `software_version` varchar(10) collate utf8_bin default '',
  `update_type` varchar(80) collate utf8_bin default '',
  `mac_filter` varchar(80) collate utf8_bin default '',
  `signature_type` varchar(80) collate utf8_bin default '',
  `test_flag` varchar(10) collate utf8_bin default '',
  `gujian_version` varchar(80) collate utf8_bin default '',
  `view` varchar(1) collate utf8_bin default '0',
  `dvb_version` varchar(80) collate utf8_bin default '',
  `dvb_provider_code` varchar(80) collate utf8_bin default '',
  `ca_type` varchar(80) collate utf8_bin default '',
  `ca_version` varchar(80) collate utf8_bin default '',
  `ca_depend_version` varchar(80) collate utf8_bin default '',
  `app_package` varchar(80) collate utf8_bin default '',
  `app_version_range` varchar(80) collate utf8_bin default '',
  `app_version` varchar(80) collate utf8_bin default '',
  `app_signature_type` varchar(80) collate utf8_bin default '',
  `program_name` varchar(80) collate utf8_bin default '',
  `program_version` varchar(80) collate utf8_bin default '',
  `program_signature_type` varchar(80) collate utf8_bin default '',
  `update_model` varchar(1) collate utf8_bin default '1',
  `yingjian_version` varchar(40) collate utf8_bin default '',
  `version_compare_way` varchar(1) collate utf8_bin default '1',
  PRIMARY KEY  (`id`),
  KEY `product_index_update_way` (`product_update_way`),
  KEY `product_index_software_version` (`software_version`),
  KEY `product_index_dvb_version` (`dvb_version`),
  KEY `product_index_app_package` (`app_package`),
  KEY `product_index_app_version` (`app_version`),
  KEY `product_index_program_name` (`program_name`),
  KEY `product_index_program_version` (`program_version`),
  KEY `FK43DCE1941EDBBA8` (`product_update_file_id`),
  KEY `FK43DCE197E5C400F` (`product_id`),
  CONSTRAINT `FK43DCE1941EDBBA8` FOREIGN KEY (`product_update_file_id`) REFERENCES `product_update_file` (`id`),
  CONSTRAINT `FK43DCE197E5C400F` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_update_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_update_ibfk_2` FOREIGN KEY (`product_update_file_id`) REFERENCES `product_update_file` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_update`
--

LOCK TABLES `product_update` WRITE;
/*!40000 ALTER TABLE `product_update` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_update_file`
--

DROP TABLE IF EXISTS `product_update_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_update_file` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `actual_filename` varchar(240) collate utf8_bin default '',
  `actual_filepath` varchar(240) collate utf8_bin default '',
  `upload_time` datetime default NULL,
  `upload_filename` varchar(240) collate utf8_bin default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_update_file`
--

LOCK TABLES `product_update_file` WRITE;
/*!40000 ALTER TABLE `product_update_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_update_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_action_log`
--

DROP TABLE IF EXISTS `system_action_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_action_log` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `system_user_name` varchar(40) collate utf8_bin default '',
  `system_user_number` varchar(40) collate utf8_bin NOT NULL,
  `system_action` text collate utf8_bin,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_action_log`
--

LOCK TABLES `system_action_log` WRITE;
/*!40000 ALTER TABLE `system_action_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_action_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_client`
--

DROP TABLE IF EXISTS `system_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_client` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `sta_year` int(4) NOT NULL,
  `sta_month` int(2) NOT NULL,
  `sta_day` int(2) NOT NULL,
  `sta_hour` int(2) NOT NULL,
  `username` varchar(17) collate utf8_bin NOT NULL,
  `gujian_version` varchar(20) collate utf8_bin default NULL,
  `product_model` varchar(40) collate utf8_bin default '',
  `gujian_version_after` varchar(20) collate utf8_bin default '1.0',
  PRIMARY KEY  (`id`),
  KEY `system_client_username` (`username`),
  KEY `system_client_year` (`sta_year`),
  KEY `system_client_month` (`sta_month`),
  KEY `system_client_day` (`sta_day`),
  KEY `system_client_hour` (`sta_hour`),
  KEY `system_client_gujian_version` (`gujian_version`),
  KEY `client_gujian_version_after` (`gujian_version_after`)
) ENGINE=InnoDB AUTO_INCREMENT=4536 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_client`
--

LOCK TABLES `system_client` WRITE;
/*!40000 ALTER TABLE `system_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_client_info`
--

DROP TABLE IF EXISTS `system_client_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_client_info` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `username` varchar(17) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `system_client_info_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_client_info`
--

LOCK TABLES `system_client_info` WRITE;
/*!40000 ALTER TABLE `system_client_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_client_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_update_statistic`
--

DROP TABLE IF EXISTS `system_update_statistic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_update_statistic` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `update_way` varchar(2) collate utf8_bin default '',
  `client_sn` varchar(120) collate utf8_bin default NULL,
  `time_cost` varchar(18) collate utf8_bin default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_update_statistic`
--

LOCK TABLES `system_update_statistic` WRITE;
/*!40000 ALTER TABLE `system_update_statistic` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_update_statistic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) collate utf8_bin default NULL,
  `contactway` varchar(255) collate utf8_bin default '',
  `username` varchar(48) collate utf8_bin default '',
  `password` varchar(48) collate utf8_bin default '',
  `enabled` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  PRIMARY KEY  (`id`),
  KEY `system_user_index_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user`
--

LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_responsible_category_link`
--

DROP TABLE IF EXISTS `user_responsible_category_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_responsible_category_link` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKF3C9199E90CAFEC7` (`user_id`),
  KEY `FKF3C9199E6BF75EBE` (`category_id`),
  KEY `FKF3C9199E493A45AB` (`user_id`),
  CONSTRAINT `FKF3C9199E493A45AB` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FKF3C9199E6BF75EBE` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `FKF3C9199E90CAFEC7` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `user_responsible_category_link_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `user_responsible_category_link_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_responsible_category_link`
--

LOCK TABLES `user_responsible_category_link` WRITE;
/*!40000 ALTER TABLE `user_responsible_category_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_responsible_category_link` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-20 11:00:44
