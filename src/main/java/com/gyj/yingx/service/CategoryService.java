package com.gyj.yingx.service;

import com.gyj.yingx.entity.Category;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    HashMap<String,Object> queryPrentCategoryByPage(Integer page,Integer rows);

    HashMap<String,Object> queryChildCategoryByPage(String parentId,Integer page,Integer rows);

    List<Category> getAllParentId();

    void insertCategory(Category category);

    void updateCategory(Category category);

    HashMap<String,String> deleteCategory(Category category);
}
