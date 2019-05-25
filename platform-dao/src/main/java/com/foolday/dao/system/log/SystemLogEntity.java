package com.foolday.dao.system.log;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.ActionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author userkdg
 * @date 2019/5/22 22:06
 **/
@TableName("t_system_log")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SystemLogEntity extends BaseEntity<SystemLogEntity> {
    /**
     * 操作人姓名
     */
    private String operator;

    private String operatorId;

    /**
     * 记录日志信息是否为异常操作 请求情况 成功、失败
     */
    private ActionStatus operateStatus;

    /**
     * 结果信息
     */
    private String resultMsg;

    /**
     * 访问的资源名称
     */
    private String resourceName;

    /**
     * 访问ip
     */
    private String host;

    /**
     * 请求耗时
     */
    private Long cost;

    /**
     * 动作类型 HttpMethod
     */
    private String action;
    /**
     * 访问链接
     */
    private String requestUrl;

    /**
     * 请求方式 application/json
     */
    private String contentType;
    /**
     * 访问参数集
     */
    private String requestBody;
    /**
     * 结果
     */
    private String responseBody;

    private String shopId;

}
