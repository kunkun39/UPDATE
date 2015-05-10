package com.changhong.yupan.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.ClientUpdateHistory;
import com.changhong.system.domain.ClientUpdateHistory2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
@Repository("clientDao")
public class ClientDaoImpl extends HibernateEntityObjectDao implements ClientDao {

    private static final Log logger = LogFactory.getLog(ClientDaoImpl.class);

    @Value("${project.machine.id}")
    private int machineID;

    private static List<ClientUpdateHistory> histories = new ArrayList<ClientUpdateHistory>();

    public synchronized void updateClientInfoByUsername(String username, String productModel, String guJianVersion, String guJianVersionAfter, String success) {
        /**
         * 老的保存用户更新记录，每一次插入都开一个连接
         */
//        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
//
//        ClientUpdateHistory clientUpdateHistory = new ClientUpdateHistory(username, productModel, guJianVersion, guJianVersionAfter);
//        SQLQuery insert = session.createSQLQuery("insert system_client(username, product_model, gujian_version, gujian_version_after, sta_year, sta_month, sta_day, sta_hour) " +
//                "values ('" + username + "', '" + productModel + "','" + guJianVersion + "', '" + guJianVersionAfter +
//                "', '" + clientUpdateHistory.getYear() + "', '" + clientUpdateHistory.getMonth() + "', '" + clientUpdateHistory.getDay() + "', '" + clientUpdateHistory.getHour() + "')");
//        insert.executeUpdate();

        /**
         * 最新的, MYSQL批插入，速度是5倍以上，请看测试代码
         */
        ClientUpdateHistory clientUpdateHistory = new ClientUpdateHistory(username, productModel, guJianVersion, guJianVersionAfter, success);
        histories.add(clientUpdateHistory);
        if (histories.size() >= 10) {
            getHibernateTemplate().saveOrUpdateAll(histories);
            histories.clear();
        }
    }

    public void updateLeftClientInfo() {
        if (histories.size() > 0) {
            logger.info("save left client update history " + histories.size());
            getHibernateTemplate().saveOrUpdateAll(histories);
            histories.clear();
        }
    }
}
