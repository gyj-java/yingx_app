package com.gyj.yingx.service;

import java.util.HashMap;

public interface LogService {

    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    void deleteLogById(String id);
}
