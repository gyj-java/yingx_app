package com.gyj.yingx.service;

import java.util.HashMap;

public interface FeedBackService {
    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    void deleteFeedBackById(String id);
}
