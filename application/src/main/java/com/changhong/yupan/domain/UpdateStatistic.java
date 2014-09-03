package com.changhong.yupan.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 14-4-18
 * Time: 上午10:20
 */
public class UpdateStatistic extends EntityBase {

    private String updateWay;

    private String clientSn;

    private long timeCost;

    public UpdateStatistic() {
    }

    public UpdateStatistic(String updateWay, String clientSn, long timeCost) {
        this.updateWay = updateWay;
        this.clientSn = clientSn;
        this.timeCost = timeCost;
    }

    public String getUpdateWay() {
        return updateWay;
    }

    public void setUpdateWay(String updateWay) {
        this.updateWay = updateWay;
    }

    public String getClientSn() {
        return clientSn;
    }

    public void setClientSn(String clientSn) {
        this.clientSn = clientSn;
    }

    public long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
    }
}
