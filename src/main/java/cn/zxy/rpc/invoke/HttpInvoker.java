package cn.zxy.rpc.invoke;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Silence
 * @Date 2017/6/10
 */
public class HttpInvoker implements Invoker {
    public static final Invoker invoker = new HttpInvoker();
    public static final HttpClient httpClient = getHttpClient();
    public static final String CHARSET = "UTF-8";

    private static HttpClient getHttpClient() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(200);
        manager.setDefaultMaxPerRoute(20);
        HttpHost localhost = new HttpHost("lcoalhsot", 8080);
        manager.setMaxPerRoute(new HttpRoute(localhost), 50);
        return HttpClients.custom().setConnectionManager(manager).build();
    }

    public String request(String request, ConsumerConfig consumerConfig) {
        HttpPost httpPost = new HttpPost(consumerConfig.getUrl());
        httpPost.setHeader("Connection","Keep-Alive");
        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("data",request));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,CHARSET));
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(),CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void response(String response, OutputStream outputStream) {
        try {
            outputStream.write(response.getBytes(CHARSET));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
