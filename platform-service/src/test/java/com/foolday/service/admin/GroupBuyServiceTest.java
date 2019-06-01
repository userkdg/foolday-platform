package com.foolday.service.admin;

import com.foolday.service.api.admin.GroupBuyServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.groupbuy.GroupBuyVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupBuyServiceTest {

    @Resource
    GroupBuyServiceApi groupBuyServiceApi;

    @Before
    public void setUp() throws Exception {
        LoginUser user = new LoginUser();
        user.setShopId("b580ecf2f4c60c4a4ab3e7c1b7a7c07f");
//        LoginUserHolder.set(user);
    }

    @Test
    public void add() {
        GroupBuyVo vo = new GroupBuyVo();
        vo.setConditionNum(8);
        vo.setName("新团购，三人拼团");
        vo.setCurrPrice(20.50f);
        vo.setOriPrice(33.00f);
        LocalDate startlocalDate = LocalDate.of(2019, 5, 5);
        LocalDate endlocalDate = LocalDate.of(2019, 6, 5);
        LocalTime localTime = LocalTime.of(0, 0, 0, 0);
        LocalDateTime startlocalDateTime = LocalDateTime.of(startlocalDate, localTime);
        LocalDateTime endlocalDateTime = LocalDateTime.of(endlocalDate, localTime);
        vo.setStartTime(startlocalDateTime);
        vo.setEndTime(endlocalDateTime);
        vo.setIncludeShopIds("280763b0bc926997b5d0708a6d9db73b,640fcb878c4095a77778cc83c5933249");
        vo.setImgIds("a3c7970fbf044b5ebff7674e5534318b");
        vo.setRule("店内使用");
        vo.setUseStartTime("08:00");
        vo.setUseEndTime("20:00");
        vo.setRemark("park车免费");
        vo.setLimitTimeSecond(3600);
        vo.setRepeatTimes(3);
        vo.setKcCount(500);
        vo.setGoodsDetail("这个描述拼团详情，是一个大的文本");

        // 这个结构转化为json存储
        List<Map> includeGoods = new ArrayList<>();
        // 包含
        Map type = new HashMap();
        type.put("name", "包含");
        List<Map> goodsList = new ArrayList<>();
        Map goods = new HashMap();
        goods.put("id", "8812fcf45485526cedd28b9766c67c56");
        goods.put("num", "2");
        goodsList.add(goods);
        type.put("goodsList", goods);
        includeGoods.add(type);
        // 三选一
        Map type2 = new HashMap();
        type2.put("name", "二选一");
        List<Map> goodsList2 = new ArrayList<>();
        Map goods2 = new HashMap();
        goods2.put("id", "a1ac4181fa6e3e319ac8af0aee4ec8cb");
        goods2.put("num", "1");
        Map goods3 = new HashMap();
        goods3.put("id", "a702b6d2e0951ed8893832df3ebb81fe");
        goods3.put("num", "1");
        goodsList2.add(goods2);
        goodsList2.add(goods3);
        type2.put("goodsList", goodsList2);
        includeGoods.add(type2);
        vo.setIncludeGoods(includeGoods);

        boolean add = groupBuyServiceApi.add(vo, "shopId");
        Assert.assertTrue(add);
    }


    @Test
    public void edit() {
        GroupBuyVo vo = new GroupBuyVo();
        vo.setId("afacfc87de8de92bb78ec3e5ba9530e3");
        vo.setName("修改名字");
        boolean edit = groupBuyServiceApi.edit(vo);
        Assert.assertTrue(edit);
    }

    @Test
    public void delete(){
        boolean delete = groupBuyServiceApi.delete("f6744a7144b3b611b07f8384d28f3f2b");
        Assert.assertTrue(delete);
    }


}