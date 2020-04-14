package com.gyj.yingx.controller;


import com.gyj.yingx.entity.Category;
import com.gyj.yingx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页展示一级类别
     * */
    @RequestMapping("showPrentCategoryByPage")
    public HashMap<String,Object> showPrentCategoryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = null;


        map = categoryService.queryPrentCategoryByPage(page, rows);

        return map;
    }

    /**
     * 分页展示二级类别
     * */
    @RequestMapping("showChildCategoryByPage")
    public HashMap<String,Object> showChildCategoryByPage(String parentId,Integer page,Integer rows){
        HashMap<String, Object> map = null;


        map = categoryService.queryChildCategoryByPage(parentId,page, rows);

        return map;
    }

    /**
     * 对分类进行操作
     * */
    @RequestMapping("categoryEdit")
    public HashMap<String, String> categoryEdit(String oper,Category category){
        HashMap<String, String> map = null;
        switch (oper){
            case "add":
                categoryService.insertCategory(category);
                break;
            case "del":
                map = categoryService.deleteCategory(category);
                break;
            case "edit":
                categoryService.updateCategory(category);
                break;
        }
        return map;
    }

    /**
     * 获取所有父类级别的信息
     * */
    @RequestMapping("getParentCategory")
    public void getParentCategory(HttpServletResponse response){
        List<Category> parentIds = categoryService.getAllParentId();
        String data = "<select>";
        for (Category parentId : parentIds) {
            data += "<option value='"+parentId.getId()+"'>"+parentId.getCateName()+"</option>";
        }
        data += "</select>";
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(data);
        } catch (IOException e) {

        }

    }

}
