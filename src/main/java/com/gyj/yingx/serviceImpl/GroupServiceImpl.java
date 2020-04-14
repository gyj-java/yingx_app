package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.dao.GroupDao;
import com.gyj.yingx.dao.GroupMapper;
import com.gyj.yingx.entity.Category;
import com.gyj.yingx.entity.Group;
import com.gyj.yingx.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupMapper groupMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String getSelectLable() {
        List<Group> list = groupDao.selectByExample(null);

        String data = "<select>";
        for (Group group : list) {
            data += "<option value='" + group.getId() + "'>" + group.getTitle() + "</option>";
        }
        data += "</select>";

        return data;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Group queryById(String id) {
        Group group = groupDao.selectByPrimaryKey(id);
        return group;
    }

    @Override
    public String insertGroup(Group group) {
        groupDao.insertSelective(group);
        return null;
    }

    @Override
    public void deleteGroupById(String id) {
        //删除前要先判断改分组
        //groupDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateGroupById(Group group) {

    }
}
