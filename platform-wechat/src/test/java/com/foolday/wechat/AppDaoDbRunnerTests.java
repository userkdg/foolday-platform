package com.foolday.wechat;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.dao.test.TestEntity;
import com.foolday.dao.test.TestMapper;
import com.foolday.service.api.TestServiceApi;
import com.foolday.service.config.WechatProperties;
import com.foolday.serviceweb.dto.TestServiceWebDto;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * web端的测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformWechatApplication.class, properties = {"application.yml"})
@Controller
public class AppDaoDbRunnerTests {
    @Resource
    private WechatProperties wechatProperties;
    @Resource
    private WxMpService wxMpService;

    /**
     * 测试模板消息
     *
     * @throws WxErrorException
     */
    @Test
    public void message() throws WxErrorException {
        WxMpTemplateMessage orderPaySuccessTemplate = WxMpTemplateMessage.builder().build();
        orderPaySuccessTemplate.setToUser(wechatProperties.getMyOpenId());
        // 在公共平台定义的模板的id
        orderPaySuccessTemplate.setTemplateId("4giGhXGRGb_Ex79lGhbmPhkjvew4aQPRCwGWQt8G2_A");
        orderPaySuccessTemplate.setUrl(" http://s2wta3.natappfree.cc/wetchat/message/notifyOrderStatusUpdateTemplate");
        WxMpTemplateData firstData = new WxMpTemplateData("first", "订单支付成功");
        WxMpTemplateData orderMoneySumData = new WxMpTemplateData("orderMoneySum", "0.01");
        WxMpTemplateData orderProductNameData = new WxMpTemplateData("orderProductName", "雪碧");
        WxMpTemplateData remarkData = new WxMpTemplateData("Remark", "无备注");
        orderPaySuccessTemplate.addData(firstData)
                .addData(orderMoneySumData)
                .addData(orderProductNameData)
                .addData(remarkData);
        wxMpService.getTemplateMsgService().sendTemplateMsg(orderPaySuccessTemplate);
    }


    @Autowired
    TestMapper testMapper;

//    @Autowired
//    private WechatProperties wechatProperties;

    @Test
    public void t() {
        System.out.println(wechatProperties);
    }

    @Test
    public void testDto() {
        TestServiceWebDto testServiceWebDto = new TestServiceWebDto();
        System.out.println(testServiceWebDto);
    }

    @Test
    public void test() {
        List<TestEntity> testEntity = testMapper.selectList(null);
        System.out.println(testEntity);

    }

    @Autowired
    private TestServiceApi testService;

    @GetMapping("/test")
    public void test2() {
        testService.test();
    }

    @Test
    public void add() {
        // 使用plus抽象的不用加id可以
        TestEntity testEntity = new TestEntity();
        testEntity.setName("b");
        testEntity.setUpperCaseName("b");
        testMapper.insert(testEntity);
        // 已测试 自己编写的插入 不写id会报错  Field 'id' doesn't have a default value（由于id的策略为非自增id）
//        int add = testMapper.add("B", "B");
        // 对已存在id=0的数据测试是否为更新数据 测试结果为： Duplicate entry '0' for key 'PRIMARY'
//        int add = testMapper.add(0, "B", "B");
    }

    @Test
    public void selectPage() {
        IPage<TestEntity> testEntityIPage = testMapper.selectPage(new Page<>(0, 1), null);
        List<TestEntity> records = testEntityIPage.getRecords();
        System.out.println(records);
    }

    @Test
    public void select() {
        //        不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity)
        Integer integer = testMapper.selectCount(Wrappers.lambdaQuery(new TestEntity()));
        Integer integer2 = testMapper.selectCount(null);
        System.out.println(integer);
    }

    @Autowired
    DataSource dataSource;

    @Resource(name = "queryRunner")
    QueryRunner queryRunner;

    @Autowired
    AsyncQueryRunner asyncQueryRunner;

    /**
     * test datasource success
     *
     * @throws SQLException
     */
    @Test
    public void contextLoads() throws SQLException, ExecutionException, InterruptedException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        QueryRunner runner = new QueryRunner(dataSource);
        Map<Object, String> select_date = runner.query("select 1 ", new BeanMapHandler<>(String.class));
        System.out.println(select_date);
        Map<Object, String> select_date1 = queryRunner.query("select 1 ", new BeanMapHandler<>(String.class));
        System.out.println(select_date1);
        Future<Map<Object, String>> query = asyncQueryRunner.query("select 1 ", new BeanMapHandler<>(String.class));
        Object o = query.get();
    }


}
