package com.qfedu.fmmall.service.job;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.qfedu.fmmall.dao.OrderItemMapper;
import com.qfedu.fmmall.dao.OrdersMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.entity.OrderItem;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.entity.ProductSku;
import com.qfedu.fmmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeOutCheckJob {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderService orderService;

    private WXPay wxPay= new WXPay(new WXPayConfig() {
        @Override
        public String getAppID() {
            return "wx632c8f211f8122c6";
        }

        @Override
        public String getMchID() {
            return "1497984412";
        }

        @Override
        public String getKey() {
            return "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";
        }

        @Override
        public InputStream getCertStream() {
            return null;
        }

        @Override
        public int getHttpConnectTimeoutMs() {
            return 0;
        }

        @Override
        public int getHttpReadTimeoutMs() {
            return 0;
        }
    });

    @Scheduled(cron = "0/60 * * * * ? ")
    public void checkAndCloseOrder() {
        try {
        //1.查询30分钟的订单依然没有支付
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", "1");
        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime", time);
        List<Orders> orders = ordersMapper.selectByExample(example);

        //2.访问微信平台接口,确认订单的支付状态

            for (int i = 0; i < orders.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                Orders order = orders.get(i);
                map.put("out_trade_no", order.getOrderId());
                Map<String, String> resp = wxPay.orderQuery(map);
                if ("SUCCESS".equals(resp.get("trade_state"))) {
                    //2.1如果已经支付则status=2
                    Orders updateOrder = new Orders();
                    updateOrder.setOrderId(order.getOrderId());
                    updateOrder.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(updateOrder);
                } else if ("NOTPAY".equals(resp.get("trade_state"))) {
                    //2.2若未支付，取消订单
                    //a.向微信平台取消支付请求
                    Map<String, String> resp1 = wxPay.closeOrder(map);
                    System.out.println(resp1);
                    if ("SUCCESS".equals(resp1.get("result_code"))){

                    }
                    //关闭订单
                    orderService.closeOrder(order.getOrderId());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
