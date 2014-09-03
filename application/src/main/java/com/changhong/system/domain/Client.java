package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 14-5-22
 * Time: 下午2:11
 */
public class Client extends EntityBase {

    private String username;

    /********************************************GET/SET***********************************************/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
