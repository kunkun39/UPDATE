package com.changhong.yupan.web.event;

import org.springframework.context.ApplicationEvent;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:19
 */
public class ClientInfoUpdateEvent extends ApplicationEvent {

    private String clientUsername;

    private String clientProductModel;

    private String clientGujianVersion;

    private String clientGujianVersionAfter;

    private String success;

    public ClientInfoUpdateEvent(String clientUsername, String clientProductModel, String clientGujianVersion, String clientGujianVersionAfter, String success) {
        super("client_info_update");
        this.clientUsername = clientUsername;
        this.clientProductModel = clientProductModel;
        this.clientGujianVersion = clientGujianVersion;
        this.clientGujianVersionAfter = clientGujianVersionAfter;
        this.success = success;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getClientProductModel() {
        return clientProductModel;
    }

    public String getClientGujianVersion() {
        return clientGujianVersion;
    }

    public String getClientGujianVersionAfter() {
        return clientGujianVersionAfter;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
