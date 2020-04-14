package com.gyj.yingx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoDetailVo {

//    id视频id
    private String id;
//    videoTitle视频标题
    private String videoTitle;
//    cover视频封面
    private String cover;
//    path视频路径
    private String path;
//    uploadTime视频上传时间
    private Date uploadTime;
//    description视频描述
    private String description;
//    likeCount视频点赞数
    private String likeCount;
//    cateName所属类别名
    private String cateName;
//    categoryId所属类别id
    private String categoryId;
//    userId所属用户id
    private String userId;
//    userPicImg用户头像
    private String userPicImg;
//    userName用户名
    private String userName;
//    playCount播放次数
    private String playCount;
//    isAttention是否关注该用户true  已关注\|false 未关注
    private String isAttention;
//    videoList视频集合
    private List<VideoDetailVo> videoList;
}
