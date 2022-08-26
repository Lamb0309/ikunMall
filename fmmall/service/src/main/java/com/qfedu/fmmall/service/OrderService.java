package com.qfedu.fmmall.service;

import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderService {
    public Map<String,String> addOrder(String cids, Orders orders) throws SQLException;

    public int updateOrderStatus(String orderId,String status);

    public ResultVO getOrderById(String orderId);

    public void closeOrder(String orderId);

    public ResultVO listOrders(String userId,String status,int start,int limit);
}
