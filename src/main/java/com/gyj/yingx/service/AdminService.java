package com.gyj.yingx.service;

import com.gyj.yingx.entity.Admin;

import java.util.HashMap;
import java.util.List;

public interface AdminService {

    HashMap<String, Object> login(Admin admin);

    List<Admin> queryAll();

    Admin queryAdminById(String id);

    void deleteAdminById(String id);

    void updateAdminById(Admin admin);

    void insertAddmin(Admin admin);
}
