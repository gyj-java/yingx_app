package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.annotation.AddLog;
import com.gyj.yingx.common.CommonResult;
import com.gyj.yingx.dao.VideoDao;
import com.gyj.yingx.dao.VideoMapper;
import com.gyj.yingx.entity.Video;
import com.gyj.yingx.entity.VideoExample;
import com.gyj.yingx.po.VideoPo;
import com.gyj.yingx.service.VideoService;
import com.gyj.yingx.util.AliyunOssUtil;
import com.gyj.yingx.util.InterceptVideoPhotoUtil;
import com.gyj.yingx.util.UUIDUtil;
import com.gyj.yingx.vo.VideoDetailVo;
import com.gyj.yingx.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private VideoMapper videoMapper;

/**************************************后台使用**************************************************/
    /**
     * 后台分页查询展示视频
     */
    @AddLog("分页展示视频")
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        VideoExample videoExample = new VideoExample();
        Integer records = videoDao.countByExample(videoExample);
        map.put("records", records);
        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页   page
        map.put("page", page);
        //数据     rows
        //开始条数
        int start = rows * (page - 1);
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds(start, rows);
        videoExample.setOrderByClause("publish_date");
        List<Video> videos = videoMapper.selectByExampleAndRowBounds(videoExample, rowBounds);
        map.put("rows", videos);

        return map;
    }

    /**
     * 添加视频
     */
    @Override
    @AddLog("添加视频")
    public String insertVideo(Video video) {
        String uuid = UUIDUtil.getUUID();
        video.setId(uuid);
        video.setPublishDate(new Date());
        //虚拟死数据
        //分类、分组
        video.setUserId("1");
        video.setCategoryId("1");
        video.setGroupId("1");

        videoDao.insertSelective(video);
        return uuid;
    }

    /**
     * 视频上传至本地
     */
    @Override
    @AddLog("视频上传至本地")
    public void videoUplaodToLocal(MultipartFile videoPath, String id, HttpServletRequest request) {
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/videoUpload");

        //防止文件夹不存在导致上传失败，因此在不存在时创建一个
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //2获取文件名
        String filename = videoPath.getOriginalFilename();
        String newName = System.currentTimeMillis() + "-" + filename;

        //频接本地存放路径    D:\动画.jpg
        // 1585661687777-好看.mp4
        String[] names = newName.split("\\.");
        String interceptName = names[0];
        String coverName = interceptName + ".jpg";  //频接封面名字
        String coverPath = realPath + "/" + coverName;  //频接视频封面的本地绝对路径

        try {
            //3.文件上传
            videoPath.transferTo(new File(realPath, newName));

            //4.封面图片上传
            InterceptVideoPhotoUtil.fetchFrame(realPath + "/" + newName, coverPath);
            //5.图片修改
            //修改的条件
            Video video = new Video();
            video.setId(id);
            video.setVideoPath("/videoUpload/" + newName);
            video.setCoverImg("/videoUpload/" + coverName);

            //修改
            videoDao.updateByPrimaryKeySelective(video);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 视频上传至远程
     * 阿里云
     *
     * @param id        上传视频的id
     * @param videoPath 上传视频的文件流
     */
    @Override
    @AddLog("上传视频至阿里云")
    public void videoUplaodToRemote(MultipartFile videoPath, String id) {

        //获取文件名
        String filename = videoPath.getOriginalFilename();
        String newName = System.currentTimeMillis() + "-" + filename;

        /*1.视频上传至阿里云
         *上传字节数组
         * 参数：
         *   bucket:存储空间名
         *   videoPath: 指定MultipartFile类型的文件
         *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
         * */
        AliyunOssUtil.uploadFileBytes(videoPath, "video/" + newName);

        //网络视频完整路径
        String netVideoPath = "https://yingx-gyj.oss-cn-beijing.aliyuncs.com/video/" + newName;

        //频接本地存放路径    D:\动画.jpg
        // 1585661687777-好看.mp4
        String[] names = newName.split("\\.");
        String interceptName = names[0];
        String coverName = interceptName + ".jpg";  //频接封面名字

        /*2.截取视频第一帧做封面
         * 视频截取  并上传至阿里云
         * 参数：
         *   bucker: 存储空间名
         *   fileName:远程文件文件名    目录名/文件名
         *   coverName：截取的封面名
         * */
        AliyunOssUtil.videoCoverIntercept("video/" + newName, "video-screenshorts/" + coverName);
        //网络图片路径
        String netCoverImgPath = "https://yingx-gyj.oss-cn-beijing.aliyuncs.com/video-screenshorts/" + coverName;

        //5.修改视频信息
        //添加修改条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        //修改的结果
        Video video = new Video();

        video.setVideoPath(netVideoPath);
        video.setCoverImg(netCoverImgPath);

        //调用修改方法
        videoMapper.updateByExampleSelective(video, example);

    }

    /**
     * 修改video
     */
    @Override
    @AddLog("修改视频")
    public String updateVideo(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
        return null;
    }

    /**
     * 删除视频，且删除远程阿里云的数据
     */
    @Override
    @AddLog("删除视频，且删除远程阿里云的数据")
    public HashMap<String, Object> deleteVideoById(String id) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            //设置条件
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(id);

            //从数据库中查询出video的详细信息
            Video video = videoMapper.selectOneByExample(example);
            //从数据库中删除对应id的vedio
            videoDao.deleteByPrimaryKey(id);

            //字符串拆分
            String[] pathSplit = video.getVideoPath().split("/");
            String[] coverSplit = video.getCoverImg().split("/");

            //https://yingx-186.oss-cn-beijing.aliyuncs.com/video-screenshorts/"+coverName;
            String videoName = pathSplit[pathSplit.length - 2] + "/" + pathSplit[pathSplit.length - 1];
            String coverName = coverSplit[coverSplit.length - 2] + "/" + coverSplit[coverSplit.length - 1];

            System.out.println(videoName);
            System.out.println(coverName);

            /*2.删除视频
             * 删除阿里云文件
             * 参数：
             *   bucker: 存储空间名
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delete(videoName);

            /*3.删除封面
             * 删除阿里云文件
             * 参数：
             *   bucker: 存储空间名
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delete(coverName);

            map.put("status", "200");
            map.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("message", "删除失败");
        }
        return map;
    }


/********************************前台使用***********************************/
    /**
     * 前台：根据上传时间展示视频
     */
    @AddLog("前台主页展示视频")
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoVo> queryByReleaseTime() {

        List<VideoPo> videoPos = videoMapper.queryByReleaseTime();

        ArrayList<VideoVo> videoVosList = new ArrayList<>();

        //点赞数
        //遍历视频id
        for (VideoPo v : videoPos) {

            String id = v.getId();
            //根据视频id查询视频的点赞数
            Integer likeCount = 18;

            //给vo赋值
            VideoVo videoVo = new VideoVo(v.getId(), v.getVTitle(), v.getVBrief(), v.getVPath(),
                    v.getVCover(), v.getVPublishDate(), likeCount, v.getCateName(), v.getHeadImg()
            );
            videoVosList.add(videoVo);
        }

        return videoVosList;
    }

    @AddLog("前台根据用户输入用户查询视频")
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoVo> queryByLikeVideoName(String content) {

        List<VideoPo> videoPos = videoMapper.queryByLikeVideoName(content);

        ArrayList<VideoVo> videoVosList = new ArrayList<>();

        //点赞数
        //遍历视频id
        for (VideoPo v : videoPos) {

            String id = v.getId();
            //根据视频id查询视频的点赞数
            Integer likeCount = 18;

            //给vo赋值
            VideoVo videoVo = new VideoVo(v.getId(), v.getVTitle(), v.getVBrief(), v.getVPath(),
                    v.getVCover(), v.getVPublishDate(), likeCount, v.getCateName(), v.getHeadImg()
            );
            videoVosList.add(videoVo);
        }

        return videoVosList;
    }

    @Override
    public CommonResult save(String description, MultipartFile videoFile, String videoTitle, String categoryId, String userId) {

        try {
            Video video = new Video();
            video.setTitle(videoTitle);
            video.setBrief(description);
            video.setUserId(userId);
            video.setCategoryId(categoryId);

            String uuid = insertVideo(video);

            videoUplaodToRemote(videoFile, uuid);
            return new CommonResult().success("100", "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().success("104", "添加失败", null);
        }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public VideoDetailVo queryByVideoDetail(String videoId, String cateId, String userId) {
        VideoPo videoPo = videoMapper.queryByVideoDetail(videoId, cateId, userId);

        if(videoPo.getIsAttention()!=null){
            videoPo.setIsAttention("true");
        }else{
            videoPo.setIsAttention("false");
        }

//        有缓存查找
//        String likeCount;点赞数
//        String playCount;播放数
        List<VideoPo> videoPos = videoMapper.queryByCategory(cateId);

        List<VideoDetailVo> videoDetailVos = new ArrayList<>();

        for (VideoPo po : videoPos) {
            String flag = "false";
            if(po.getIsAttention()!=null){
                flag = "true";
            }
            VideoDetailVo videoDetailVo = new VideoDetailVo(po.getId(), po.getVTitle(),
                    po.getVCover(), po.getVPath(), po.getVPublishDate(), po.getVBrief(),
                    "", po.getCateName(), po.getCategoryId(), po.getUserId(),
                    po.getHeadImg(), po.getUserName(), "", flag,
                    null);
        }

        VideoDetailVo videoDetailVo = new VideoDetailVo(videoPo.getId(), videoPo.getVTitle(),
                videoPo.getVCover(), videoPo.getVPath(), videoPo.getVPublishDate(), videoPo.getVBrief(),
                "", videoPo.getCateName(), videoPo.getCategoryId(), videoPo.getUserId(),
                videoPo.getHeadImg(), videoPo.getUserName(), "", videoPo.getIsAttention(),
                videoDetailVos);


        return videoDetailVo;
    }


}
