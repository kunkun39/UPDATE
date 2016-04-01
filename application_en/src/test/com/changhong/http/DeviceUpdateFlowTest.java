package com.changhong.http;

import com.alibaba.fastjson.JSONArray;
import com.changhong.system.domain.User;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.yupan.service.HttpClientRequestImpl;
import junit.framework.TestCase;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午5:09
 */
public class DeviceUpdateFlowTest extends TestCase {

//    public final static String HOST = "http://www.ottserver.com:8080/update/";
    public final static String HOST = "http://chupdate.yuppcdn.net:8080/update/";
//    public final static String HOST = "http://211.149.208.93:8099/update/";

    public void testExecuteDeviceUpdate() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"username\": \"98:2F:3C:D9:E7:93\",\n" +
                "        \"model\": \"IHO-1000_2K\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"19\",\n" +
                "\t\t\"macadress\":\"101\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"false\",\n" +
                "        \"firmwareversion\": \"3.5\",\n" +
                "        \"hardwareversion\": \"00000020\",\n" +
                "        \"apkversion\": \"2.6\"\n" +
                "\t\t}\n" +
                "}";

        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod(HOST + "main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }
}
