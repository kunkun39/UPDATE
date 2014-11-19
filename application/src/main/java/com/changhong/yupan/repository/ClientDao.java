package com.changhong.yupan.repository;


import com.changhong.common.repository.EntityObjectDao;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
public interface ClientDao extends EntityObjectDao {

    public void updateClientInfoByUsername(String username, String productModel, String guJianVersion, String guJianVerionAfter);

    void updateLeftClientInfo();
}
