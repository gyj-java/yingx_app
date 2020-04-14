package com.gyj.yingx.controller;

import com.gyj.yingx.entity.Video;
import com.gyj.yingx.service.VideoService;
import com.gyj.yingx.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 分页展示视频
     * */
    @RequestMapping("showVideoByPage")
    public HashMap<String, Object> showVideoByPage(Integer page, Integer rows){
        HashMap<String, Object> map = null;

        map = videoService.queryByPage(page, rows);

        return map;
    }

    /**
     * 对视频的操作
     * */
    @RequestMapping("videoEdit")
    public Object videoEdit(String oper, Video video){
        String uuid = "";

                switch (oper){
            case "add":
                uuid = videoService.insertVideo(video);
                return uuid;
            case "del":
                HashMap<String, Object> map = videoService.deleteVideoById(video.getId());
                return map;
            case "edit":
                videoService.updateVideo(video);
                break;
        }

        return null;
    }

    /**
     * 视频上传
     * */
    @RequestMapping("videoUpload")
    public String userImgUpload(MultipartFile videoPath, String id, HttpServletRequest request){
        //本地上传
        //videoService.videoUplaodToLocal(videoPath, id, request);
        //远程上传
        videoService.videoUplaodToRemote(videoPath, id);
        return "视频上传完成";
    }
}
