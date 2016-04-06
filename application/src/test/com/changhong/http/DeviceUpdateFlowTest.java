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

//    public final static String HOST = "http://www.changhongotv.scmcc.com.cn:8080/update/";
    public final static String HOST = "http://192.168.0.104:8080/application_en/";

    public void testExecuteDeviceUpdate() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"username\": \"00:14:49:0D:00:00\",\n" +
                "        \"model\": \"IHO-10004K\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"19\",\n" +
                "\t\t\"macadress\":\"101\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"false\",\n" +
                "        \"firmwareversion\": \"2.0\",\n" +
                "        \"hardwareversion\": \"00000030\",\n" +
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
