package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.service.ShoppingCartService;
import com.qfedu.fmmall.utils.Base64Utils;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopcart")
@CrossOrigin
@Api(value = "提供购物车业务相关接口", tags = "购物车管理")
public class ShopcartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("添加购物车接口")
    @PostMapping("/add")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart,@RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.addShoppingCart(cart);
        return resultVO;
    }

    @ApiOperation("查询购物车接口")
    @GetMapping("/list")
    @ApiImplicitParam(dataType = "String",name = "userId",value = "用户id",required = true)
    public ResultVO list(Integer userId,@RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.listShoppingCartsByUserId(userId);
        return resultVO;
    }

    @ApiOperation("修改购物车接口")
    @PutMapping("/update/{cid}/{cnum}")
    public ResultVO update(@PathVariable("cid") int cartId,
                           @PathVariable("cnum")int cartNum,
                           @RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.updateCartNum(cartId, cartNum);
        return resultVO;
    }

    @ApiOperation("删除购物车接口")
    @DeleteMapping ("/delete/{cid}")
    public ResultVO delete(@PathVariable("cid") int cartId,
                           @RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.deleteCartBYCartId(cartId);
        return resultVO;
    }



    @ApiOperation("根据购物车id查询购物车接口")
    @GetMapping("/listbycids")
    @ApiImplicitParam(dataType = "String",name = "cids",value = "用户id集合",required = true)
    public ResultVO listByCids(String cids, @RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.listCartBYCartId(cids);
        return resultVO;
    }

    @ApiOperation("删除多个购物车接口")
    @DeleteMapping ("/deletes/{cids}")
    public ResultVO deletes(@PathVariable("cids") String cartIds,@RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.deleteCartBYCartIds(cartIds);
        return resultVO;
    }



}
