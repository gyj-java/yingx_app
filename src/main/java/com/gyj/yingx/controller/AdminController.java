package com.gyj.yingx.controller;


import com.gyj.yingx.entity.Admin;
import com.gyj.yingx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * */
    @RequestMapping("login")
    public HashMap<String, Object> login(Admin admin, HttpServletRequest request){
        HashMap<String, Object> map = adminService.login(admin);
        Object value = map.get("status");
        if(value.equals("200")){
            HttpSession session = request.getSession();
            session.setAttribute("admin",map.get("admin"));
        }
        return map;
    }
}
