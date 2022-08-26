package com.qfedu.fmmall.service.Impl;

import com.qfedu.fmmall.dao.ProductCommentsMapper;
import com.qfedu.fmmall.entity.ProductComments;
import com.qfedu.fmmall.entity.ProductCommentsVO;
import com.qfedu.fmmall.service.ProductCommentService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
@Service
public class ProductCommentServiceImpl implements ProductCommentService {
    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Override
    public ResultVO listCommentByProductId(String productId,int limit,int pageNum) {
//        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommContentByProductId(pid);
        //1.根据商品id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);
        //2.计算总页数(必须知道每一页显示多少条，pageSize=limit)
        int pageCount = count%limit==0?count/limit : count/limit+1;
        //3.查询该页数据(因为评论有用户学信息，需要连表查询)
        int start=(pageNum-1)*limit;
        List<ProductCommentsVO> list = productCommentsMapper.selectCommContentByProductId(productId, start, limit);
        return new ResultVO(ResStatus.OK,"success",new PageHelper<ProductCommentsVO>(count,pageCount,list));
    }

    @Override
    public ResultVO getCommentsCount(String productId) {
        //1.查询当前商品评价的总数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int total = productCommentsMapper.selectCountByExample(example);

        //2.查询好评评价数
        criteria.andEqualTo("commType",1);
        int goodTotal = productCommentsMapper.selectCountByExample(example);

        //3.查询好评评价数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 =  example1.createCriteria();
        criteria1.andEqualTo("productId",productId);
        criteria1.andEqualTo("commType",0);
        int midTotal = productCommentsMapper.selectCountByExample(example1);

        //4.查询好评评价数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 =  example2.createCriteria();
        criteria2.andEqualTo("productId",productId);
        criteria2.andEqualTo("commType",-1);
        int badTotal = productCommentsMapper.selectCountByExample(example2);

        //5.计算好评率
        double percent = (Double.parseDouble(goodTotal+"") / Double.parseDouble(total+"") )*100;
//        String percentValue = (percent+"").substring(0,(percent+"").lastIndexOf(".")+3);
        String percentValue=StringUtils.substring((percent+""), 0, (percent+"").lastIndexOf(".")+3);
        HashMap<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("goodTotal",goodTotal);
        map.put("midTotal",midTotal);
        map.put("badTotal",badTotal);
        map.put("percent",percentValue);

        ResultVO success = new ResultVO(ResStatus.OK, "success", map);
        return success;
    }
}
