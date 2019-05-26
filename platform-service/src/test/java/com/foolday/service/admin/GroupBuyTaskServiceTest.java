package com.foolday.service.admin;

import com.foolday.common.enums.GroupBuyTaskStatus;
import com.foolday.service.api.admin.GroupBuyTaskServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import com.foolday.serviceweb.dto.admin.groupbuytask.GroupBuyTaskVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupBuyTaskServiceTest {

    @Resource
    GroupBuyTaskServiceApi groupBuyTaskServiceApi;

    @Before
    public void setUp(){
        LoginUser user = new LoginUser();
        user.setUserId("asdlkj123oiusdf098xcvpoi");
        LoginUserHolder.set(user);
    }

    @Test
    public void add() {
        GroupBuyTaskVo vo = new GroupBuyTaskVo();
        vo.setGroupbuyId("2e3082816908c361721ad997aa92f2eb");
        groupBuyTaskServiceApi.add(vo);

    }

    @Test
    public void edit() {
        GroupBuyTaskVo vo = new GroupBuyTaskVo();
        vo.setId("721632d7ee32f8fb56a5800ef6120d91");
        vo.setStatus(GroupBuyTaskStatus.无效);
        groupBuyTaskServiceApi.edit(vo);
    }
}