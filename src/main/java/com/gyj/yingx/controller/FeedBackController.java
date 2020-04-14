package com.gyj.yingx.controller;

import com.gyj.yingx.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("feedBack")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @RequestMapping("showAllFeedBack")
    public HashMap<String,Object> showAlllog(Integer page, Integer rows){
        HashMap<String, Object> map = null;
        map = feedBackService.queryByPage(page, rows);

        return map;
    }

}
