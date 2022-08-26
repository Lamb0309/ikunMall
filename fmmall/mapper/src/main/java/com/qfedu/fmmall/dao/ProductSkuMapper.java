package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.ProductSku;
import com.qfedu.fmmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDAO<ProductSku> {
    public List<ProductSku> selectProductSkusByProductId(String productId);
}