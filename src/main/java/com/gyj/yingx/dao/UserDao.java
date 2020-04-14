package com.gyj.yingx.dao;

import com.gyj.yingx.entity.User;
import com.gyj.yingx.entity.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {
    Integer countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}