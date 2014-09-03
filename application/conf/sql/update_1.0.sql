alter table product_update add username_from_filter varchar(17) default NULL;
alter table product_update add username_to_filter varchar(17) default NULL;

-- 用户基本信息
DROP TABLE IF EXISTS `system_client_info`;
CREATE TABLE `system_client_info` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `username` varchar(17) not NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

