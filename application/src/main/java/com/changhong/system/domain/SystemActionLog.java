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
            buffer.append("添加新目录<" + categoryName + ">");
        } else if (type.equals(ActionType.EDIT)) {
            buffer.append("对目录<" + categoryName + ">进行了编辑操作");
        } else if (type.equals(ActionType.DELETE)) {
            buffer.append("对目录<" + categoryName + ">进行了删除操作");
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
            buffer.append("添加新产品<" + productName + ">");
        } else if (type.equals(ActionType.EDIT)) {
            buffer.append("对产品<" + productName + ">进行了编辑操作");
        } else if (type.equals(ActionType.DELETE)) {
            buffer.append("对产品<" + productName + ">进行了删除操作");
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
            buffer.append("对产品<" + productName + ">添加了<" + updateName + ">");
        } else if (type.equals(ActionType.EDIT)) {
            buffer.append("对产品<" + productName + "><" + updateName + ">进行了编辑操作");
        } else if (type.equals(ActionType.DELETE)) {
            buffer.append("对产品<" + productName + "><" + updateName + ">进行了删除操作");
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
