package com.gyj.yingx.dao;

import com.gyj.yingx.entity.Video;
import com.gyj.yingx.entity.VideoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoDao {
    Integer countByExample(VideoExample example);

    int deleteByExample(VideoExample example);

    int deleteByPrimaryKey(String id);

    int insert(Video record);

    int insertSelective(Video record);

    List<Video> selectByExample(VideoExample example);

    Video selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByExample(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

}