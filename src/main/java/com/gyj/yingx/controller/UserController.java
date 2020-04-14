package com.gyj.yingx.controller;

import com.gyj.yingx.entity.User;
import com.gyj.yingx.service.UserService;
import com.gyj.yingx.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页展示用户数据
     * */
    @RequestMapping("showAllUser")
    public HashMap<String,Object> showAllUser(Integer page,Integer rows){
        HashMap<String, Object> map = null;
        map = userService.queryByPage(page, rows);

        return map;
    }

    /**
     * 操作用户信息
     * */
    @RequestMapping("editUser")
    public String editUser(String oper, User user){
        String uuid ="";
        switch (oper){
            case "add":

                uuid = userService.insertUser(user);
                break;
            case "del":
                userService.deleteUserById(user.getId());
                break;
            case "edit":
                userService.updateUserById(user);
                break;
        }

        return uuid;
    }

    /**
     * 用户上传头像
     * */
    @RequestMapping("userImgUpload")
    public String userImgUpload(MultipartFile headImg, String id, HttpServletRequest request){
        userService.userImgUplaod(headImg, id, request);
        return "图片上传完成";
    }


    @RequestMapping("changUserStatus")
    public String changUserStatus(User user){
        userService.changUserStatus(user);

        return "状态改变完成";
    }



}
