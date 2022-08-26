package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.dao.ProductMapper;
import com.qfedu.fmmall.entity.ProductVO;
import com.qfedu.fmmall.service.CategoryService;
import com.qfedu.fmmall.service.IndexService;
import com.qfedu.fmmall.service.ProductService;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/index")
@Api(value = "提供首页数据显示所需的接口", tags = "首页管理")
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @ApiOperation(value = "首页轮播图接口")
    @GetMapping("/indeximg")
    public ResultVO listIndexImgs(){
        return indexService.listIndexImgs();
    }


    @GetMapping("/category-list")
    @ApiOperation(value = "查询商品分类接口")
    public ResultVO listCategory(){
        return categoryService.listCategories();
    }

    @GetMapping("/list-recommend")
    @ApiOperation(value = "查询新品推荐商品接口")
    public ResultVO listCommendProducts(){
        return productService.listRecommendProducts();
    }

    @GetMapping("/category-recommend")
    @ApiOperation(value = "查询分类推荐商品接口")
    public ResultVO listCommendProductsByCategoty(){
        return categoryService.listFirstCategories();
    }
}
