alter table system_client add `gujian_version_after` varchar(20) default '1.0';
ALTER TABLE `system_client` ADD INDEX  client_gujian_version_after(`gujian_version_after`) ;