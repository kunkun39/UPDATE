package com.changhong.system.web.event;

import com.changhong.system.domain.SystemActionLog;

/**
 * User: Jack Wang
 * Date: 14-5-6
 * Time: 下午5:23
 */
public class UserActionEvent extends AbstractActionEvent {

    private String actionUserName;

    public UserActionEvent(Object source, SystemActionLog.ActionType type, String userName, String userNumber, String actionUserName) {
        super("user_action", type, userName, userNumber);
        this.actionUserName = actionUserName;
    }

    public String getActionUserName() {
        return actionUserName;
    }
}
