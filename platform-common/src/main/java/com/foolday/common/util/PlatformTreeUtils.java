package com.foolday.common.util;


import com.foolday.common.exception.PlatformException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
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

        Object parentId;
        E parent;
        for (E data : datas) {
            try {
                parentId = PropertyUtils.getProperty(data, idProperty);
            } catch (Exception var13) {
                logger.error("无法读取id属性", var13);
                throw new PlatformException();
            }

            try {
                parent = (E) BeanUtils.cloneBean(data);
                dataMap.put(parentId, parent);
            } catch (Exception var12) {
                logger.error("无法复制数据", var12);
                throw new PlatformException();
            }
        }
        try {
            for (E data : dataMap.values()) {
                parentId = PropertyUtils.getProperty(data, parentIdProperty);
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

    public static void main(String[] args) {
        List<TreeVo> treeVos = new ArrayList<TreeVo>() {{
            add(new TreeVo("1", "0", "node1", null, null));
            add(new TreeVo("2", "0", "node2", null, null));
            add(new TreeVo("3", "1", "node2", null, null));
            add(new TreeVo("4", "2", "node2", null, null));
        }};
        List<TreeVo> trees = PlatformTreeUtils.createTrees(treeVos, "id", "parentId", "children");
    }

}
class TreeVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String parentId;
    private List<TreeVo> children = Lists.newArrayList();
    private String parentName;

    public TreeVo() {
    }

    public TreeVo(String id, String parentId, String name, List<TreeVo> children, String parentName) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = children;
        this.parentName = parentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}