package com.gyj.yingx.controller;

import com.gyj.yingx.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping("showAllLog")
    public HashMap<String,Object> showAlllog(Integer page, Integer rows){
        HashMap<String, Object> map = null;
        map = logService.queryByPage(page, rows);

        return map;
    }

}
