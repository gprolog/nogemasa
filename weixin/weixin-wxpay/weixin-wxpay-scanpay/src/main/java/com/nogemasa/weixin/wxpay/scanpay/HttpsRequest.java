package com.nogemasa.weixin.wxpay.scanpay;

import com.tencent.common.Configure;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * <br/>create at 15-9-24
 *
 * @author liuxh
 * @since 1.0.0
 */
public class HttpsRequest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private boolean hasInit = false;// 表示请求器是否已经做了初始化工作
    private int socketTimeout = 10000;// 连接超时时间，默认10秒
    private int connectTimeout = 30000;// 传输超时时间，默认30秒
    private RequestConfig requestConfig;// 请求器的配置
    private CloseableHttpClient httpClient;// HTTP请求器

    public HttpsRequest() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, IOException {
        init();
    }

    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException,
            KeyManagementException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream inputStream = new FileInputStream(new File(Configure.getCertLocalPath()))) {
            keyStore.load(inputStream, Configure.getCertPassword().toCharArray());//设置证书密码
        } catch (CertificateException e) {
            logger.error("证书问题", e);
        } catch (NoSuchAlgorithmException ignore) {
            // I'm sure that NoSuchAlgorithmException would not happen
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
        resetRequestConfig();// 根据默认超时限制初始化requestConfig
        hasInit = true;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url         API地址
     * @param postDataXML 要提交的XML数组字符串
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String sendPost(String url, String postDataXML)
            throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyManagementException {
        if (!hasInit) {
            init();
        }
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(new StringEntity(postDataXML, "UTF-8"));// 指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        httpPost.setConfig(requestConfig);// 设置请求器的配置
        logger.info("executing request" + httpPost.getRequestLine());
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            logger.error("http get throw ConnectionPoolTimeoutException(wait time out)", e);
        } catch (ConnectTimeoutException e) {
            logger.error("http get throw ConnectTimeoutException", e);
        } catch (SocketTimeoutException e) {
            logger.error("http get throw SocketTimeoutException", e);
        } catch (Exception e) {
            logger.error("http get throw Exception", e);
        } finally {
            httpPost.abort();
        }
        return result;
    }

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig() {
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)
                .build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}
