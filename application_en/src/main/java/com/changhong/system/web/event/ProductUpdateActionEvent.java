package com.changhong.system.web.event;

import com.changhong.system.domain.SystemActionLog;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 上午11:35
 */
public class ProductUpdateActionEvent extends AbstractActionEvent {

    private String productName;

    private String updateWay;

    public ProductUpdateActionEvent(SystemActionLog.ActionType type, String userName, String userNumber, String productName, String updateWay) {
        super("product_update_action", type, userName, userNumber);
        this.productName = productName;
        this.updateWay = updateWay;
    }

    public String getProductName() {
        return productName;
    }

    public String getUpdateWay() {
        return updateWay;
    }
}
