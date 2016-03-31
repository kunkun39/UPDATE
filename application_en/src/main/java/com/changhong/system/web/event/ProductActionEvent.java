package com.changhong.system.web.event;

import com.changhong.system.domain.SystemActionLog;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 上午11:34
 */
public class ProductActionEvent extends AbstractActionEvent {

    private String productName;

    public ProductActionEvent(SystemActionLog.ActionType type, String userName, String userNumber, String productName) {
        super("product_action", type, userName, userNumber);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
