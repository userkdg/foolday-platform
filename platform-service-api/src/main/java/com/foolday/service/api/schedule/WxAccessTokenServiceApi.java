package com.foolday.service.api.schedule;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface WxAccessTokenServiceApi {

    /**
     * json string
     *
     * @return
     */
    Optional<String> activeRefreshAccessToken();

    /**
     * json string 2 map
     *
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
    static Map<String, Object> json2Map(Optional<String> jsonStr) {
        return new Gson().fromJson(jsonStr.orElse("{}"), HashMap.class);
    }

    Optional<String> refreshAccessToken();

    Optional<String> returnAccessToken();

    Optional<String> forceRefreshAccessToken();
}
