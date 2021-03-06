package com.foolday.common.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对请求体的三种类型的参数封装
 * get -> urlParams
 * post -> jsonObject
 * multipart-form/data -> fileParams
 */
public class RequestParams implements Serializable {
    private ConcurrentHashMap<String, String> urlParams;
    private ConcurrentHashMap<String, Object> fileParams;
    private JSONObject jsonObject = new JSONObject();

    private RequestParams() {
        urlParams = new ConcurrentHashMap<>();
        fileParams = new ConcurrentHashMap<>();
    }

    enum RequestParamType {
        JSON,
        FILE,
        URL
    }

    /**
     * 原有的singleton 单列存在数据共享问题，调整为独立的实例对象 （工厂模式）
     *
     * @return
     */
    public static RequestParams getInstance() {
        return new RequestParams();
    }

    public RequestParams putJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.paramType = RequestParamType.JSON;
        return this;
    }

    public RequestParams putJsonObjectMap(Map<String, Object> map) {
        if (this.jsonObject != null) {
            this.jsonObject.putAll(map);
        } else {
            this.jsonObject = new JSONObject(map);
        }
        this.paramType = RequestParamType.JSON;
        return this;
    }

    public RequestParams putJsonObjectMap(RequestParamType paramType, Map<String, Object> map) {
        this.paramType = paramType;
        this.jsonObject = new JSONObject(map);
        return this;
    }

    public RequestParams putJsonObject(RequestParamType paramType, JSONObject jsonObject) {
        this.paramType = paramType;
        this.jsonObject = jsonObject;
        return this;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /*
    null > http get
     */
    private RequestParamType paramType = null;

    public ConcurrentHashMap<String, Object> getFileParams() {
        return fileParams;
    }

    public ConcurrentHashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public final RequestParamType getParamType() {
        return paramType;
    }

    /**
     * Adds key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for new param.
     */
    public RequestParams putUrlKV(String key, String value) {
        if (!RequestParamType.URL.equals(this.paramType)) {
            this.paramType = RequestParamType.URL;
        }
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
        return this;
    }

    /**
     * Adds key/value string with Object pair to the request.
     *
     * @param key    the key name for the new param.
     * @param object the value object for new param.
     */
    public RequestParams putFileKV(String key, Object object) {
        if (!RequestParamType.FILE.equals(this.paramType)) {
            this.paramType = RequestParamType.FILE;
        }
        if (key != null) {
            fileParams.put(key, object);
        }
        return this;
    }

    public boolean isJsonParam() {
        return (RequestParamType.JSON.equals(this.paramType));
    }

}
