package com.changhong.system.web.listener;

import com.changhong.yupan.service.ClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: Jack Wang
 * Date: 14-11-18
 * Time: 下午2:01
 */
public class SystemContextListener implements ServletContextListener {

    private ClientService clientService;

    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    /**
     * 用户的升级历史记录是30条一次的批更新，所以如果是在30条以下的话，在CONTEXT DESTORY的时候，应该把剩下的全部持久化到数据库中
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        clientService = (ClientService) appCtx.getBean("clientService");
        clientService.updateLeftClientInfo();
    }
}
