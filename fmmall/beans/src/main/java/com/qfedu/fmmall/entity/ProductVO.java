package com.qfedu.fmmall.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVO {
    private String productId;
    private String productName;
    private Integer categoryId;
    private Integer rootCategoryId;
    private Integer soldNum;
    private Integer productStatus;
    private String content;
    private Date createTime;
    private Date updateTime;
    private List<ProductImg> imgs;  //套餐图片
    private List<ProductSku> skus;   //套餐信息
}