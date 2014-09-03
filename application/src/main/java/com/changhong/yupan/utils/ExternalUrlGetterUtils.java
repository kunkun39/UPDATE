package com.changhong.yupan.utils;

import com.changhong.yupan.service.HttpClientRequestImpl;
import org.json.JSONObject;

/**
 * User: Jack Wang
 * Date: 14-4-4
 * Time: 上午10:37
 */
public class ExternalUrlGetterUtils {

    private static final String domain = "http://wl.xbdj.cn/";

    private static final String BAIDU_WANGPAN_MATCH_PATTER1 = "^(http://)?pan.baidu.com/s/[A-Za-z0-9]+";

    private static final String BAIDU_WANGPAN_MATCH_PATTER2 = "^(http://)?pan.baidu.com/share/link\\?shareid=[0-9]+&uk=[0-9]+";

    private static final String HUAWEI_WANGPAN_MATCH_PATTER1 = "^(http://)?dl.vmall.com/[A-Za-z0-9]+";

    private static final String HUAWEI_WANGPAN_MATCH_PATTER2 = "^(http://)?dl.dbank.com/[A-Za-z0-9]+";

    private static final String XUNIE_WANGPAN_MATCH_PATTER1 = "^(http://)?kuai.xunlei.com/d/[A-Za-z0-9]+";

    public static String getActualUrl(String externalUrl) {
        String actualUrl = "";

        try {
            StringBuilder builder = new StringBuilder();

            //百度网盘
            if(externalUrl.matches(BAIDU_WANGPAN_MATCH_PATTER1)) {
                builder.append("http://www.xbdj.cn/wailian/function/getType.php?type=baidu&id=new&uk=");
                String[] tokens = externalUrl.split("/");
                builder.append(tokens[tokens.length - 1]);

                HttpClientRequestImpl requester = new HttpClientRequestImpl();
                String response = requester.httpGetRequestForString(builder.toString());
                JSONObject json = new JSONObject(response);
                if (json.get("status").equals("1") || json.getInt("status")  == 1) {
                    actualUrl = domain + "baidu/new/" + tokens[tokens.length - 1] + json.get("type");
                } else {
                    actualUrl = "";
                }

            } else if (externalUrl.matches(BAIDU_WANGPAN_MATCH_PATTER2)) {
                builder.append("http://www.xbdj.cn/wailian/function/getType.php?type=baidu&id=");
                String[] tokens = externalUrl.split("\\?");
                String parameters = tokens[tokens.length - 1];
                String[] values = parameters.split("&");
                String id = values[0].split("=")[1];
                String uk = values[1].split("=")[1];
                builder.append(id);
                builder.append(uk);

                HttpClientRequestImpl requester = new HttpClientRequestImpl();
                String response = requester.httpGetRequestForString(builder.toString());
                JSONObject json = new JSONObject(response);
                if (json.get("status").equals("1")) {
                    actualUrl = domain + "baidu/" + id + "/" + uk;
                } else {
                    actualUrl = "";
                }
            }


            //华为网盘
            if(externalUrl.matches(HUAWEI_WANGPAN_MATCH_PATTER1) || externalUrl.matches(HUAWEI_WANGPAN_MATCH_PATTER2)) {
                builder.append("http://www.xbdj.cn/wailian/function/getType.php?type=dbank&fid=");
                String[] tokens = externalUrl.split("/");
                builder.append(tokens[tokens.length - 1]);

                HttpClientRequestImpl requester = new HttpClientRequestImpl();
                String response = requester.httpGetRequestForString(builder.toString());
                JSONObject json = new JSONObject(response);
                if (json.get("status").equals("1") || json.getInt("status")  == 1) {
                    actualUrl = domain + "dbank/" + tokens[tokens.length - 1] + json.get("type");
                } else {
                    actualUrl = "";
                }
            }

            //迅雷快传
            if(externalUrl.matches(XUNIE_WANGPAN_MATCH_PATTER1)) {
                builder.append("http://www.xbdj.cn/wailian/function/getType.php?type=xunlei&fid=");
                String[] tokens = externalUrl.split("/");
                builder.append(tokens[tokens.length - 1]);

                HttpClientRequestImpl requester = new HttpClientRequestImpl();
                String response = requester.httpGetRequestForString(builder.toString());
                JSONObject json = new JSONObject(response);
                if (json.get("status").equals("1") || json.getInt("status")  == 1) {
                    actualUrl = domain + "xunlei/" + tokens[tokens.length - 1] + json.get("type");
                } else {
                    actualUrl = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actualUrl;
    }
}
