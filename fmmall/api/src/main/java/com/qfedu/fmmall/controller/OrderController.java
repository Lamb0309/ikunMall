package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPay;
import com.qfedu.fmmall.config.MyPayConfig;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(value = "提供订单相关的接口", tags = "订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("订单添加接口")
    @PostMapping("/add")
    public ResultVO add(String cids, @RequestBody Orders orders,@RequestHeader("token") String token){
        ResultVO resultVO = null;
        try {
            Map<String, String> orderInfo = orderService.addOrder(cids, orders);
            String orderId=orderInfo.get("orderId");
            String productNames=orderInfo.get("productNames");
            if (orderId!=null){
                //设置支付订单的参数
                HashMap<String,String> data=new HashMap<>();
                data.put("body",productNames);    //商品描述
                data.put("out_trade_no",orderId);//使用当前用户订单的编号作为当前支付交易的交易号
                data.put("fee_type","CNY");     //支付方式
//                data.put("total_fee",orders.getActualAmount()*100+"");    //支付金额
                data.put("total_fee","1");    //支付金额 设置为1
                data.put("trade_type","NATIVE");//交易类型
                data.put("notify_url","http://ikuns.free.idcfengye.com/pay/callback");      //设置支付完成时回调方法的url
                //微信支付
                WXPay wxPay = new WXPay(new MyPayConfig());
                //对微信支付平台发送请求获取响应
                Map<String, String> resp = wxPay.unifiedOrder(data);
//                if(resp.containsKey("code_url")){
//
//                    resp.put("code_url", "wxp://f2f0TGKn36XHw9qnuHYLsuExUAjkGe4yZdHNQi5UaC0PPyw");
//                }
                orderInfo.put("payUrl",resp.get("code_url"));
                resultVO =new ResultVO(ResStatus.OK,"提交订单成功",orderInfo);
            }else {
                resultVO =new ResultVO(ResStatus.NO,"提交订单失败",null);
            }
        } catch (SQLException throwables) {
            resultVO =new ResultVO(ResStatus.NO,"提交订单失败",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;
    }

    @ApiOperation("订单查询接口")
    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("oid") String orderId,@RequestHeader("token") String token){
        ResultVO resultVO = orderService.getOrderById(orderId);
        return resultVO;
    }

    @ApiOperation("订单查询以及根据订单状态查询订单详情接口")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "userId", value = "用户Id", required = true),
            @ApiImplicitParam(dataType = "String", name = "status", value = "订单状态", required = false),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页条数", required = true)
    })
    public ResultVO list(@RequestHeader("token") String token, String userId, String status, int pageNum, int limit){
        ResultVO resultVO = orderService.listOrders(userId, status, pageNum, limit);
        return resultVO;
    }
}
