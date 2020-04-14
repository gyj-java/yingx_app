package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.dao.AdminDao;
import com.gyj.yingx.entity.Admin;
import com.gyj.yingx.entity.AdminExample;
import com.gyj.yingx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public HashMap<String, Object> login(Admin admin) {
        HashMap<String, Object> map = new HashMap<>();
        //模板查询
        AdminExample ad = new AdminExample();
        ad.createCriteria().andUsernameEqualTo(admin.getUsername());

        List<Admin> admins = adminDao.selectByExample(ad);
        try {
            Admin result = admins.get(0);
            if(admin.getPassword().equals(result.getPassword())){
                //登陆成功
                map.put("status","200");
                map.put("message","登陆成功");
                map.put("admin",result);
            }else{
                //密码错误
                map.put("status","400");
                map.put("message","密码错误");
            }
        } catch (NullPointerException e) {
            //用户不存在
            map.put("status","400");
            map.put("message","用户不存在");
        }
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Admin> queryAll() {
        List<Admin> admins = adminDao.selectByExample(null);
        return admins;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Admin queryAdminById(String id) {
        Admin admin = adminDao.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public void deleteAdminById(String id) {
        int i = adminDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateAdminById(Admin admin) {
        int i = adminDao.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void insertAddmin(Admin admin) {
        int insert = adminDao.insert(admin);
    }
}
