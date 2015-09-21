package com.nogemasa.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;

/**
 * <br/>create at 15-8-10
 *
 * @author liuxh
 * @since 1.0.0
 */
public abstract class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * GET方式的http请求，获取返回结果
     *
     * @return 响应结果字符串
     */
    public static String httpGet(String requestUrl) {
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpResponse response = client.execute(new HttpGet(requestUrl));
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("GET方式的http请求出错，请求地址：{}", requestUrl, e);
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException ignored) {
            }
            return null;
        }
    }

    /**
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（get、post）
     * @param outputStr     提交的数据
     * @return 响应结果字符串
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuilder buffer = new StringBuilder();
        try {
            //创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(sslContext.getSocketFactory());
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            //将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (ConnectException e) {
            logger.error("WeiXin server connection timed out!", e);
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return buffer.toString();
    }
}
