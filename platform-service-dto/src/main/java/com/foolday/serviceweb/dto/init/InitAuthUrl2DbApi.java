package com.foolday.serviceweb.dto.init;

import java.io.IOException;

/**
 * @author userkdg
 * @date 2019/5/25 14:42
 **/
public interface InitAuthUrl2DbApi {
    void initAuthUrlFromClass(String baseSystemUrl, String basePackage) throws IOException;
}
