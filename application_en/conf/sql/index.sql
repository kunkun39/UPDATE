ALTER TABLE `system_user` ADD INDEX  system_user_index_name(`name`);
ALTER TABLE `product_category` ADD INDEX  product_category_index_name(`name`) ;
ALTER TABLE `product_category` ADD INDEX  product_category_index_parent_id (`parent_id`);
ALTER TABLE `product` ADD INDEX  product_index_name(`name`) ;
ALTER TABLE `product` ADD INDEX  product_index_model(`model`) ;
ALTER TABLE `product` ADD INDEX  product_index_category_id(`product_category_id`) ;
ALTER TABLE `user_responsible_category_link` ADD INDEX  user_responsible_index_user_id(`user_id`) ;
ALTER TABLE `user_responsible_category_link` ADD INDEX  user_responsible_index_category_id(`category_id`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_update_way(`product_update_way`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_update_type(`update_type`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_software_version(`software_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_dvb_version(`dvb_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_app_package(`app_package`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_app_version(`app_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_program_name(`program_name`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_program_version(`program_version`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_file_id(`product_update_file_id`) ;
ALTER TABLE `product_update` ADD INDEX  product_index_product_id(`product_id`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_username(`username`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_year(`sta_year`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_month(`sta_month`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_day(`sta_day`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_hour(`sta_hour`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_version(`gujian_version`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_version_after(`gujian_version_after`) ;
ALTER TABLE `system_client` ADD INDEX  system_client_product_model(`product_model`) ;
ALTER TABLE `system_client_info` ADD INDEX  system_client_info_username(`username`) ;
ALTER TABLE `system_client_info` ADD INDEX  system_client_info_version_current(`gujian_version_current`) ;


