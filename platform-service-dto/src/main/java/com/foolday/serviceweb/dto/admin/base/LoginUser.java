package com.foolday.serviceweb.dto.admin.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean valid;
    private String userId;
    private String userName;
    private String shopId;
    private String browser;
    private String app;
    private String os;
    private String ip;

    public LoginUser() {
    }

    public LoginUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public LoginUser(String userId, String userName, String shopId) {
        this.userId = userId;
        this.userName = userName;
        this.shopId = shopId;
    }

}
