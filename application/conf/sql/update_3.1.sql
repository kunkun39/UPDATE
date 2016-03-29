DROP TABLE IF EXISTS `system_client_2`;
alter table system_client_info add product_model varchar(40) default "";
alter table system_client_info add gujian_version varchar(17) default "";

