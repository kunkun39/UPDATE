package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 14-5-22
 * Time: 下午2:11
 */
public class Client extends EntityBase {

    private String model;

    private String username;

    private String version;

    public Client() {
    }

    public Client(String model, String username, String version) {
        this.model = model;
        this.username = username;
        this.version = version;
    }

    /********************************************GET/SET***********************************************/

     public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
