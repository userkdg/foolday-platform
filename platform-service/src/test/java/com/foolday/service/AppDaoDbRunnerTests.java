package com.foolday.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.base.RedisBeanNameApi;
import com.foolday.common.enums.GoodsStatus;
import com.foolday.common.enums.TagType;
import com.foolday.common.util.KeyUtils;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.dao.tags.TagsEntity;
import com.foolday.dao.tags.TagsMapper;
import com.foolday.dao.test.TestEntity;
import com.foolday.dao.test.TestMapper;
import com.foolday.service.api.TestServiceApi;
import com.foolday.serviceweb.dto.TestServiceWebDto;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.foolday.common.util.UuidUtils.uuid32;
import static com.foolday.service.common.SpringContextUtils.getBean;

/**
 * service 层服务测试
 * 设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class AppDaoDbRunnerTests {

    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_S_S)
    RedisTemplate redisTemplate;

    @Test
    public void getYaml() {

        RedisTemplate bean = (RedisTemplate) getBean(RedisBeanNameApi.REDIS_TEMPLATE_S_S);
        System.out.println();

        String l = KeyUtils.generateOrderNoOfDay(redisTemplate);
        System.out.println(l);
        l = KeyUtils.generateOrderNoOfDay(redisTemplate);
        System.out.println(l);
        l = KeyUtils.generateOrderNoOfDay(redisTemplate);
        System.out.println(l);
        l = KeyUtils.generateOrderNoOfDay(redisTemplate);
        System.out.println(l);
        l = KeyUtils.generateOrderNoOfDay(redisTemplate);
        System.out.println(l);
        l = KeyUtils.generateOrderNoOfDay(redisTemplate);
        System.out.println(l);

    }

    @Resource
    TagsMapper tagsMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    private TestServiceApi testServiceApi;

    @Test
    public void goods() {
        testServiceApi.test();
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setName("雪碧");
        goodsEntity.setShopId(uuid32());
        goodsEntity.setStatus(GoodsStatus.上架);
        goodsEntity.setDescription("雪的口感");
        goodsEntity.setPrice(5.0F);
        goodsEntity.setDiscntPrice(4.0F);
        goodsEntity.setKccnt(100);
        goodsEntity.setImgId(uuid32());
        goodsEntity.setCreateTime(LocalDateTime.now());
        goodsMapper.insert(goodsEntity);
        GoodsEntity goodsEntity1 = goodsMapper.selectById(goodsEntity.getId());

        System.out.println(goodsEntity);
        goodsEntity.setStatus(GoodsStatus.下架);
        goodsMapper.updateById(goodsEntity);
    }

    @Test
    public void tags() {
        TagsEntity tagsEntity = new TagsEntity();
        tagsEntity.setName("热门推荐");
        tagsEntity.setType(TagType.GOODS);
        tagsEntity.setCreateTime(LocalDateTime.now());
        System.out.println(tagsEntity);
        tagsMapper.insert(tagsEntity);
        System.out.println(tagsEntity);
    }

    /**
     * test spring-data-redis
     */
    @Test
    public void redis() {
        List clientList = redisTemplate.getClientList();
        Long add = redisTemplate.opsForSet().add(1, 1);

    }

    @Test
    public void testDto() {
        TestServiceWebDto testServiceWebDto = new TestServiceWebDto();
        System.out.println(testServiceWebDto);
    }

    @Autowired
    TestMapper testMapper;

    @Test
    public void test() {
        TestEntity testEntity = testMapper.selectOne(null);
        System.out.println(testEntity);

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
