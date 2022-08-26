package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.Product;
import com.qfedu.fmmall.entity.ProductVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductMapper extends GeneralDAO<Product> {
    //查询推荐商品
    public List<ProductVO>  selectRecommendProducts();
    //查询销量前6的商品
    public List<ProductVO> selectTop6ByCategory(int id);
    //通过商品类别查询商品
    public List<ProductVO> selectProductByCategoryId(@Param("cid") int cid,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);
    //通过类别id查询品牌
    public List<String> selectBrandByCategoryId(int cid);
    //通过模糊查询商品
    public List<ProductVO> selectProductByKeyWord(@Param("keyword") String keyword,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);
    //通过搜索关键字查询品牌
    public List<String> selectBrandByKeyword(String keyword);
}