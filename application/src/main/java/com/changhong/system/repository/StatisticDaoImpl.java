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

    public JSONArray loadDailyClientUpdateAmountByMonth(String productModel, String updateSuccess, int year, int month) throws JSONException {
        Map<Integer, Integer> statistic = new HashMap<Integer, Integer>();
        JSONArray array = new JSONArray();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //全年统计
        if (month > 0) {
            String sql = "select sta_day, count(id) as total from system_client " +
                    "where successful = '" + updateSuccess  + "' and product_model = '" + productModel + "' and sta_year = " + year + " and sta_month = " + month + " group by sta_day";
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
        } else {
            String sql = "select sta_month, count(id) as total from system_client " +
                    "where successful = '" + updateSuccess  + "' and product_model = '" + productModel + "' and sta_year = " + year + " group by sta_month";
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
            for (int i=1; i<= totalMonths; i++) {
                buffer.append(statistic.get(i) + ",");
            }
            json.put("days", totalMonths);
            json.put("total", buffer.toString().substring(0, buffer.toString().length() - 1));
            array.put(json);
        }

        return array;
    }

    public JSONArray loadDailyClientUpdateAmountByResult(String productModel, int year, int month) throws JSONException {
        JSONArray array = new JSONArray();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //全年统计
        if (month > 0) {
            String sql = "select successful, count(id) as total from system_client where product_model = '" + productModel + "' and sta_year = " + year + " and sta_month = " + month + " group by successful";
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();

            for (Object o : list) {
                Object[] result = (Object[]) o;
                String successful = (String) result[0];
                BigInteger total = (BigInteger) result[1];

                JSONObject json = new JSONObject();
                json.put("successful", successful);
                json.put("total", total);
                array.put(json);
            }
        } else {
            String sql = "select successful, count(id) as total from system_client where product_model = '" + productModel + "' and sta_year = " + year + " group by successful";
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();

            for (Object o : list) {
                Object[] result = (Object[]) o;
                String successful = (String) result[0];
                BigInteger total = (BigInteger) result[1];

                JSONObject json = new JSONObject();
                json.put("successful", successful);
                json.put("total", total);
                array.put(json);
            }
        }

        return array;
    }

    public JSONArray loadVersionClientUpdateAmountByResult(String productModel,String updateSuccess) throws JSONException {
        JSONArray array = new JSONArray();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //版本统计

        //查询数据表中versionAfter得到所有的升级版本
        String sql = "select  DISTINCT gujian_version_after as version from system_client where product_model = '" + productModel + "' group by gujian_version_after";
        SQLQuery query = session.createSQLQuery(sql);

        StringBuffer buffer = new StringBuffer();
        JSONObject jsonVersion = new JSONObject();

        List listVersion=query.list();
        for (Object o : listVersion) {
            String version= (String)o;
            buffer.append(version + ",");
        }
        jsonVersion.put("version",buffer.toString().substring(0, buffer.toString().length() - 1));
        array.put(jsonVersion);

       // select  DISTINCT gujian_version_after from system_client where product_model = 'OTS-2000SCA' group by gujian_version_after;
       // select gujian_version,gujian_version_after, count(gujian_version_after) as total ,successful from system_client where product_model = 'OTS-2000SCA' group by gujian_version_after,successful;

        //统计数据表中versionAfter升级的数量
        String sqlCount = "select count(id) as total from system_client where successful = '" + updateSuccess  + "' and product_model = '" + productModel + "' group by gujian_version_after";
        SQLQuery queryCount = session.createSQLQuery(sqlCount);

        StringBuffer bufferTotal = new StringBuffer();
        JSONObject jsonVersionTotal = new JSONObject();
        List list = queryCount.list();
        for(Object o:list){
            String result= o.toString();
            bufferTotal.append(result + ",");
        }
        jsonVersionTotal.put("total",bufferTotal.toString().substring(0, bufferTotal.toString().length() - 1));
        array.put(jsonVersionTotal);

        return array;
    }
}
