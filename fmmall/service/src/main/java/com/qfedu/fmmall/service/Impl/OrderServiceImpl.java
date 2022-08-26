package com.qfedu.fmmall.service.Impl;

import com.qfedu.fmmall.dao.OrderItemMapper;
import com.qfedu.fmmall.dao.OrdersMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.dao.ShoppingCartMapper;
import com.qfedu.fmmall.entity.*;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;

//    @Override
    @Transactional
    public Map<String,String> addOrder(String cids,Orders orders) throws SQLException {
        Map<String,String> map=new HashMap();

        //1.校验库存,根据cid查询当前订单中关联的购物车记录
        String[] arr = cids.split(",");
        List<Integer> cidsList= new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            cidsList.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> list = shoppingCartMapper.listShopCartByCid(cidsList);
        boolean f=true;
        String untitled="";
        for (ShoppingCartVO sc:list) {
            if (Integer.parseInt(sc.getCartNum())>sc.getSkuStock()){
                f=false;
            }
            untitled=untitled+sc.getProductName()+",";
        }

        if (f){
            //2..库存充足时 ---保存订单
            orders.setUntitled(untitled);
            orders.setCreateTime(new Date());
            orders.setStatus("1");
            //生成订单编号
            String orderId = UUID.randomUUID().toString().replace("-", "");
            orders.setOrderId(orderId);
            //保存订单
            int i = ordersMapper.insert(orders);
            //3.生成商品快照

            for (ShoppingCartVO sc: list) {
                String itemId=System.currentTimeMillis()+""+(new Random().nextInt(89999)+10000);
                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(), sc.getProductId(), sc.getProductImg(),
                        sc.getSkuId(), sc.getSkuName(), new BigDecimal(sc.getSellPrice()), Integer.parseInt(sc.getCartNum()),
                        new BigDecimal(sc.getSellPrice()* Integer.parseInt(sc.getCartNum())), new Date(), new Date(), 0);
                int m = orderItemMapper.insert(orderItem);
            }
             //4.扣减库存
             for (ShoppingCartVO sc:list) {
                //得到商品套餐id，提供商品id修改库存量
                 String skuId = sc.getSkuId();
                 int newStock=sc.getSkuStock()-Integer.parseInt(sc.getCartNum());
                 ProductSku productSku = new ProductSku();
                 productSku.setStock(newStock);
                 productSku.setSkuId(skuId);
                 int k = productSkuMapper.updateByPrimaryKeySelective(productSku);
             }
             map.put("orderId",orderId);
             map.put("productNames",untitled);
             return map;
        }else {
            //库存不足
            return null;
        }
    }

    @Override
    public int updateOrderStatus(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        return i;
    }

    @Override
    public ResultVO getOrderById(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        if (orders!=null){
            return new ResultVO(ResStatus.OK,"success",orders.getStatus());
        }else {
            return new ResultVO(ResStatus.NO,"fail",null);
        }

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeOrder(String orderId) {
        //1.修改订单status=6（超时） close_type=1
        Orders cancleOrder = new Orders();
        cancleOrder.setOrderId(orderId);
        cancleOrder.setStatus("6");
        cancleOrder.setCloseType(1);
        ordersMapper.updateByPrimaryKeySelective(cancleOrder);
        //2.还原库存-->orderId-->skuId-->buyCount--->stock+buyCount，还原库存
        Example example1 = new Example(OrderItem.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("orderId",orderId);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
        //还原库存
        for (int j=0 ; j<orderItems.size();j++){
            OrderItem orderItem = orderItems.get(j);
            //修改库存
            ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
            productSku.setStock(productSku.getStock()+orderItem.getBuyCounts());
            productSkuMapper.updateByPrimaryKeySelective(productSku);
        }
    }

    @Override
    public ResultVO listOrders(String userId, String status, int pageNum, int limit) {
        //1.分页查询
        int start =(pageNum-1)*limit;
        List<OrdersVO> ordersVOS = ordersMapper.selectOrders(userId, status, start, limit);
        //2.查询总记录数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        if (status != null && !"".equals(status)){
            criteria.andEqualTo("status",status);
        }
        int count = ordersMapper.selectCountByExample(example);
        //3.计算总页数
//        int pageCount = count%limit==0?count/limit:count/limit+1;
        int pageCount = count%limit==0?count/limit:count/limit+1;
        PageHelper<OrdersVO> pageHelper = new PageHelper<>(count, pageCount, ordersVOS);
        return new  ResultVO(ResStatus.OK,"success",pageHelper);
    }
}
