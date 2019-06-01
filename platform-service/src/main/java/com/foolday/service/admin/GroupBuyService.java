package com.foolday.service.admin;

import com.alibaba.fastjson.JSONArray;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.GroupBuyStatus;
import com.foolday.common.util.JodaTimeUtils;
import com.foolday.dao.groupbuy.GroupBuyEntity;
import com.foolday.dao.groupbuy.GroupBuyMapper;
import com.foolday.service.api.admin.GroupBuyServiceApi;
import com.foolday.serviceweb.dto.admin.groupbuy.GroupBuyVo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Slf4j
@Service
@Transactional
public class GroupBuyService implements GroupBuyServiceApi {
    @Resource
    private GroupBuyMapper groupBuyMapper;

    @Override
    public boolean add(GroupBuyVo vo, String shopId) {
        GroupBuyEntity entity = GroupBuyEntity.newInstance();

        // 进行vo和entity的转化
        entity.setShopId(shopId);
        entity.setName(vo.getName());
        entity.setConditionNum(vo.getConditionNum());
        entity.setCurrPrice(vo.getCurrPrice());
        entity.setOriPrice(vo.getOriPrice());
        entity.setEndTime(vo.getEndTime());
        entity.setStartTime(vo.getStartTime());
        entity.setIncludeShopIds(vo.getIncludeShopIds());
        entity.setRule(vo.getRule());
        entity.setUseEndTime(vo.getUseEndTime());
        entity.setUseStartTime(vo.getUseStartTime());
        entity.setRemark(vo.getRemark());
        entity.setStatus(GroupBuyStatus.有效);
        entity.setImgIds(vo.getImgIds());
        entity.setLimitTimeSecond(vo.getLimitTimeSecond());
        entity.setRepeatTimes(vo.getRepeatTimes());
        entity.setKcCount(vo.getKcCount());
        entity.setGoodsDetail(vo.getGoodsDetail());

        // 这个结构转化为json存储
        List<Map> includeGoods = vo.getIncludeGoods();
        String includeGoodsJson = JSONArray.toJSONString(includeGoods);
        entity.setIncludeShopIds(includeGoodsJson);

        // 设置团券码
        String ymdhms = JodaTimeUtils.time2Str(DateTime.now(), "yyyyMMddHHmmss");
        int ran=(int)(Math.random()*9000)+1000;
        String groupBuyCode = ymdhms + ran;
        entity.setGroupBuyCode(groupBuyCode);
        // 设置核销码
        entity.setHxCode(groupBuyCode);

        int ret = groupBuyMapper.insert(entity);
        return ret > 0;
    }

    @Override
    public boolean edit(GroupBuyVo vo) {
        GroupBuyEntity entity = new GroupBuyEntity();
        BeanUtils.copyProperties(vo, entity);
        int i = groupBuyMapper.updateById(entity);
        return i > 0;
    }

    @Override
    public boolean delete(String... ids) {
        List<GroupBuyEntity> shopEntities = BaseServiceUtils.checkAllByIds(groupBuyMapper, ids);
        long count = shopEntities.stream().map(entity -> {
            return groupBuyMapper.deleteById(entity.getId());
        }).count();

        log.info("删除拼团定义表：{}个, id为{}", count, ids);
        return count > 0;
    }
}
