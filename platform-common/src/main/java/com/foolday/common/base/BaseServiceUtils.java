package com.foolday.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.PlatformAssert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 不允许通过本接口来注入实例！！
 * 只为了给service层提取公共抽象
 * 如果不要求判断id的实体是否存在可以不实现
 * <p>
 */
@Slf4j
public final class BaseServiceUtils {
    /**
     * 通用判断所给的id是否存在对应实体
     *
     * @param modelBaseMapper
     * @param id
     * @param <Model>
     * @return
     */
    public static <Model> Model checkOneById(BaseMapper<Model> modelBaseMapper, String id) {
        PlatformAssert.isTrue(StringUtils.isNotBlank(id), "传递的标识为空，无法获取处理对象");
        Model entity = modelBaseMapper.selectById(id);
        PlatformAssert.notNull(entity, "无法获取处理对象,信息已被删除,请刷新页面");
        return entity;
    }

    /**
     * 基于实体的查询
     *
     * @param model
     * @param id
     * @param errorMsg
     * @param <Model>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <Model extends BaseEntity> Model checkOneByModelId(Class<Model> modelCls, String id, String errorMsg) {
        Model model;
        try {
            model = modelCls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PlatformException("实体实例化异常 e=>" + e);
        }
        PlatformAssert.isTrue(StringUtils.isNotBlank(id), "传递的标识为空，无法获取处理对象");
        model.setId(id);
        PlatformAssert.notNull(model, StringUtils.isNotBlank(errorMsg) ? errorMsg : "无法获取处理对象,信息已被删除,请刷新页面");
        return (Model) model.selectById();
    }

    /**
     * 通用判断所给的id是否存在对应实体
     *
     * @param modelBaseMapper
     * @param id
     * @param <Model>
     * @return
     */
    public static <Model> Model checkOneById(BaseMapper<Model> modelBaseMapper, String id, String errorMsg) {
        PlatformAssert.isTrue(StringUtils.isNotBlank(id), "传递的标识为空，无法获取处理对象");
        Model entity = modelBaseMapper.selectById(id);
        PlatformAssert.notNull(entity, StringUtils.isNotBlank(errorMsg) ? errorMsg : "无法获取处理对象,信息已被删除,请刷新页面");
        return entity;
    }

    /**
     * 本方法为通过唯一标识判断数据库中是否存在，不存在着不在往下执行，（快速失败）fast fail
     *
     * @param modelBaseMapper
     * @param id
     * @param <Model>
     * @return
     */
    public static <Model> List<Model> checkAllByIds(BaseMapper<Model> modelBaseMapper, String... id) {
        List<Model> models = Stream.of(id).map(modelBaseMapper::selectById)
                .peek(model -> {
                    if (Objects.isNull(model)) {
                        log.error("{}中存在已被删的数据信息，请刷新系统");
                    }
                })
                .filter(Objects::nonNull).collect(Collectors.toList());
        PlatformAssert.isTrue(models.size() == Arrays.stream(id).count(), "无法获取处理对象,信息已被删除,请刷新系统");
        return models;
    }

    public static String baseInsert(BaseMapper modelBaseMapper, BaseEntity model) {
        int insert = modelBaseMapper.insert(model);
        if (insert == 1) log.debug("写入成功");
        return model.getId();
    }

}
