package com.gyj.yingx.controller;

import com.gyj.yingx.entity.Group;
import com.gyj.yingx.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 向修改视频返回分组的选项卡
     * */
    @RequestMapping("showAllGroup")
    public void showAllGroup(HttpServletResponse response){

        String selectLable = groupService.getSelectLable();

        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(selectLable);
        } catch (IOException e) {

        }
    }

}
