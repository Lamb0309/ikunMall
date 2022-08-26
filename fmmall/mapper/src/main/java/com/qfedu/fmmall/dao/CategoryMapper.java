package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.Category;
import com.qfedu.fmmall.entity.CategoryVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryMapper extends GeneralDAO<Category> {
    public List<CategoryVO> selectAllCategories();

    //子查询
    public List<CategoryVO> selectAllCategories2(int id);

    //查询一级类别
    public List<CategoryVO> selectFirstLevelCategories();
}