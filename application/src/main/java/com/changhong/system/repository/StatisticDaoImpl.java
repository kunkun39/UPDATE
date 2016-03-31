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

    public JSONArray loadDailyClientUpdateAmountByMonth(String productModel, int year, int month, String guJianVersion, String guJianVersionAfter) throws JSONException {
        Map<Integer, Integer> statistic = new HashMap<Integer, Integer>();
        JSONArray array = new JSONArray();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //全年统计
        if (month > 0) {
            String sql = "select a.sta_day, count(a.id) as total from system_client a " + "left join system_client_info b on a.username = b.username " +
                    "where a.gujian_version = '" + guJianVersion + "' and a.gujian_version_after = '" + guJianVersionAfter + "' and a.product_model = '" + productModel + "' and a.sta_year = " + year + " and a.sta_month = " + month + " and gujian_version_after <= gujian_version_current  group by a.sta_day";
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
            for (int i = 1; i <= totalDays; i++) {
                buffer.append(statistic.get(i) + ",");
            }
            json.put("days", CHDateUtils.getTotalDaysForOneMonth(year, month));
            json.put("total", buffer.toString().substring(0, buffer.toString().length() - 1));
            array.put(json);
        } else {
            String sql = "select a.sta_month, count(a.id) as total from system_client a " + "left join system_client_info b on a.username = b.username " +
                    "where a.gujian_version = '" + guJianVersion + "' and a.gujian_version_after = '" + guJianVersionAfter + "' and a.product_model = '" + productModel + "' and a.sta_year = " + year + " and gujian_version_after <= gujian_version_current group by a.sta_month";
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();

            int totalMonths = 12;
            for (int i = 1; i <= 12; i++) {
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
            for (int i = 1; i <= totalMonths; i++) {
                buffer.append(statistic.get(i) + ",");
            }
            json.put("days", totalMonths);
            json.put("total", buffer.toString().substring(0, buffer.toString().length() - 1));
            array.put(json);
        }

        return array;
    }

    public JSONArray loadClientVersionAmountByResult(String productModel) throws JSONException {
        JSONArray array = new JSONArray();
        StringBuffer buffer = new StringBuffer();
        StringBuffer bufferTotal = new StringBuffer();
        JSONObject json = new JSONObject();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //统计数据表中versionAfter升级的数量
        String sqlCount = "select DISTINCT(gujian_version_current), count(id) as total from system_client_info where product_model = '" + productModel + "' group by gujian_version_current";
        SQLQuery queryCount = session.createSQLQuery(sqlCount);
        List list = queryCount.list();

        if (list != null && list.size() > 0) {
            for (Object o : list) {
                Object[] result = (Object[]) o;
                String version = (String) result[0];
                String total = result[1].toString();

                buffer.append(version + ",");
                bufferTotal.append(total + ",");
            }

        } else {
            buffer.append("1.0" + ",");
            bufferTotal.append(0 + ",");
        }

        json.put("version", buffer.toString().substring(0, buffer.toString().length() - 1));
        json.put("total", bufferTotal.toString().substring(0, bufferTotal.toString().length() - 1));
        array.put(json);

        return array;
    }
}
