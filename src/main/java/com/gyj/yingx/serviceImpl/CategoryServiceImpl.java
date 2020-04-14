package com.gyj.yingx.serviceImpl;

import com.gyj.yingx.dao.CategoryDao;
import com.gyj.yingx.dao.CategoryMapper;
import com.gyj.yingx.entity.Category;
import com.gyj.yingx.entity.CategoryExample;
import com.gyj.yingx.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 父类级别分页查询
     * */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryPrentCategoryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo("1");
        //总条数   records
        Integer records = categoryDao.countByExample(categoryExample);
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
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);
        map.put("rows", categories);
        return map;
    }

    /**
     * 子类级别分类查询
     * */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryChildCategoryByPage(String parentId,Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(parentId);
        //总条数   records
        Integer records = categoryDao.countByExample(categoryExample);
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
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);
        map.put("rows", categories);

        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> getAllParentId() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo("1");
        List<Category> categories = categoryDao.selectByExample(categoryExample);
        return categories;
    }

    @Override
    public void insertCategory(Category category) {
        categoryDao.insertSelective(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateByPrimaryKeySelective(category);
    }

    @Override
    public HashMap<String,String> deleteCategory(Category category) {
        HashMap<String,String> map = new HashMap<>();
        Category select = categoryDao.selectByPrimaryKey(category.getId());

        if(select.getLevels().equals("1")){
            List<Category> categories = null;
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andParentIdEqualTo(select.getId());
            categories = categoryDao.selectByExample(categoryExample);

            if(categories!=null){
                map.put("message","删除失败，该类别拥有子类");
            }else{
                categoryDao.deleteByPrimaryKey(select.getId());
                map.put("message","删除成功");
            }
        }else if(select.getLevels().equals("2")){
            categoryDao.deleteByPrimaryKey(select.getId());
            map.put("message","删除成功");
        }
        return map;
    }
}
