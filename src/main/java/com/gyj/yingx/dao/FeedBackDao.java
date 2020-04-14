package com.gyj.yingx.dao;

import com.gyj.yingx.entity.FeedBack;
import com.gyj.yingx.entity.FeedBackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeedBackDao {
    Integer countByExample(FeedBackExample example);

    int deleteByExample(FeedBackExample example);

    int deleteByPrimaryKey(String id);

    int insert(FeedBack record);

    int insertSelective(FeedBack record);

    List<FeedBack> selectByExample(FeedBackExample example);

    FeedBack selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FeedBack record, @Param("example") FeedBackExample example);

    int updateByExample(@Param("record") FeedBack record, @Param("example") FeedBackExample example);

    int updateByPrimaryKeySelective(FeedBack record);

    int updateByPrimaryKey(FeedBack record);
}