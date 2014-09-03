package com.changhong.http;

import com.changhong.yupan.service.HttpClientRequestImpl;
import junit.framework.TestCase;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午5:09
 */
public class DeviceUpdateFlowTest extends TestCase {

    public void testExecuteDeviceUpdateWithLive1() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"username\": \"65:88:ff:73:6B:3A\",\n" +
                "        \"model\": \"hi3716cv200\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"18\",\n" +
                "\t\t\"macadress\":\"0000\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"true\",\n" +
                "        \"firmwareversion\": \"0.8\",\n" +
                "        \"hardwareversion\": \"0.1\"\n" +
                "\t\t}\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://www.ottserver.com:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateWithLive2() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"username\": \"00:14:49:AD:FF:61\",\n" +
                "        \"model\": \"OTS-2000SC\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"17\",\n" +
                "\t\t\"macadress\":\"1\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"true\",\n" +
                "        \"firmwareversion\": \"1.9\",\n" +
                "        \"hardwareversion\": \"0101\"\n" +
                "\t\t}\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://www.ottserver.com:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateWith1_1() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"username\": \"65:88:ff:73:69:47\",\n" +
                "        \"model\": \"hi3716cv200\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"18\",\n" +
                "\t\t\"macadress\":\"0000\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"true\",\n" +
                "        \"firmwareversion\": \"1111.1004\",\n" +
                "        \"hardwareversion\": \"1.1\"\n" +
                "\t\t}\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateWith1_2() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"model\": \"X039030\",\n" +
                "        \"datatype\": \"1\",\n" +
                "        \"androidsdk\": \"18\",\n" +
                "\t\t\"macadress\":\"1100\",\n" +
                "        \"signtype\": \"test\",\n" +
                "        \"testmode\": \"false\",\n" +
                "        \"firmwareversion\": \"3.0\"\n" +
                "\t\t}\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testConcurrentUpdateWith1_1() {
        for (int i = 0; i < 10000; i++) {
            Thread thead = new Thread() {
                @Override
                public void run() {
                    testExecuteDeviceUpdateWith1_1();
                }
            };
            thead.run();
        }
    }

    public void testConcurrentUpdateWith1_2() {
        for (int i = 0; i < 100; i++) {
            testExecuteDeviceUpdateWith1_1();
        }
    }

    public void testExecuteDeviceUpdateWith2() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"model\": \"X039030\",\n" +
                "        \"datatype\": \"2\",\n" +
                "        \"androidsdk\": \"17\",\n" +
                "\t\t\"macadress\":\"1100\",\n" +
                "        \"signtype\": \"_ignore\",\n" +
                "        \"testmode\": \"false\",\n" +
                "        \"firmware_diffversion\": \"2.0\"\n" +
                "\t\t}\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateWith3() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"model\": \"X039030\",\n" +
                "        \"datatype\": \"3\",\n" +
                "        \"androidsdk\": \"17\",\n" +
                "\t\t\"macadress\":\" 1100\",\n" +
                "        \"signtype\": \"_ignore\",\n" +
                "        \"testmode\": \"false\",\n" +
                "        \"firmwareversion\": \"1.0\",\n" +
                "\t\t\"operatorcode\":\"0001\",\n" +
                "\t\t\"catype\":\"01\",\n" +
                "\t\t\"caversion\":\"01\",\n" +
                "\t\t\"refercoreversion\":\"18\",\n" +
                "\t\t\"dtvversion\":\"2\"\n" +
                "\t\t}\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateWith4() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"packagename\": \"com.changhong.test \",\n" +
                "\t\t\"datatype\":\"4\",\n" +
                "\t\t\"sdkadapt\":\"17\",\n" +
                "        \"signtype\": \"SP\",\n" +
                "\t\t\"appversioncode\":\"10\",\n" +
                "        \"testmode\": \"false\"\n" +
                "    }\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }

    public void testExecuteDeviceUpdateWith5() {
        String json = "{\n" +
                "    \"client\": {\n" +
                "        \"model\": \"X039030\",\n" +
                "\t\t\"dataname\":\"hireferko\",\n" +
                "\t\t\"datatype\":\"5\",\n" +
                "        \"signtype\": \"CODE\",\n" +
                "\t\t\"dataversioncode\":\"1\",\n" +
                "        \"testmode\": \"true\"\n" +
                "       \n" +
                "    }\n" +
                "}";
        HttpClientRequestImpl client = new HttpClientRequestImpl();
        PostMethod method = new PostMethod("http://localhost:8080/update/main.html");
        method.addParameter("json", json);
        String s = client.httpPostRequest(method);
        System.out.println(s);
    }
}
