ALTER TABLE `system_user` ADD INDEX  system_user_index_name(`name`);
ALTER TABLE `product_category` ADD INDEX  product_category_index_name(`name`) ;
ALTER TABLE `product` ADD INDEX  product_index_name(`name`) ;
ALTER TABLE `product` ADD INDEX  product_index_model(`model`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_update_way(`product_update_way`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_update_type(`update_type`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_software_version(`software_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_dvb_version(`dvb_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_app_package(`app_package`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_app_version(`app_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_program_name(`program_name`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_program_version(`program_version`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_username(`username`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_year(`sta_year`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_month(`sta_month`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_day(`sta_day`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_hour(`sta_hour`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_gujian_version(`gujian_version`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_product_model(`product_model`) ;
ALTER TABLE `system_client_info` ADD INDEX  system_client_info_username(`username`) ;


