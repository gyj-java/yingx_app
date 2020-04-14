package com.gyj.yingx.dao;

import com.gyj.yingx.entity.Video;
import com.gyj.yingx.po.VideoPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {

    List<VideoPo> queryByReleaseTime();

    List<VideoPo> queryByLikeVideoName(String content);

    VideoPo queryByVideoDetail(@Param("videoId") String videoId,@Param("cateId") String cateId,@Param("userId") String userId);

    List<VideoPo> queryByCategory(String categoryId);
}
