package com.foolday.common.constant;

/**
 * web层常理
 */
public final class WebConstant {
    private WebConstant() {
    }

    public static final String AUTH_AUTHED_TOKEN = "FoolDay-Auth";

    /**
     * 对所有密码加常量盐
     */
    public static final String PLATFORM_PASSWORD_HEX_CHAR = "PASSWORD_HEX_";

    /**
     * 使用session来存储用户的shopId的key
     */
    public static final String USER_SHOP_ID_FROM_SESSION = "USER_SHOP_ID_KEY_";

    /**
     * 存储与redis中的key
     */
    public static final String ADMIN_USER_SHOPID_KEY = "admin:user:shopId";
    /**
     * 针对swagger中返回给前端的通用描述信息
     */
    public static final String RESPONSE_RESULT_MSG = "正常返回，其中FantResult.data[]=>返回的数据;FantResult.ok=false=>FantResult.message的失败信息,FantResult.ok=true=>FantResult.message为成功信息";

    public static final String RESPONSE_PAGE_MSG = "正常返回分页列表，其中FantPage.rows[]=>返回的数据;FantPage.totalCount=>总数量;FantPage.pageSize=>每一页数量;FantPage.pageNo=>当前页码";

}
