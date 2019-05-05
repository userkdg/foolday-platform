package com.foolday.common.util;

import com.alibaba.fastjson.JSONObject;
import com.foolday.common.base.RequestParams;
import lombok.Synchronized;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于okhttp3的简单封装
 */
public class HttpUtils {
    private static final long TIME_OUT = 60000L;

    private volatile static OkHttpClient okHttpClient;

    private static class Holder {
        private static HttpUtils instance = new HttpUtils();
    }

    public static HttpUtils getHttpUtilsInstance() {
        return Holder.instance;
    }

    private HttpUtils() {
        initConfig();
    }

    @Synchronized
    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null)
            initConfig();
        return okHttpClient;
    }

    /**
     * 初始化：为OkHttpClient配置参数
     */
    private void initConfig() {
        /****** 配置基本参数 ******/
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //设置连接超时时间
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        //设置写操作超时时间
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //设置读操作超时时间
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        //设置重定向
        okHttpClientBuilder.followRedirects(true);


        /****** 添加https支持 ******/
        okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
        //信任所有Https
//        okHttpClientBuilder.sslSocketFactory(HttpUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        okHttpClient = okHttpClientBuilder.build();
    }

    public Request createPostRequest(String url, RequestParams params) {
        Request request;
        if (params != null) {
            if (params.isJsonParam()) {
                if (params.getJsonParam() instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) params.getJsonParam();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
                    request = new Request.Builder().url(url).post(body).build();
                } else {
                    throw new IllegalArgumentException("RequestParams.jsonParam must a JsonObject");
                }
            } else {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : params.getUrlParams().entrySet()) {
                    //将请求参数添加到请求体中
                    bodyBuilder.add(entry.getKey(), entry.getValue());
                }
                FormBody body = bodyBuilder.build();
                request = new Request.Builder().url(url).post(body).build();
            }
        } else {
            //如果请求参数为空时，当成get请求处理
            request = new Request.Builder().url(url).get().build();
        }

        return request;
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public ResponseBody executePostRequest(String url, RequestParams params) {
        Request postRequest = createPostRequest(url, params);
        Call call = getOkHttpClient().newCall(postRequest);
        try (Response execute = call.execute()) {
            return execute.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String executePostRequestResult(String url, RequestParams params) {
        try {
            return executePostRequest(url, params).string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String executeGetRequestResult(String url, RequestParams params) {
        try {
            return executeGetRequest(url, params).string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseBody executeGetRequest(String url, RequestParams params) {
        Request postRequest = createGetRequest(url, params);
        Call call = getOkHttpClient().newCall(postRequest);
        try (Response execute = call.execute()) {
            return execute.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.getUrlParams().entrySet()) {
                //将请求参数添加到请求体中
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        return new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get()
                .build();
    }

    /**
     * 发送http/https请求
     *
     * @param request
     * @param callback
     * @return
     */
    public void sendRequest(Request request, Callback callback) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
