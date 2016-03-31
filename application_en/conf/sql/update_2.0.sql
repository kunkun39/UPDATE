DROP TABLE IF EXISTS `system_client_2`;
CREATE TABLE `system_client_2` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `sta_year` int(4) not NULL,
  `sta_month` int(2) not NULL,
  `sta_day` int(2) not NULL,
  `sta_hour` int(2) not NULL,
  `username` varchar(17) not NULL,
  `product_model` varchar(40) default '',
  `gujian_version` varchar(20) default '',
  `gujian_version_after` varchar(20) default '1.0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE system_client ADD successful varchar (1) DEFAULT '0';
ALTER TABLE system_client_2 ADD successful varchar(1) DEFAULT '0';
UPDATE system_client set successful = '1';