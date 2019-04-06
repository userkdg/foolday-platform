package com.foolday.cloud.serviceweb.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@ApiModel("服务层和web层交互实体")
public class TestServiceWebDto implements Serializable {
    private String id;
    private String name;
}
