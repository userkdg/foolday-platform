package com.foolday.admin.controller;

import com.foolday.common.util.PlatformTreeUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String doLogin() {
        return "ok";
    }

    /**
     *
     * @return
     */
    @ApiOperation("树")
    @ApiResponses(@ApiResponse(code = 200, message = "正常返回", response = List.class))
    @GetMapping("/tree")
    public List<TreeVo> getTree() {
        List<TreeVo> treeVos = new ArrayList<TreeVo>() {{
            add(TreeVo.builder().id("1").parentId("0").name("node1").build());
            add(TreeVo.builder().id("2").parentId("0").name("node2").build());
            add(TreeVo.builder().id("3").parentId("1").name("node1_1").build());
            add(TreeVo.builder().id("4").parentId("1").name("node1_2").build());
            add(TreeVo.builder().id("5").parentId("2").name("node2_1").build());
            add(TreeVo.builder().id("6").parentId("2").name("node2_2").build());
        }};
        List<TreeVo> trees = PlatformTreeUtils.createTrees(treeVos, "id", "parentId", "children");
        return trees;
    }

    @Data
    @Builder
    static class TreeVo implements Serializable {
        private static final long serialVersionUID = 1L;
        private String id;
        private String name;
        private String parentId;
        private List<TreeVo> children = Lists.newArrayList();
        private String parentName;
    }
}
