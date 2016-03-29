package com.changhong.yupan.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.Client;
import com.changhong.system.domain.ClientUpdateHistory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
@Repository("clientDao")
public class ClientDaoImpl extends HibernateEntityObjectDao implements ClientDao {

    private static final Log logger = LogFactory.getLog(ClientDaoImpl.class);

    public void updateClientInfo(String username, String productModel, String guJianVersion) {
        List<Client> clients = getHibernateTemplate().find("from Client c where c.username = ?", new Object[]{username});

        if (clients.isEmpty()) {
            Client client = new Client(productModel, username, guJianVersion);
            getHibernateTemplate().saveOrUpdate(client);
        } else {
            Client client = clients.get(0);
            if (!client.getVersion().equals(guJianVersion)) {
                client.setVersion(guJianVersion);
            }
        }
    }

    public synchronized void trackClientUpdateInfo(String username, String productModel, String guJianVersion) {
        ClientUpdateHistory clientUpdateHistory = new ClientUpdateHistory(username, productModel, guJianVersion, guJianVersion, "1");
        List<ClientUpdateHistory> alreadyExists = getHibernateTemplate().find("from ClientUpdateHistory c where c.username = ? and c.productModel = ? and c.guJianVersion = ?",
                new Object[]{username, productModel, guJianVersion});

        if (alreadyExists.isEmpty()) {
            getHibernateTemplate().saveOrUpdate(clientUpdateHistory);
        }
    }

    public void updateLeftClientInfo() {
    }
}
