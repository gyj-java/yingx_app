package com.gyj.yingx.service;

import com.gyj.yingx.common.CommonResult;
import com.gyj.yingx.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UserService {

/*********************后台管理员操作用户*********************************************/
    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    void userImgUplaod(MultipartFile headImg, String id, HttpServletRequest request);

    void changUserStatus(User user);

    List<User> queryAllUser();

    User queryById(String id);

    String insertUser(User user);

    void deleteUserById(String id);

    void updateUserById(User user);

/*******************************前台用户操作***************************************************/

    User queryUserByPhoneNUmber(String phone);
}
