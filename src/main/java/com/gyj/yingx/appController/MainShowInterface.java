package com.gyj.yingx.appController;

import com.gyj.yingx.common.CommonResult;
import com.gyj.yingx.po.VideoPo;
import com.gyj.yingx.service.VideoService;
import com.gyj.yingx.vo.VideoDetailVo;
import com.gyj.yingx.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//springboot解决java后台跨域方式
//@CrossOrigin

@RestController
@RequestMapping("app")
public class MainShowInterface {

    @Autowired
    private VideoService videoService;

    /*
     * 首页查询视频信息接口
     * */
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime() {
        /*
        * {对象
            "data": [集合
                {对象
                    "id": "a11282389568441fa166ebedef03e530",
                    "videoTitle": "人民的名义",
                    "cover": "http://q40vnlbog.bkt.clouddn.com/1578650041065_人民的名义.jpg",
                    "path": "http://q3th1ypw9.bkt.clouddn.com/1578650041065_人民的名义.mp4",
                    "uploadTime": "2020-01-30",
                    "description": "人民的名义",
                    "likeCount": 0,
                    "cateName": "Java",
                    "userPhoto":用户头像
                },
            ],
            "message": "查询成功",
            "status": "100"
        }
        * */
        try {
            //查询数据
            List<VideoVo> videoVos = videoService.queryByReleaseTime();
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     * 根据用户输入进行查询
     * */
    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){

        try {
            //查询数据
            List<VideoVo> videoVos = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    @RequestMapping("save")
    public CommonResult save(String description, MultipartFile videoFile,String videoTitle,String categoryId,String userId){
        return videoService.save(description, videoFile, videoTitle, categoryId, userId);
    }

    @RequestMapping("queryByVideoDetail")
    public CommonResult queryByVideoDetail(String videoId,String cateId,String userId){

        VideoDetailVo videoDetailVo = videoService.queryByVideoDetail(videoId, cateId, userId);

        if(videoDetailVo!=null){
            return new CommonResult().success("100","查询成功",videoDetailVo);
        }else{
            return new CommonResult().failed("104","查询失败",null);
        }
    }


}
