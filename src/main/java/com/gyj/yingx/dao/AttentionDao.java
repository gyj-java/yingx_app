package com.gyj.yingx.dao;

import com.gyj.yingx.entity.Attention;
import com.gyj.yingx.entity.AttentionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttentionDao {
    long countByExample(AttentionExample example);

    int deleteByExample(AttentionExample example);

    int deleteByPrimaryKey(String id);

    int insert(Attention record);

    int insertSelective(Attention record);

    List<Attention> selectByExample(AttentionExample example);

    Attention selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Attention record, @Param("example") AttentionExample example);

    int updateByExample(@Param("record") Attention record, @Param("example") AttentionExample example);

    int updateByPrimaryKeySelective(Attention record);

    int updateByPrimaryKey(Attention record);
}