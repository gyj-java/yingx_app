package com.gyj.yingx.service;

import com.gyj.yingx.common.CommonResult;
import com.gyj.yingx.entity.Video;
import com.gyj.yingx.po.VideoPo;
import com.gyj.yingx.vo.VideoDetailVo;
import com.gyj.yingx.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {

    /**
     * 后台管理员
     * */
//分页展示所有视频
    HashMap<String, Object> queryByPage(Integer page, Integer rows);

//视频上传至本地
    void videoUplaodToLocal(MultipartFile videoPath, String id, HttpServletRequest request);

//视频上传至阿里云
    void videoUplaodToRemote(MultipartFile videoPath, String id);

//添加视频
    String insertVideo(Video video);

//修改视频信息
    String updateVideo(Video video);

//删除视频（远程
    HashMap<String, Object> deleteVideoById(String id);


    /**
     * 前台用户使用
     * */
//根据发布时间排序展示所有视频
    List<VideoVo> queryByReleaseTime();

//根据用户输入视频名字查询
    List<VideoVo> queryByLikeVideoName(String content);

//用户上传视频
    CommonResult save(String description, MultipartFile videoFile,String videoTitle,String categoryId,String userId);

    VideoDetailVo queryByVideoDetail(String videoId, String cateId, String userId);

}
