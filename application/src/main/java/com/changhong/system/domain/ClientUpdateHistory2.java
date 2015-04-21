package com.changhong.system.domain;

/**
 * 集群的时候用于另一张表的统计, 在两台集群的服务器中，一台保存到ClientUpdateHistory， 另一台保存到ClientUpdateHistory2，以此类推
 *
 * User: Jack Wang
 * Date: 14-12-4
 * Time: 上午9:37
 */
public class ClientUpdateHistory2 extends ClientUpdateHistory {

    public ClientUpdateHistory2() {
    }

    public ClientUpdateHistory2(String username, String productModel, String guJianVersion, String gujianVersionAfter, String success) {
        super(username, productModel, guJianVersion, gujianVersionAfter, success);
    }
}
