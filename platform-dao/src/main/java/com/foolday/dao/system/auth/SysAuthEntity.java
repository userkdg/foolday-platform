package com.foolday.dao.system.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.HttpMethodType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * id varchar(36) not null,
 * url varchar(500) not null comment '控制器的url',
 * auth_http_method tinyint(2) null comment '请求方式 httpMethod',
 * create_time datetime default CURRENT_TIMESTAMP null,
 * update_time datetime null on update CURRENT_TIMESTAMP,
 * status tinyint(2) null comment 'uri是否有效',
 * base_url varchar(100) null comment '基础uri eg：/system || /weChat if null get url else base_url+url'
 *
 * @author userkdg
 * @date 2019/5/25 12:10
 **/
@TableName("t_sys_auth")
@Setter
@Getter
@ToString(callSuper = true)
public class SysAuthEntity extends BaseEntity<SysAuthEntity> {
    private String url;

    private HttpMethodType authHttpMethod;

    /**
     * 请只取有效的urlId
     */
    private CommonStatus status;

    private String baseUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysAuthEntity that = (SysAuthEntity) o;
        return Objects.equals(url, that.url) &&
                authHttpMethod == that.authHttpMethod &&
                status == that.status &&
                Objects.equals(baseUrl, that.baseUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, authHttpMethod, status, baseUrl);
    }
}
