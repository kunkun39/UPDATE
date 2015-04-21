package com.changhong.yupan.service;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:24
 */
public interface ClientService {

    public void updateClientInfo(String username, String productModel, String guJianVersion, String guJianVersionAfter, String success);

    public void updateLeftClientInfo();
}
