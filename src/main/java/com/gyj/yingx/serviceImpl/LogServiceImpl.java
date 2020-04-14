package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.dao.LogDao;
import com.gyj.yingx.dao.LogMapper;
import com.gyj.yingx.entity.Log;
import com.gyj.yingx.entity.LogExample;
import com.gyj.yingx.entity.User;
import com.gyj.yingx.entity.UserExample;
import com.gyj.yingx.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Autowired
    private LogMapper logMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        LogExample logExample = new LogExample();
        Integer records = logDao.countByExample(logExample);
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
        List<Log> logs = logMapper.selectByRowBounds(new Log(), rowBounds);
        map.put("rows", logs);

        return map;
    }

    @Override
    public void deleteLogById(String id) {
        logDao.deleteByPrimaryKey(id);
    }
}
