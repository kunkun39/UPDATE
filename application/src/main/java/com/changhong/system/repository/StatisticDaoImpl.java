package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.CHStringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-5-9
 * Time: 下午5:04
 */
@Repository("statisticDao")
public class StatisticDaoImpl extends HibernateEntityObjectDao implements StatisticDao {

    public JSONArray loadDailyClientUpdateAmountByMonth(String productModel, int year, int month) throws JSONException {
        JSONArray array = new JSONArray();
        Map<Integer, Integer> statistic = new HashMap<Integer, Integer>();

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        String sql = "select sta_day, count(id) as total from system_client where product_model = '" + productModel + "' and sta_year = " + year + " and sta_month = " + month + " group by sta_day";
        SQLQuery query = session.createSQLQuery(sql);
        List list = query.list();

        int totalDays = CHDateUtils.getTotalDaysForOneMonth(year, month);
        for (int i = 1; i <= totalDays; i++) {
            statistic.put(i, 0);
        }

        for (Object o : list) {
            Object[] result = (Object[]) o;
            Integer day = (Integer) result[0];
            BigInteger total = (BigInteger) result[1];
            statistic.put(day, total.intValue());
        }

        JSONObject json = new JSONObject();
        StringBuffer buffer = new StringBuffer();
        for (int i=1; i<= totalDays; i++) {
            buffer.append(statistic.get(i) + ",");
        }
        json.put("days", CHDateUtils.getTotalDaysForOneMonth(year, month));
        json.put("total", buffer.toString().substring(0, buffer.toString().length() - 1));
        array.put(json);

        return array;
    }

    public JSONArray loadDailyClientUpdateAmountByVersion(String productModel, int year, int month) throws JSONException {
        JSONArray array = new JSONArray();

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        String sql = "select gujian_version, count(id) as total from system_client where product_model = '" + productModel + "' and sta_year = " + year + " and sta_month = " + month + " group by gujian_version";
        SQLQuery query = session.createSQLQuery(sql);
        List list = query.list();


        for (Object o : list) {
            Object[] result = (Object[]) o;
            String version = (String) result[0];
            BigInteger total = (BigInteger) result[1];

            JSONObject json = new JSONObject();
            json.put("version", version);
            json.put("total", total);
            array.put(json);
        }

        return array;
    }
}
