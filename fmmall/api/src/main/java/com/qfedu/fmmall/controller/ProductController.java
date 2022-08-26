package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.dao.ProductCommentsMapper;
import com.qfedu.fmmall.service.ProductCommentService;
import com.qfedu.fmmall.service.ProductService;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/product")
@Api(value = "提供商品信息相关的接口", tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentService productCommentService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detail-info/{pid}")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid){
        return  productService.getProductBasicInfo(pid);
    }

    @ApiOperation("商品参数信息查询接口")
    @GetMapping("/detail-params/{pid}")
    public ResultVO getProductParams(@PathVariable("pid") String pid){
        return  productService.getProductProductParams(pid);
    }

    @ApiOperation("商品评论信息查询接口")
    @GetMapping("/detail-comments/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "页码",required = true)
    })
    public ResultVO getProductComment(@PathVariable("pid") String pid,int limit,int pageNum){
        return  productCommentService.listCommentByProductId(pid,limit,pageNum);
    }

    @ApiOperation("商品评价统计查询接口")
    @GetMapping("/detail-commentscount/{pid}")
    public ResultVO getProductCommentCount(@PathVariable("pid") String pid){
        return  productCommentService.getCommentsCount(pid);
    }

    @ApiOperation("根据类别查询接口")
    @GetMapping("/listbycid/{cid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "页码",required = true)
    })
    public ResultVO getProductByCategoryId(@PathVariable("cid") int cid,int limit,int pageNum){
        return  productService.getProductByCategoryId(cid,pageNum,limit);
    }

    @ApiOperation("根据类别id查询品牌接口")
    @GetMapping("/listbrands/{cid}")
    public ResultVO getBrandsByCategoryId(@PathVariable("cid") int cid){
        return  productService.selectBrandByCategoryId(cid);
    }

    @ApiOperation("根据关键字模糊查询接口")
    @GetMapping("/searchbykeyword")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "keyword",value = "关键字",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "条数",required = true)
    })
    public ResultVO getProductByKeyWord(String keyword,int pageNum,int limit){
        return  productService.searchProduct(keyword,pageNum,limit);
    }

    @ApiOperation("根据关键字查询品牌接口")
    @GetMapping("/listbrandsbykeyword")
    @ApiImplicitParam(dataType = "String",name = "keyword",value = "关键字",required = true)
    public ResultVO getBrandsByKeyWord(String keyword){
        return  productService.selectBrandByKeyWord(keyword);
    }
}
