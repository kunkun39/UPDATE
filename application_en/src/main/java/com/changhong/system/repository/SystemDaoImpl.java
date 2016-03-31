package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.SystemActionLog;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:33
 */
@Repository("systemDao")
public class SystemDaoImpl extends HibernateEntityObjectDao implements SystemDao {

    public int loadSystemActionLogSize(int year, int month) {
        Date start = CHDateUtils.getFirstDateOfMonth(year, month);
        Date end = CHDateUtils.getFirstDateOfNextMonth(year, month);

        String hql = "select count(l.id) from SystemActionLog l where l.timestamp >= ? and l.timestamp < ? order by l.timestamp desc";

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter(0, start);
        query.setParameter(1, end);

        List list = query.list();
        return ((Long)list.get(0)).intValue();
    }

    public List<SystemActionLog> loadSystemActionLog(int year, int month, int startPosition, int pageSize) {
        Date start = CHDateUtils.getFirstDateOfMonth(year, month);
        Date end = CHDateUtils.getFirstDateOfNextMonth(year, month);

        String hql = "from SystemActionLog l where l.timestamp >= ? and l.timestamp < ? order by l.timestamp desc";

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter(0, start);
        query.setParameter(1, end);
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<SystemActionLog> logs = query.list();
        return logs;
    }
}
