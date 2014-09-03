SET FOREIGN_KEY_CHECKS=0;

-- 系统模块
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) default NULL,
  `contactway` varchar(255) default '',
  `username` varchar(48) default '',
  `password` varchar(48) default '',
  `enabled` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) default '',
  `parent_id` int default NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`parent_id`) REFERENCES product_category (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) default '',
  `model` varchar(120) default '',
  `description` text default NULL,
  `product_category_id` int(11) default NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`product_category_id`) REFERENCES product_category (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `user_responsible_category_link`;
CREATE TABLE `user_responsible_category_link` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  FOREIGN KEY (`user_id`) REFERENCES system_user (`id`),
  FOREIGN KEY (`category_id`) REFERENCES product_category (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `product_update_file`;
CREATE TABLE `product_update_file` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `upload_filename` varchar(240) default '',
  `actual_filename` varchar(240) default '',
  `actual_filepath` varchar(240) default '',
  `upload_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `product_update`;
CREATE TABLE `product_update` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `product_id` int(11) NOT NULL,
  `product_update_way` varchar(10) default '',
  `product_update_file_id` int(11) default NULL,
  `product_update_url` varchar(1024) default '',
  `software_version` varchar(10) default '',
  `update_type` varchar(80) default '',
  `mac_filter` varchar(80) default '',
  `signature_type` varchar(80) default '',
  `test_flag` varchar(10) default '',
  `gujian_version` varchar(80) default '',
  `view` varchar(1) default '0',
  `update_model` varchar(1) default '1',
  `dvb_version` varchar(80) default '',
  `dvb_provider_code` varchar(80) default '',
  `ca_type` varchar(80) default '',
  `ca_version` varchar(80) default '',
  `ca_depend_version` varchar(80) default '',
  `app_package` varchar(80) default '',
  `app_version_range` varchar(80) default '',
  `app_version` varchar(80) default '',
  `app_signature_type` varchar(80) default '',
  `program_name` varchar(80) default '',
  `program_version` varchar(80) default '',
  `program_signature_type` varchar(80) default '',
  PRIMARY KEY  (`id`),
  FOREIGN KEY (`product_id`) REFERENCES product (`id`),
  FOREIGN KEY (`product_update_file_id`) REFERENCES product_update_file (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- 更新统计模块
DROP TABLE IF EXISTS `system_update_statistic`;
CREATE TABLE `system_update_statistic` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `update_way` varchar(2) default '',
  `client_sn` varchar(120) default NULL,
  `time_cost` varchar(18) default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 系统日志模块
DROP TABLE IF EXISTS `system_action_log`;
CREATE TABLE `system_action_log` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `system_user_name` varchar(40) default '',
  `system_user_number` varchar(40) not NULL,
  `system_action` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 用户更新的记录
DROP TABLE IF EXISTS `system_client`;
CREATE TABLE `system_client` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `sta_year` int(4) not NULL,
  `sta_month` int(2) not NULL,
  `sta_day` int(2) not NULL,
  `sta_hour` int(2) not NULL,
  `username` varchar(17) not NULL,
  `product_model` varchar(40) default '',
  `gujian_version` varchar(20) default '',
  `yingjian_version` varchar(40) default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



SET FOREIGN_KEY_CHECKS=1;