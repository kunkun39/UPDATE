package com.changhong.system.web.listener;

import com.changhong.system.domain.SystemActionLog;
import com.changhong.system.service.SystemService;
import com.changhong.system.web.event.CategoryActionEvent;
import com.changhong.system.web.event.ProductActionEvent;
import com.changhong.system.web.event.ProductUpdateActionEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:18
 */
@Service("systemActionLogListener")
public class SystemActionLogListener implements ApplicationListener {

    private Logger logger = Logger.getLogger(SystemActionLogListener.class);

    @Autowired
    private SystemService systemService;

    public void onApplicationEvent(ApplicationEvent event) {
        SystemActionLog log = null;
        if (event instanceof CategoryActionEvent) {
            CategoryActionEvent category = (CategoryActionEvent) event;
            log = SystemActionLog.generateProductCategoryActionLog(category.getType(),
                    category.getUserName(), category.getUserNumber(), category.getCategoryName());
        }
        if (event instanceof ProductActionEvent) {
            ProductActionEvent product = (ProductActionEvent) event;
            log = SystemActionLog.generateProductActionLog(product.getType(),
                    product.getUserName(), product.getUserNumber(), product.getProductName());
        }
        if (event instanceof ProductUpdateActionEvent) {
            ProductUpdateActionEvent update = (ProductUpdateActionEvent) event;
            log = SystemActionLog.generateProductUpdateActionLog(update.getType(),
                    update.getUserName(), update.getUserNumber(), update.getProductName(), update.getUpdateWay());
        }

        if (log != null) {
            systemService.saveSystemActionLog(log);
        }
    }
}
