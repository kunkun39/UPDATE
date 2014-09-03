package com.changhong.yupan.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.ClientUpdateHistory;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
@Repository("clientDao")
public class ClientDaoImpl extends HibernateEntityObjectDao implements ClientDao {

    public void updateClientInfoByUsername(String username, String productModel, String guJianVersion, String guJianVersionAfter) {
        /** old code remove, because of this doesn's support history clientUpdateHistory update statistic
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        SQLQuery query = session.createSQLQuery("select id from system_client where username = '" + username + "'");
        List list = query.list();
        if (list.isEmpty()) {
            ClientUpdateHistory clientUpdateHistory = new ClientUpdateHistory(username, guJianVersion);
            SQLQuery insert = session.createSQLQuery("insert system_client(username, gujian_version, sta_year, sta_month, sta_day, sta_hour) " +
                    "values ('" + username + "', '" + guJianVersion + "', '" + clientUpdateHistory.getYear() + "', '" + clientUpdateHistory.getMonth() + "', '" + clientUpdateHistory.getDay() + "', '" + clientUpdateHistory.getHour() + "')");
            insert.executeUpdate();
        } else {
            int id = (Integer)list.get(0);
            SQLQuery update = session.createSQLQuery("update system_client set gujian_version = '" + guJianVersion + "' where id = " + id);
            update.executeUpdate();
        }
        **/

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        ClientUpdateHistory clientUpdateHistory = new ClientUpdateHistory(username, productModel, guJianVersion, guJianVersionAfter);
        SQLQuery insert = session.createSQLQuery("insert system_client(username, product_model, gujian_version, gujian_version_after, sta_year, sta_month, sta_day, sta_hour) " +
                "values ('" + username + "', '" + productModel + "','" + guJianVersion + "', '" + guJianVersionAfter +
                "', '" + clientUpdateHistory.getYear() + "', '" + clientUpdateHistory.getMonth() + "', '" + clientUpdateHistory.getDay() + "', '" + clientUpdateHistory.getHour() + "')");
        insert.executeUpdate();
    }
}
