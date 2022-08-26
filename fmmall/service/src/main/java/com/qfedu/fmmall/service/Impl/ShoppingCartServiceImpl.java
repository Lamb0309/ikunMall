package com.qfedu.fmmall.service.Impl;

import com.qfedu.fmmall.dao.ShoppingCartMapper;
import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.entity.ShoppingCartVO;
import com.qfedu.fmmall.service.ShoppingCartService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int insert = shoppingCartMapper.insert(cart);
        if (insert>0){
           return new ResultVO(ResStatus.OK,"success",null);
        }else {
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listShoppingCartsByUserId(int userId) {
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopCartByUserId(userId);
        return new ResultVO(ResStatus.OK,"success",list);
    }

    @Override
    public ResultVO updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(cartId, cartNum);
        if (i>0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else {
            return new ResultVO(ResStatus.NO,"fail",null);
        }

    }

    @Override
    public ResultVO deleteCartBYCartId(int cartId) {
        int i = shoppingCartMapper.deleteByPrimaryKey(cartId);
        if (i>0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else {
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Override
    public ResultVO listCartBYCartId(String cids) {
        String[] arr = cids.split(",");
        List<Integer> cartIds=new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            cartIds.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> list = shoppingCartMapper.listShopCartByCid(cartIds);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", list);
        return resultVO;
    }

    @Override
    public ResultVO deleteCartBYCartIds(String cids) {
        String[] arr = cids.split(",");
        List<Integer> cartIds=new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            cartIds.add(Integer.parseInt(arr[i]));
        }
        int i = shoppingCartMapper.deleteShopCartByCids(cartIds);
        if (i>0){
            return new ResultVO(ResStatus.OK, "success", null);
        }else {
            return new ResultVO(ResStatus.NO, "fail", null);
        }

    }




}
