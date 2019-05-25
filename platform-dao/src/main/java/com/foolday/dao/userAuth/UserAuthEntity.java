package com.foolday.dao.userAuth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.HttpMethodType;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 用来管理每个
 * @author userkdg
 * @date 2019/5/23 23:24
 **/
@Data
@ToString(callSuper = true)
@TableName("t_user_auth")
public class UserAuthEntity extends BaseEntity<UserAuthEntity> {
    private String userId;

    private String authUrl;

    private HttpMethodType authHttpMethod;

    private CommonStatus status;

    private String baseUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserAuthEntity that = (UserAuthEntity) o;

        return new EqualsBuilder()
                .append(userId, that.userId)
                .append(authUrl, that.authUrl)
                .append(authHttpMethod, that.authHttpMethod)
                .append(status, that.status)
                .append(baseUrl, that.baseUrl)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(authUrl)
                .append(authHttpMethod)
                .append(status)
                .append(baseUrl)
                .toHashCode();
    }
}
