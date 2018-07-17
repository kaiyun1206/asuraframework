/*
 * Copyright (c) 2018. sunkaiyun1206@163.com.
 */
package org.asuraframework.commons.net;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.asuraframework.commons.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>HTTP 客户端</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2018/7/17 下午7:05
 * @since 1.0
 */
public class AsuraHttpClient {

    private static final String AND = "&";

    private static final String QUESTION_MARK = "?";

    private static final String EQUAL = "=";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int CONNECT_TIMEOUT = 3000;

    private static final int SOCKET_TIMEOUT = 3000;

    private static Logger LOGGER = LoggerFactory.getLogger(AsuraHttpClient.class);

    private static AsuraHttpClient asuraHttpClient;

    public static AsuraHttpClient getInstance() {
        if (Check.isNull(asuraHttpClient)) {
            synchronized (AsuraHttpClient.class) {
                if (Check.isNull(asuraHttpClient)) {
                    asuraHttpClient = new AsuraHttpClient();
                }
            }
        }
        return asuraHttpClient;
    }

    /**
     * 执行get请求
     *
     * @param url
     *
     * @return
     */
    public String doGet(@Nonnull String url) throws IOException {
        return doGet(url, null);
    }

    /**
     * 执行get请求，params中的参数将会附加到url后面
     *
     * @param url    http请求地址
     * @param params 请求参数
     *
     * @return http get请求的响应
     *
     * @throws IOException 异常
     */
    public String doGetWithParams(@Nonnull String url, Map<String, Object> params) throws IOException {
        Objects.requireNonNull(url, "Http request url is null");

        StringBuilder builder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            Set<String> keySet = params.keySet();
            boolean flag = false;
            for (String key : keySet) {
                Object value = params.get(key);
                if (flag) {
                    builder.append(AND);
                }
                builder.append(key).append(EQUAL).append(value);
                flag = true;
            }
        }
        if (url.contains(QUESTION_MARK)) {
            url = url.concat(AND).concat(builder.toString());
        } else {
            url = url.concat(QUESTION_MARK).concat(builder.toString());
        }
        return doGet(url, null);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param header
     *
     * @return
     */
    public String doGet(@Nonnull String url, Map<String, String> header) throws IOException {
        return doGet(url, header, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    /**
     * 执行get请求 带超时参数
     *
     * @param url
     * @param header
     *
     * @return
     */
    public String doGet(@Nonnull String url, Map<String, String> header, int connectTimeout, int socketTimeout)
            throws IOException {
        Objects.requireNonNull(url, "http url cannot be bull");
        /**
         * 默认的httpclient
         */
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("SEND GET REQUEST URL:[{}],connectTimeout:[{}],socketTimeout:[{}]", url, connectTimeout,
                    socketTimeout);
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        handlerHeader(header, httpGet);
        handlerRequestConfig(connectTimeout, socketTimeout, httpGet);
        try {
            return httpClient.execute(httpGet, new StringResponseHandler());
        } finally {
            httpClient.close();
        }
    }

    /**
     * @param url
     * @param param
     *
     * @return
     *
     * @throws IOException
     */
    public String doPostForm(@Nonnull String url, Map<String, String> param) throws IOException {
        return doPostForm(url, param, null, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }


    /**
     * @param url
     * @param params
     * @param header
     *
     * @return
     *
     * @throws IOException
     */
    public String doPostForm(@Nonnull String url, Map<String, String> params, Map<String, String> header) throws
            IOException {
        return doPostForm(url, params, header, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    /**
     * @param url
     * @param params
     * @param header
     * @param connectTimeout
     * @param socketTimeout
     *
     * @return
     *
     * @throws IOException
     */
    public String doPostForm(@Nonnull String url, Map<String, String> params, Map<String, String> header, int
            connectTimeout, int socketTimeout) throws IOException {
        Objects.requireNonNull(url, "http url cannot be bull");
        /**
         * 默认的httpclient
         */
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("SEND POST REQUEST URL:[{}],PARAMS:[{}],connectTimeout:[{}],socketTimeout:[{}]", url, params,
                    connectTimeout, socketTimeout);
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        handlerHeader(header, httpPost);
        handlerRequestConfig(connectTimeout, socketTimeout, httpPost);
        handlerParam(params, httpPost);
        try {
            return httpClient.execute(httpPost, new StringResponseHandler());
        } finally {
            httpClient.close();
        }
    }

    /**
     * @param url
     * @param jsonString
     *
     * @return
     *
     * @throws IOException
     */
    public String doPostJson(@Nonnull String url, String jsonString) throws IOException {
        return doPostJson(url, jsonString, null, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }


    /**
     * @param url
     * @param jsonString
     * @param header
     *
     * @return
     *
     * @throws IOException
     */
    public String doPostJson(@Nonnull String url, String jsonString, Map<String, String> header) throws IOException {
        return doPostJson(url, jsonString, header, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    /**
     * @param url
     * @param jsonString
     * @param header
     * @param connectTimeout
     * @param socketTimeout
     *
     * @return
     *
     * @throws IOException
     */
    public String doPostJson(@Nonnull String url, String jsonString, Map<String, String> header, int connectTimeout,
                             int socketTimeout) throws IOException {
        Objects.requireNonNull(url, "http url cannot be bull");

        /**
         * 默认的httpclient
         */
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("SEND POST REQUEST URL:[{}],PARAMS:[{}],connectTimeout:[{}],socketTimeout:[{}]", url,
                    jsonString, connectTimeout, socketTimeout);
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (Check.isNullOrEmpty(header)) {
            header = new HashMap<>();
            header.put("Content-Type", "application/json");
        }
        handlerHeader(header, httpPost);
        handlerRequestConfig(connectTimeout, socketTimeout, httpPost);
        handlerJsonParam(jsonString, httpPost);
        try {
            return httpClient.execute(httpPost, new StringResponseHandler());
        } finally {
            httpClient.close();
        }
    }

    private void handlerParam(Map<String, String> param, HttpPost httpPost) throws UnsupportedEncodingException {
        if (Check.isNullOrEmpty(param)) {
            return;
        }
        Iterator<String> iterator = param.keySet().iterator();
        List<NameValuePair> nvps = new ArrayList<>(param.size());
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            nvps.add(new BasicNameValuePair(paramName, param.get(paramName)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, CharsetUtils.get(DEFAULT_CHARSET)));
    }

    private void handlerJsonParam(String jsonParam, HttpPost httpPost) {
        if (Check.isNullOrEmpty(jsonParam)) {
            return;
        }
        httpPost.setEntity(new StringEntity(jsonParam, DEFAULT_CHARSET));
    }

    /**
     * @param connectTimeout
     * @param socketTimeout
     * @param httpRequestBase
     */
    private void handlerRequestConfig(int connectTimeout, int socketTimeout, HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectTimeout)
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * @param header
     * @param httpRequestBase
     */
    private void handlerHeader(Map<String, String> header, HttpRequestBase httpRequestBase) {
        if (Check.isNullOrEmpty(header)) {
            return;
        }
        Iterator<String> iterator = header.keySet().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            httpRequestBase.addHeader(headerName, header.get(headerName));
        }
    }

    class StringResponseHandler extends AbstractResponseHandler<String> {

        private String charset;

        private StringResponseHandler() {
            this(DEFAULT_CHARSET);
        }

        private StringResponseHandler(String charset) {
            this.charset = charset;
        }

        @Override
        public String handleEntity(HttpEntity entity) throws IOException {
            return EntityUtils.toString(entity, charset);
        }

    }
}
