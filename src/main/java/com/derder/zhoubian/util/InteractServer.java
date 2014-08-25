package com.derder.zhoubian.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-5
 * Time: 下午5:14
 * 与服务器进行交互
 */
public class InteractServer {
    private String RESULT_MAP_CODE_KEY = "code";
    private String RESULT_MAP_MESSAGE_KEY = "message";
    /**
     * get方式从服务器获取数据
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Map<String,String> getDataFromServer(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        return result(response);
    }

    public Map<String,String> postDataToServer(String url, List<NameValuePair> textNameValuePairList,
                                          List<NameValuePair> fileNameValuePairList) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        ContentType TEXT_PLAIN = ContentType.create("text/plain", Charset.forName("UTF_8"));
        if (null != textNameValuePairList) {
            for (NameValuePair textNameValuePair : textNameValuePairList) {
                multipartEntityBuilder.addTextBody(textNameValuePair.getName(), textNameValuePair.getValue(),TEXT_PLAIN);
            }
        }

        if (null != fileNameValuePairList) {
            for (NameValuePair fileNameValuePair : fileNameValuePairList) {
                multipartEntityBuilder.addBinaryBody(fileNameValuePair.getName(), new File(fileNameValuePair.getValue()));
            }
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        HttpResponse response = httpClient.execute(httpPost);
        return result(response);
    }

    private Map<String,String> result(HttpResponse response) throws IOException {
        Map<String,String> resultMap = new HashMap<String, String>();
        String s;
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
            resultMap.put(RESULT_MAP_CODE_KEY,HttpStatus.SC_OK+"");
            resultMap.put(RESULT_MAP_MESSAGE_KEY,s);
        } else {
            resultMap.put(RESULT_MAP_CODE_KEY,response.getStatusLine().getStatusCode()+"");
            resultMap.put(RESULT_MAP_MESSAGE_KEY,response.getStatusLine().getReasonPhrase());
        }
        return resultMap;
    }

    public String getRESULT_MAP_CODE_KEY() {
        return RESULT_MAP_CODE_KEY;
    }

    public String getRESULT_MAP_MESSAGE_KEY() {
        return RESULT_MAP_MESSAGE_KEY;
    }
}
