package com.foolday.common.base;

import com.alibaba.fastjson.JSONObject;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {
    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();
    public Object jsonParam = null;

    /**
     * Adds key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for new param.
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    /**
     * Adds key/value string with Object pair to the request.
     *
     * @param key    the key name for the new param.
     * @param object the value object for new param.
     * @throws FileNotFoundException when the file not find throw a exception.
     */
    public void put(String key, Object object) throws FileNotFoundException {
        if (key != null) {
            fileParams.put(key, object);
        }
    }

    /**
     * Adds object pair to the request.
     *
     * @param object
     */
    public void put(Object object) {
        this.jsonParam = object;
    }

    public boolean isJsonParam() {
        return (this.jsonParam instanceof JSONObject);
    }

}
