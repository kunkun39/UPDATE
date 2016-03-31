package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import com.changhong.update.domain.ProductUpdate;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 上午10:40
 */
public class SystemActionLog extends EntityBase {

    private String userName;

    private String userNumber;

    private String action;

    public enum ActionType {
        ADD,
        EDIT,
        DELETE
    }

    public static SystemActionLog generateProductCategoryActionLog(ActionType type, String userName, String userNumber, String categoryName) {
        SystemActionLog log = new SystemActionLog();
        log.setUserName(userName);
        log.setUserNumber(userNumber);

        StringBuffer buffer = new StringBuffer();
        if (type.equals(ActionType.ADD)) {
            buffer.append("add new product category <" + categoryName + ">");
        } else if (type.equals(ActionType.EDIT)) {
            buffer.append("edit product category <" + categoryName + ">");
        } else if (type.equals(ActionType.DELETE)) {
            buffer.append("delete product category <" + categoryName + ">");
        }
        log.setAction(buffer.toString());

        return log;
    }

    public static SystemActionLog generateProductActionLog(ActionType type, String userName, String userNumber, String productName) {
        SystemActionLog log = new SystemActionLog();
        log.setUserName(userName);
        log.setUserNumber(userNumber);

        StringBuffer buffer = new StringBuffer();
        if (type.equals(ActionType.ADD)) {
            buffer.append("add new product <" + productName + ">");
        } else if (type.equals(ActionType.EDIT)) {
            buffer.append("edit product <" + productName + ">");
        } else if (type.equals(ActionType.DELETE)) {
            buffer.append("delete product <" + productName + ">");
        }
        log.setAction(buffer.toString());

        return log;
    }

    public static SystemActionLog generateProductUpdateActionLog(ActionType type, String userName, String userNumber,
                                              String productName, String updateWay) {
        SystemActionLog log = new SystemActionLog();
        log.setUserName(userName);
        log.setUserNumber(userNumber);

        StringBuffer buffer = new StringBuffer();
        String updateName = ProductUpdate.getUpdateWayName(updateWay);
        if (type.equals(ActionType.ADD)) {
            buffer.append("add new update setting for product <" + productName + "> which name is <" + updateName + ">");
        } else if (type.equals(ActionType.EDIT)) {
            buffer.append("edit <" + productName + "> update information which name is <" + updateName + ">");
        } else if (type.equals(ActionType.DELETE)) {
            buffer.append("delete <" + productName + "> update information which name is<" + updateName + ">");
        }
        log.setAction(buffer.toString());

        return log;
    }

    /*************************************************GETTER**********************************************************/

    public String getAction() {
        return action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
