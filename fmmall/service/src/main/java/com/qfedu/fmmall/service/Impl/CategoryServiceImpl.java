package com.qfedu.fmmall.service.Impl;

import com.qfedu.fmmall.dao.CategoryMapper;
import com.qfedu.fmmall.entity.CategoryVO;
import com.qfedu.fmmall.service.CategoryService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    //查询三个级别的分类
    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories();
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", categoryVOS);
        return resultVO;
    }
    //查询一级分类，同时查询一级分类下销量最高的6个商品
    @Override
    public ResultVO listFirstCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", categoryVOS);
        return resultVO;
    }
}
