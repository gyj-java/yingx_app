package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.annotation.AddLog;
import com.gyj.yingx.dao.FeedBackDao;
import com.gyj.yingx.dao.FeedBackMapper;
import com.gyj.yingx.entity.FeedBack;
import com.gyj.yingx.entity.FeedBackExample;
import com.gyj.yingx.entity.Log;
import com.gyj.yingx.entity.LogExample;
import com.gyj.yingx.service.FeedBackService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackDao feedBackDao;

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @AddLog("查看反馈信息")
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        FeedBackExample feedBackExample = new FeedBackExample();
        Integer records = feedBackDao.countByExample(feedBackExample);
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
        List<FeedBack> feedBacks = feedBackMapper.selectByRowBounds(new FeedBack(), rowBounds);
        map.put("rows", feedBacks);

        return map;
    }

    @AddLog("删除反馈信息")
    @Override
    public void deleteFeedBackById(String id) {
        feedBackDao.deleteByPrimaryKey(id);
    }
}
