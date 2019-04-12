package com.foolday.admin.controller;

import com.foolday.admin.base.AdminBaseController;
import com.foolday.service.api.admin.CategoryServiceApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "商品分类", tags = {"商品分类接口"})
@RestController
@RequestMapping("/category")
public class GoodsCategoryController implements AdminBaseController {
    @Resource
    CategoryServiceApi categoryServiceApi;

}
