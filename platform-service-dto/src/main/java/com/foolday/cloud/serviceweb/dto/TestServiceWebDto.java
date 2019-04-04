package com.foolday.cloud.serviceweb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class TestServiceWebDto implements Serializable {
    private String id;
    private String name;
}
