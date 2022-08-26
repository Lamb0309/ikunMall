package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {
    public ResultVO listRecommendProducts();

    public ResultVO getProductBasicInfo(String productId);

    public ResultVO getProductProductParams(String productId);

    public ResultVO getProductByCategoryId(int categoryId,int pageNum,int limit);

    public ResultVO selectBrandByCategoryId(int cid);

    public ResultVO searchProduct(String keyword,int pageNum,int limit);

    public ResultVO selectBrandByKeyWord(String keyword);
}
