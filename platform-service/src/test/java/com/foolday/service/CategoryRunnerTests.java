package com.foolday.service;

import com.foolday.common.enums.CommonStatus;
import com.foolday.common.enums.TopDownStatus;
import com.foolday.dao.category.GoodsCategoryEntity;
import com.foolday.service.api.admin.GoodsCategoryServiceApi;
import com.foolday.serviceweb.dto.admin.category.GoodsCategoryVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * service 层服务测试
 * 设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class CategoryRunnerTests {
    @Resource
    private GoodsCategoryServiceApi categoryServiceApi;

    @Test
    public void category() {
        GoodsCategoryEntity categoryEntity = categoryServiceApi.
                newGoodsCategory(GoodsCategoryVo.builder().name("热门推荐").status(CommonStatus.有效)
                        .topDownStatus(TopDownStatus.置顶).build());
        System.out.println("new add " + categoryEntity);
        GoodsCategoryEntity categoryEntity2 = categoryServiceApi.
                newGoodsCategory(GoodsCategoryVo.builder().name("热门推荐2").status(CommonStatus.有效)
                        .topDownStatus(TopDownStatus.置底).build());
        System.out.println("new add2 " + categoryEntity2);

        List<GoodsCategoryEntity> categoryEntities = categoryServiceApi.listOrderCategory();
        System.out.println("获取列表2" + categoryEntities);
        boolean b = categoryServiceApi.updateStatus(CommonStatus.无效, categoryEntity.getId());
        System.out.println("修改状态" + b);

        boolean en2 = categoryServiceApi.editGoodsCategory(GoodsCategoryVo.builder().name("今日热推").topDownStatus(TopDownStatus.置顶).build(), categoryEntity2.getId());
        System.out.println("修改" + categoryEntity2.getId() + en2);

        List<GoodsCategoryEntity> categoryEntities2 = categoryServiceApi.listOrderCategory();
        System.out.println("获取列表" + categoryEntities2);

    }

    @Test
    public void list() {
        List<GoodsCategoryEntity> categoryEntities = categoryServiceApi.listOrderCategory();
        System.out.println(categoryEntities);
    }
}
