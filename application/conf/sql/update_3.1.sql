DROP TABLE IF EXISTS `system_client_2`;
alter table system_client_info add product_model varchar(40) default "";
alter table system_client_info add gujian_version varchar(17) default "";
alter table product_update add `gujian_version_after` varchar(80) default '';
alter table system_client_info change gujian_version gujian_version_current varchar(17) default '';

update product_update set  gujian_version_after = '3.3' where id in (1,2,3,4,5);
update product_update set  gujian_version_after = '3.5' where id in (13, 14);



