package com.foolday.common.dto;

import com.foolday.common.handler.HandlerManager;
import com.foolday.common.handler.IHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class FantResult<T> implements Serializable {
    private static final long serialVersionUID = -8189520564623761867L;
    private boolean ok;
    private String message;
    private T data;
    private Map<String, Object> moreData;

    public FantResult() {
    }

    public FantResult(boolean ok, String message, T data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public boolean isOk() {
        return this.ok;
    }

    public FantResult<T> setOk(boolean ok) {
        this.ok = ok;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public FantResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public FantResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Map<String, Object> getMoreData() {
        return this.moreData;
    }

    public FantResult<T> setMoreData(Map<String, Object> extData) {
        this.moreData = extData;
        return this;
    }

    public FantResult<T> addMoreData(String key, Object value) {
        if (this.moreData == null) {
            this.moreData = new LinkedHashMap();
        }

        this.moreData.put(key, value);
        return this;
    }


    /**
     * 异步处理事务
     *
     * @param handler
     * @return
     */
    public FantResult<T> addAsyncHanlder(IHandler handler) {
        HandlerManager.executeSingle(handler);
        return this;
    }

    /**
     * 针对 非要做else if 判断的抽象
     *
     * @param isOk
     * @param <T>
     * @return
     */
    public static <T> FantResult<T> checkAs(boolean isOk) {
        return isOk ? ok() : fail();
    }

    public static <T> FantResult<T> ok() {
        return new FantResult(true, (String) null, (Object) null);
    }

    public static <T> FantResult<T> ok(T data) {
        return new FantResult(true, (String) null, data);
    }

    public static <T> FantResult<T> fail() {
        return new FantResult(false, (String) null, (Object) null);
    }

    public static <T> FantResult<T> fail(String message) {
        return new FantResult(false, message, (Object) null);
    }

    public static <T> FantResult<T> of(boolean ok, String message) {
        return new FantResult(ok, message, (Object) null);
    }

    public static <T> FantResult<T> of(boolean ok, T data, String message) {
        return new FantResult(ok, message, data);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
