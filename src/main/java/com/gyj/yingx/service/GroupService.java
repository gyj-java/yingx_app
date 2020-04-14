package com.gyj.yingx.service;

import com.gyj.yingx.entity.Group;

import java.util.HashMap;
import java.util.List;

public interface GroupService {

    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    String getSelectLable();

    Group queryById(String id);

    String insertGroup(Group group);

    void deleteGroupById(String id);

    void updateGroupById(Group group);

}
