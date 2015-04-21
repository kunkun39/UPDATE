package com.changhong.http;

import com.changhong.update.domain.Product;
import com.changhong.yupan.service.HttpClientRequestImpl;
import junit.framework.TestCase;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午5:09
 */
public class DeviceUpdateFlowTest extends TestCase {

    public void testExecuteDeviceUpdate() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"username\": \"C6:C3:13:8A:D0:45\",\n" +
                "        \"model\": \"OTS-2000SCA\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"17\",\n" +
                "\t\t\"macadress\":\"\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"true\",\n" +
                "        \"firmwareversion\": \"2.0005\",\n" +
                "        \"hardwareversion\": \"0102\"\n" +
                "\t\t}\n" +
                "}";

        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateResult() {
        String json = "{\n" +
                "        \"username\": \"C6:C3:13:8A:D0:45\",\n" +
                "        \"model\": \"OTS-2000SCA\",\n" +
                "        \"versionBefore\": \"2.0005\",\n" +
                "        \"versionAfter\": \"2.006\",\n" +
                "        \"success\": \"0\"\n" +
                "}";

        for (int i = 0; i < 20; i++) {
            HttpClientRequestImpl client = new HttpClientRequestImpl();
            PostMethod method = new PostMethod("http://localhost:8080/update/report.html");
            method.addParameter("json", json);
            String s = client.httpPostRequest(method);
            System.out.println(s);
        }
    }
}
