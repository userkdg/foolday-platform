package com.foolday.admin.controller;

import com.foolday.admin.base.bean.LoginUserHolder;
import com.foolday.common.dto.FantResult;
import com.foolday.dao.table.TableEntity;
import com.foolday.service.api.admin.QrCodeServiceApi;
import com.foolday.service.api.admin.TableServiceApi;
import com.foolday.serviceweb.dto.admin.table.TableVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "桌位接口", tags = {"桌位操作接口"})
@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableServiceApi tableServiceApi;

    @Resource
    private QrCodeServiceApi qrCodeServiceApi;

    @ApiOperation(value = "新增桌位", notes = "传入json格式")
    @PostMapping("/add")
    public FantResult<String> add(
            @ApiParam(name = "tableVo", value = "店铺对象", required = true) @RequestBody TableVo tableVo) {
        FantResult<String> result = new FantResult<>();
        boolean ret = tableServiceApi.add(tableVo, LoginUserHolder.get());
        return ret ? FantResult.ok() : FantResult.fail();
    }

    @ApiOperation(value = "查看所有桌位")
    @GetMapping("/list")
    public FantResult<List<TableEntity>> list() {
        FantResult<List<TableEntity>> ret = new FantResult<>();
        List<TableEntity> tableEntityList = tableServiceApi.list();
        ret.setData(tableEntityList);
        return ret;
    }

    @ApiOperation(value = "修改桌位", notes = "传入json")
    @PostMapping("/edit")
    public FantResult edit(
            @ApiParam(name = "tableVo", value = "桌位对象", required = true)
                    @RequestBody TableVo tableVo) {
        boolean flag = tableServiceApi.edit(tableVo);
        return flag ? FantResult.ok() : FantResult.fail();
    }

    @ApiOperation(value = "删除桌位", notes = "form-data")
    @PostMapping("/delete")
    public FantResult delete(
            @ApiParam(name="ids", value="id数组", required = true)
            @RequestParam(value = "ids") String ids
    ){
        boolean flag = tableServiceApi.delete(ids);
        return flag ? FantResult.ok() : FantResult.fail();
    }

    @ApiOperation(value = "创建桌位并绑定二维码", notes = "json")
    @PostMapping("/addAndBindQrcode")
    public FantResult addAndBindQrcode(
            @ApiParam(name="content", value="二维码内容", required = true)
            @RequestParam String content,
            @ApiParam(name="tableVo", value = "桌位对象", required = true)
            @RequestBody TableVo tableVo
    ){
        FantResult result = FantResult.fail();
        boolean addFlag = tableServiceApi.add(tableVo,LoginUserHolder.get());
        if(addFlag){
            String id = qrCodeServiceApi.createQrcodeImg(content);
            boolean bFlag = tableServiceApi.bindQrcode(tableVo, id);
            result =  bFlag ? FantResult.ok() : FantResult.fail();
        }
        return result;
    }

}
