package com.foolday.service.admin;

import com.foolday.dao.table.TableEntity;
import com.foolday.service.PlatformServiceApplication;
import com.foolday.service.api.admin.TableServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import com.foolday.serviceweb.dto.admin.table.TableVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class TableServiceTest {

    @Resource
    private TableServiceApi tableServiceApi;
    private TableVo tableVo;

    @Before
    public void setUp() throws Exception {
        LoginUser user = new LoginUser();
        user.setShopId("b580ecf2f4c60c4a4ab3e7c1b7a7c07f");
        LoginUserHolder.set(user);

        tableVo = new TableVo();
        tableVo.setName("第005号桌");
    }

    @Test
    public void add() {
        boolean add = tableServiceApi.add(tableVo);
        System.out.println(add);
    }

    @Test
    public void list() {
        List<TableEntity> list = tableServiceApi.list();
        System.out.println(list);
    }

    @Test
    public void edit() {
        tableVo.setName("第003桌");
        boolean update = tableServiceApi.edit(tableVo);
        System.out.println(update);
    }

    @Test
    public void delete() {
        boolean update = tableServiceApi.delete("8d7e4c6650992b89f7603ea738c07b33");
        System.out.println(update);
    }
}