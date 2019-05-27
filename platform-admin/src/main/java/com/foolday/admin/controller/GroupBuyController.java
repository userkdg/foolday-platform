package com.foolday.admin.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.dao.groupbuy.GroupBuyEntity;
import com.foolday.service.api.admin.GroupBuyServiceApi;
import com.foolday.serviceweb.dto.admin.groupbuy.GroupBuyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.foolday.common.constant.WebConstant.RESPONSE_RESULT_MSG;

@Api(value = "拼团", tags = {"拼团操作接口"})
@RestController
@RequestMapping("/groupBuy")
public class GroupBuyController {

    @Resource
    private GroupBuyServiceApi groupBuyServiceApi;

    /**
     * 创建一个拼团活动
     */
    @ApiOperation(value = "定义新团购活动", notes = "传入json格式")
    @ApiResponses(@ApiResponse(code = 200, message = RESPONSE_RESULT_MSG, response = FantResult.class))
    @PostMapping("/add")
    public FantResult<Boolean> add(@RequestBody GroupBuyVo groupBuyVo){
        boolean ret = groupBuyServiceApi.add(groupBuyVo);
        return ret ? FantResult.ok() : FantResult.fail();
    }
}
