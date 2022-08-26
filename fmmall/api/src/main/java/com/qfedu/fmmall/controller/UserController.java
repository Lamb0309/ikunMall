package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.entity.Users;
import com.qfedu.fmmall.service.UserService;
import com.qfedu.fmmall.utils.Base64Utils;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value = "提供用户登录和注册的功能", tags = "用户管理")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;


    @ApiOperation(value = "用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户登录密码", required = true)
    })
    @GetMapping(value = "/login")
    public ResultVO login(@RequestParam("username") String name,
                          @RequestParam(value = "password") String pwd) {
        ResultVO resultVO = userService.checkLogin(name, pwd);
        return resultVO;
    }


    @ApiOperation(value = "用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "username", value = "用户注册账号", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户注册密码", required = true)
    })
    @PostMapping("/regist")
    public ResultVO regist(@RequestBody Users user) {
        ResultVO resultVO = userService.userResgit(user.getUsername(),user.getPassword());
        return resultVO;
    }

    @ApiOperation(value = "校验token是否过期接口")
    @GetMapping("/check")
    public ResultVO userTokenCheck(@RequestHeader("token") String token){
        return new ResultVO(ResStatus.OK,"success",null);
    }

}
