package com.foolday.common.util;


import com.foolday.common.exception.PlatformException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlatformTreeUtils {
    static final Logger logger = LoggerFactory.getLogger(PlatformTreeUtils.class);

    public PlatformTreeUtils() {
    }


    public static <E> List<E> createTrees(List<E> datas, String idProperty, String parentIdProperty, String childrenProperty) {
        PlatformAssert.notNull(idProperty);
        PlatformAssert.notNull(parentIdProperty);
        PlatformAssert.notNull(childrenProperty);
        Map<Object, E> dataMap = Maps.newLinkedHashMap();
        List<E> retList = Lists.newArrayList();

        String parentId;
        E parent;
        for (E data : datas) {
            try {
                parentId = ObjectUtils.toString(PropertyUtils.getProperty(data, idProperty));
            } catch (Exception var13) {
                logger.error("无法读取id属性", var13);
                throw new PlatformException("无法读取id属性,e=>" + ExceptionUtils.getMessage(var13));
            }

            try {
                parent = (E) BeanUtils.cloneBean(data);
                dataMap.put(parentId, parent);
            } catch (Exception var12) {
                logger.error("无法复制数据", var12);
                throw new PlatformException("无法复制数据,e=>" + ExceptionUtils.getMessage(var12));
            }
        }
        try {
            for (E data : dataMap.values()) {
                parentId = ObjectUtils.toString(PropertyUtils.getProperty(data, parentIdProperty));
                if (parentId != null && dataMap.containsKey(parentId)) {
                    parent = dataMap.get(parentId);
                    Object children = PropertyUtils.getProperty(parent, childrenProperty);
                    if (children == null) {
                        children = new ArrayList();
                        PropertyUtils.setProperty(parent, childrenProperty, children);
                    }

                    if (!(children instanceof List)) {
                        throw new ClassCastException("属性" + childrenProperty + "不是有效的子级数据属性");
                    }

                    List<E> list = (List) children;
                    list.add(data);
                } else {
                    retList.add(data);
                }
            }
        } catch (Exception e) {
            logger.error("父节点Id或则子节点列表属性字段异常", e);
            throw new PlatformException("树节点配置异常");
        }
        return retList;
    }

}
