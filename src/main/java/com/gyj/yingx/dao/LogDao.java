package com.gyj.yingx.dao;

import com.gyj.yingx.entity.Log;
import com.gyj.yingx.entity.LogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogDao {
    Integer countByExample(LogExample example);

    int deleteByExample(LogExample example);

    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExample(LogExample example);

    Log selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Log record, @Param("example") LogExample example);

    int updateByExample(@Param("record") Log record, @Param("example") LogExample example);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}