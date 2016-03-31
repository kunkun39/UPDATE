package com.changhong.yunpan.utils;

import com.changhong.yupan.service.HttpClientRequestImpl;
import com.changhong.yupan.utils.ExternalUrlGetterUtils;
import junit.framework.TestCase;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SimpleTrigger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-4
 * Time: 上午11:18
 */
public class ExternalUrlGetterUtilsTest extends TestCase {

    public void testGetActualUrl() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("com/changhong/yunpan/utils/testcases_urls.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(in);
            Element rootElement = document.getRootElement();

            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                String name = element.attributeValue("name");
                System.out.println("Wang pan test name:" + name);

                List<Element> urls = element.elements();
                for (Element url : urls) {
                    String externalUrl = url.getText();
                    String parserUrl = ExternalUrlGetterUtils.getActualUrl(externalUrl);
                    if ("".equals(parserUrl)) {
                        System.out.println(externalUrl + " -> 无效");
                    } else {
                        System.out.println(externalUrl + " -> " + parserUrl);
                    }
                }
                System.out.println("----------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testUploadFile() throws Exception {
        for (int i = 0; i < 2; i++) {
            HttpClientRequestImpl client = new HttpClientRequestImpl();

            File file = new File("D:\\ChangHong\\Applications\\application\\src\\test\\com\\changhong\\yunpan\\utils\\testcases_urls.xml");
            String url = "http://localhost:8080/index.html";

            MultipartPostMethod multipartPostMethod = new MultipartPostMethod(url);
            FilePart part1 = new FilePart("upfile_data", file);
            multipartPostMethod.addPart(part1);
            String response = client.httpPostRequest(multipartPostMethod);
            System.out.println(response);
        }
    }

    public void testBingFa() throws Exception {
        for (int i = 0; i < 10000; i++) {
            HttpClientRequestImpl client = new HttpClientRequestImpl();

            File file = new File("D:\\ChangHong\\Applications\\application\\src\\test\\com\\changhong\\yunpan\\utils\\testcases_urls.xml");
            String url = "http://localhost:8080/backend/.html";

            MultipartPostMethod multipartPostMethod = new MultipartPostMethod(url);
            FilePart part1 = new FilePart("upfile_data", file);
            multipartPostMethod.addPart(part1);
            String response = client.httpPostRequest(multipartPostMethod);
            System.out.println(response);
        }
    }

    public void testGetUrl() {
        PostMethod postMethod = new PostMethod("http://dl.vmall.com/c08ctnvepa");

        HttpClient client = new HttpClient();
        client.setConnectionTimeout(30000);
        client.getParams().setContentCharset("UTF-8");

        String response = "";
        int status = 0;
        try {
            status = client.executeMethod(postMethod);
            InputStream in = postMethod.getResponseBodyAsStream();
            FileCopyUtils.copy(in, new FileOutputStream(new File("D://nice.txt")));
            System.out.println(111);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            postMethod.releaseConnection();
        }

    }

}
