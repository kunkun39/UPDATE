package com.changhong.yupan.web.listener;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.yupan.service.ClientService;
import com.changhong.yupan.web.event.ClientInfoUpdateEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:19
 */
@Service("clientInfoUpdateListener")
public class ClientInfoUpdateListener implements ApplicationListener {

    private Logger logger = Logger.getLogger(ClientInfoUpdateListener.class);

    @Autowired
    private ClientService clientService;

    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ClientInfoUpdateEvent) {
            ClientInfoUpdateEvent clientUpdate = (ClientInfoUpdateEvent) event;
            String username = clientUpdate.getClientUsername();
            String productModel = clientUpdate.getClientProductModel();
            String gujianVersion = clientUpdate.getClientGujianVersion();
            String gujianVersionAfter = clientUpdate.getClientGujianVersionAfter();
            String success = clientUpdate.getSuccess();

            logger.info("device " + username + " update to version " + gujianVersionAfter + "with result " + success);
            clientService.updateClientHistoryInfo(username, productModel, gujianVersion, gujianVersionAfter);
        }
    }
}
