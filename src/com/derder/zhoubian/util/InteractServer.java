package com.derder.zhoubian.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-5
 * Time: 下午5:14
 * 与服务器进行交互
 */
public class InteractServer {

    /**
     * get方式从服务器获取数据
     * @param url
     * @return
     * @throws IOException
     */
    public static String getDataFromServer(String url) throws IOException {
        String s = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int length;
            while ((length = is.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            s = new String(out.toByteArray(), "UTF-8");

        } else {
        }
        return s;
    }

    public static String postDataToServer(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpRequst = new HttpPost(url);
        return null;
    }
}
