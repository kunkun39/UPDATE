package com.changhong.system.web.facade.dto;

import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:27
 */
public class SystemActionLogDTO {

    private Date time;

    private String userName;

    private String description;

    public SystemActionLogDTO() {
    }

    public SystemActionLogDTO(Date time, String userName, String description) {
        this.time = time;
        this.userName = userName;
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
