package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.dao.UserDao;
import com.gyj.yingx.dao.UserMapper;
import com.gyj.yingx.entity.User;
import com.gyj.yingx.entity.UserExample;
import com.gyj.yingx.service.UserService;
import com.gyj.yingx.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;

/**************************************后台用户管理*****************************************************/
    /**
     * 分页展示用户数据
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        UserExample example = new UserExample();
        Integer records = userDao.countByExample(example);
        map.put("records", records);
        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页   page
        map.put("page", page);
        //数据     rows
        //开始条数
        int start = rows * (page - 1);
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds(start, rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows", users);

        return map;
    }

    //用户头像上传
    @Override
    public void userImgUplaod(MultipartFile headImg, String id, HttpServletRequest request) {
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/fileUplod");

        //防止文件夹不存在导致上传失败，因此在不存在时创建一个
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //2获取文件名
        String filename = headImg.getOriginalFilename();
        String newName = System.currentTimeMillis() + "-" + filename;

        try {
            //3.文件上传
            headImg.transferTo(new File(realPath, newName));

            //4.图片修改
            //修改的条件
            User user = new User();
            user.setId(id);
            user.setHeadImg("/fileUplod/" + newName); //设置修改的结果

            //修改
            userDao.updateByPrimaryKeySelective(user);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    //用户状态改变
    @Override
    public void changUserStatus(User user) {
        if (user.getStatus().equals("0")) {
            user.setStatus("1");
        } else {
            user.setStatus("0");
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    //查询所有用户
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> queryAllUser() {
        List<User> users = userDao.selectByExample(null);
        return users;
    }

    //根据id查询用户
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryById(String id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    //添加用户
    @Override
    public String insertUser(User user) {
        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        int insert = userDao.insert(user);
        return uuid;
    }

    //删除用户
    @Override
    public void deleteUserById(String id) {
        int i = userDao.deleteByPrimaryKey(id);
    }

    //修改用户信息
    @Override
    public void updateUserById(User user) {
        if (user.getHeadImg() == "") {
            user.setHeadImg(null);
        }

        int i = userDao.updateByPrimaryKeySelective(user);
    }


    /*******************************前台用户操作***************************************************/
//  根据电话号码查询用户存在
    @Override
    public User queryUserByPhoneNUmber(String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhonenumberEqualTo(phone);
        List<User> users = userDao.selectByExample(userExample);
        try {
            User user = users.get(0);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//短信验证发送


}
