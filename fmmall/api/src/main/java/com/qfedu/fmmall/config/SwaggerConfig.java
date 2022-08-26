package com.qfedu.fmmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    //swagger的配置类
    /**
     * 1.配置生成的文档信息
     * 2.配置生产的接口信息
     *
     */
    /*
    封装接口文档信息
     */
    @Bean
    public Docket getDocket(){
       //创建封面信息
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《ikun商城》后端接口说明")
                .description("这是天狗商城的接口的详细信息")
                .version("2.0.1")
                .contact(new Contact("ouyang","www.OuYang.com","932434088@qq.com"));
        ApiInfo apiInfo=apiInfoBuilder.build();

        Docket docket=new Docket(DocumentationType.SWAGGER_2)//<----指定文档风格
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qfedu.fmmall.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
