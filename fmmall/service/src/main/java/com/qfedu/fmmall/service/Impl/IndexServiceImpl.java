package com.qfedu.fmmall.service.Impl;

import com.qfedu.fmmall.dao.IndexImgMapper;
import com.qfedu.fmmall.entity.IndexImg;
import com.qfedu.fmmall.service.IndexService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexImgMapper indexImgMapper;
    @Override
    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if (indexImgs.size()==0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }else {
            return new ResultVO(ResStatus.OK,"success",indexImgs);
        }
    }
}
