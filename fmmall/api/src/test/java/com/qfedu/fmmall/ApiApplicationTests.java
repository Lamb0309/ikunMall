package com.qfedu.fmmall;

import com.qfedu.fmmall.dao.*;
import com.qfedu.fmmall.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApiApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Test
    void contextLoads() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories2(0);
        for (CategoryVO c1:categoryVOS
             ) {
            System.out.println(c1);
            for (CategoryVO c2:c1.getCategories()
                 ) {
                System.out.println("\t"+c2);
                for (CategoryVO c3:c2.getCategories()
                     ) {
                    System.out.println("\t\t"+c3);
                }
            }
        }
    }
    @Test
    public void testRecommand(){
        for (ProductVO p : productMapper.selectRecommendProducts()) {
            System.out.println(p);
        }

    }
    @Test
    public void testSelectFirstLevelCategory(){
        for (CategoryVO c : categoryMapper.selectFirstLevelCategories()) {
            System.out.println(c);
        }

    }
    @Test
    public void testProductComment(){
        for (ProductCommentsVO p : productCommentsMapper.selectCommContentByProductId("30",1,5)) {
            System.out.println(p);
        }

    }
    @Test
    public void testShopCart(){
        for (ShoppingCartVO s : shoppingCartMapper.selectShopCartByUserId(1)) {
            System.out.println(s);
        }

    }
    @Test
    public void testList(){
        List<Integer> cids = new ArrayList<>();
        cids.add(99);
        cids.add(100);
        cids.add(101);


    }
    @Test
    public void testList1(){
       String cids="99,100";
       String[] arr = cids.split(",");
       List<Integer> cidsList= new ArrayList<>();
       for (int i=0;i<arr.length;i++){
           cidsList.add(Integer.parseInt(arr[i]));
       }
       List<ShoppingCartVO> list = shoppingCartMapper.listShopCartByCid(cidsList);
        for (ShoppingCartVO s:
             list) {
            System.out.println(s);

        }
    }
    @Test
    public void testList2(){}
}
