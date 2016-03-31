package com.changhong.system.web.event;

import com.changhong.system.domain.SystemActionLog;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 上午11:29
 */
public class CategoryActionEvent extends AbstractActionEvent {

    private String categoryName;

    public CategoryActionEvent(SystemActionLog.ActionType type, String userName, String userNumber, String categoryName) {
        super("category_action", type, userName, userNumber);
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
