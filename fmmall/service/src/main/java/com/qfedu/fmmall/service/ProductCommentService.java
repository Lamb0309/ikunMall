package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;

public interface ProductCommentService {
    /**
     *
     * @param productId  商品id
     * @param limit   每页显示数
     * @param pageNum  页码
     * @return
     */
    public ResultVO listCommentByProductId(String productId,int limit,int pageNum);
    public ResultVO getCommentsCount(String productId);
}
