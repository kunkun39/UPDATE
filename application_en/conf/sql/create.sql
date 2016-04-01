SET FOREIGN_KEY_CHECKS=0;

-- 系统模块
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `contactway` varchar(255) COLLATE utf8_bin DEFAULT '',
  `username` varchar(48) COLLATE utf8_bin DEFAULT '',
  `password` varchar(48) COLLATE utf8_bin DEFAULT '',
  `enabled` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(120) DEFAULT '',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKA0303E4E8BB56392` FOREIGN KEY (`parent_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `product_category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(120) DEFAULT '',
  `model` varchar(120) DEFAULT '',
  `description` text,
  `product_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKED8DCCEF5E729B6E` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_responsible_category_link`;
CREATE TABLE `user_responsible_category_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKF3C9199E493A45AB` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FKF3C9199E6BF75EBE` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `FKF3C9199E90CAFEC7` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `user_responsible_category_link_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `user_responsible_category_link_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `product_update_file`;
CREATE TABLE `product_update_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `actual_filename` varchar(240) COLLATE utf8_bin DEFAULT '',
  `actual_filepath` varchar(240) COLLATE utf8_bin DEFAULT '',
  `upload_time` datetime DEFAULT NULL,
  `upload_filename` varchar(240) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `product_update`;
CREATE TABLE `product_update` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `product_id` int(11) NOT NULL,
  `product_update_way` varchar(10) COLLATE utf8_bin DEFAULT '',
  `product_update_file_id` int(11) DEFAULT NULL,
  `product_update_url` varchar(1024) COLLATE utf8_bin DEFAULT '',
  `software_version` varchar(10) COLLATE utf8_bin DEFAULT '',
  `update_type` varchar(80) COLLATE utf8_bin DEFAULT '',
  `mac_filter` varchar(80) COLLATE utf8_bin DEFAULT '',
  `signature_type` varchar(80) COLLATE utf8_bin DEFAULT '',
  `test_flag` varchar(10) COLLATE utf8_bin DEFAULT '',
  `gujian_version` varchar(80) COLLATE utf8_bin DEFAULT '',
  `view` varchar(1) COLLATE utf8_bin DEFAULT '0',
  `dvb_version` varchar(80) COLLATE utf8_bin DEFAULT '',
  `dvb_provider_code` varchar(80) COLLATE utf8_bin DEFAULT '',
  `ca_type` varchar(80) COLLATE utf8_bin DEFAULT '',
  `ca_version` varchar(80) COLLATE utf8_bin DEFAULT '',
  `ca_depend_version` varchar(80) COLLATE utf8_bin DEFAULT '',
  `app_package` varchar(80) COLLATE utf8_bin DEFAULT '',
  `app_version_range` varchar(80) COLLATE utf8_bin DEFAULT '',
  `app_version` varchar(80) COLLATE utf8_bin DEFAULT '',
  `app_signature_type` varchar(80) COLLATE utf8_bin DEFAULT '',
  `program_name` varchar(80) COLLATE utf8_bin DEFAULT '',
  `program_version` varchar(80) COLLATE utf8_bin DEFAULT '',
  `program_signature_type` varchar(80) COLLATE utf8_bin DEFAULT '',
  `update_model` varchar(1) COLLATE utf8_bin DEFAULT '1',
  `yingjian_version` varchar(40) COLLATE utf8_bin DEFAULT '',
  `version_compare_way` varchar(1) COLLATE utf8_bin DEFAULT '1',
  `client_version` varchar(17) COLLATE utf8_bin DEFAULT '1.0.0',
  `client_apk_url` varchar(240) COLLATE utf8_bin DEFAULT '',
  `gujian_version_after` varchar(80) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`),
  CONSTRAINT `FK43DCE1941EDBBA8` FOREIGN KEY (`product_update_file_id`) REFERENCES `product_update_file` (`id`),
  CONSTRAINT `FK43DCE197E5C400F` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_update_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_update_ibfk_2` FOREIGN KEY (`product_update_file_id`) REFERENCES `product_update_file` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- 更新统计模块
DROP TABLE IF EXISTS `system_update_statistic`;
CREATE TABLE `system_update_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_way` varchar(2) COLLATE utf8_bin DEFAULT '',
  `client_sn` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `time_cost` varchar(18) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 系统日志模块
DROP TABLE IF EXISTS `system_action_log`;
CREATE TABLE `system_action_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `system_user_name` varchar(40) COLLATE utf8_bin DEFAULT '',
  `system_user_number` varchar(40) COLLATE utf8_bin NOT NULL,
  `system_action` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 用户更新的记录
DROP TABLE IF EXISTS `system_client`;
CREATE TABLE `system_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sta_year` int(4) NOT NULL,
  `sta_month` int(2) NOT NULL,
  `sta_day` int(2) NOT NULL,
  `sta_hour` int(2) NOT NULL,
  `username` varchar(17) COLLATE utf8_bin NOT NULL,
  `gujian_version` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `product_model` varchar(40) COLLATE utf8_bin DEFAULT '',
  `gujian_version_after` varchar(20) COLLATE utf8_bin DEFAULT '1.0',
  `successful` varchar(1) COLLATE utf8_bin DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `system_client_info`;
CREATE TABLE `system_client_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(17) COLLATE utf8_bin NOT NULL,
  `product_model` varchar(40) COLLATE utf8_bin DEFAULT '',
  `gujian_version_current` varchar(17) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



SET FOREIGN_KEY_CHECKS=1;