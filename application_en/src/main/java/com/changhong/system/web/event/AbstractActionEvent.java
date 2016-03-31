package com.changhong.system.web.event;

import com.changhong.system.domain.SystemActionLog;
import org.springframework.context.ApplicationEvent;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 上午11:27
 */
public class AbstractActionEvent extends ApplicationEvent {

    private SystemActionLog.ActionType type;

    private String userName;

    private String userNumber;

    public AbstractActionEvent(Object source, SystemActionLog.ActionType type, String userName, String userNumber) {
        super(source);
        this.userName = userName;
        this.userNumber = userNumber;
        this.type = type;
    }

    public SystemActionLog.ActionType getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNumber() {
        return userNumber;
    }
}
