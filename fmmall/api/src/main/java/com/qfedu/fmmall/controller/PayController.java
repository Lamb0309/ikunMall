package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.webSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    /**
     * 回调接口： 用于实现支付成功之后，微信支付中心向指定url发送的请求的接口
     * 1.处理来自微信支付平台的数据(使用request的输入流接受)
     */

    @RequestMapping("/callback")
    public String paySuccess(HttpServletRequest request) throws Exception {
        System.out.println("----callback");
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len=-1;
        StringBuilder builder=new StringBuilder();
        while ((len=is.read(bs))!=-1){
            builder.append(new String(bs,0,len));
        }
        String s = builder.toString();
        //使用工具类把xml转换为map
        Map<String, String> map = WXPayUtil.xmlToMap(s);
        if (map!=null&&"SUCCESS".equals(map.get("result_code"))){
            //支付成功
            //2.修改订单状态--->已付款/待发货
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId, "2");
            System.out.println("orderId"+orderId);
            //3.通过webSocket连接，向前端推送消息
            WebSocketServer.sendMsg(orderId,"1");
            //4.响应微信支付平台
            if (i>0){
                HashMap<String, String> resp = new HashMap<>();
                resp.put("return_code","success");
                resp.put("return_msg","OK");
                resp.put("app_id",map.get("appid"));
                resp.put("result_code","success");
                return WXPayUtil.mapToXml(resp);
            }else {
                return null;
            }
        }else {
            //支付失败
            return null;
        }

    }

}
