package com.qfedu.fmmall.service.Impl;

import com.qfedu.fmmall.dao.UsersMapper;
import com.qfedu.fmmall.entity.Users;
import com.qfedu.fmmall.service.UserService;
import com.qfedu.fmmall.utils.Base64Utils;
import com.qfedu.fmmall.utils.MD5Utils;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Transactional
    public ResultVO userResgit(String name, String pwd) {
        synchronized (this) {
            //1.根据用户名查询是否被注册
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",name);
            List<Users> users = usersMapper.selectByExample(example);
            //若用户名可用则进行用户保存
            if (users.size()==0) {
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int result = usersMapper.insertUseGeneratedKeys(user);
                if (result > 0) {
                    return new ResultVO(ResStatus.OK, "注册成功!", user);
                } else {
                    return new ResultVO(ResStatus.NO, "注册失败!", null);
                }

            } else {
                return new ResultVO(ResStatus.NO, "用户名已被注册", null);
            }
        }

    }

    @Override
    public ResultVO checkLogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",name);
        List<Users> users = usersMapper.selectByExample(example);
        if (users.size() == 0) {
            return new ResultVO(ResStatus.NO, "登录失败,用户名不存在!", null);
        } else {
            String md5Pwd = MD5Utils.md5(pwd);
            if (users.get(0).getPassword().equals(md5Pwd)) {
                //生产令牌
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(name)      //token携带的数据
                        .setIssuedAt(new Date())               //设置创建时间
                        .setId(users.get(0).getUserId() + "")   //设置token的id
                        .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))  //设置过期时间
                        .signWith(SignatureAlgorithm.HS256, "QIANfeng666") //设置加密方式及密码
                        .compact();
                return new ResultVO(ResStatus.OK, token, users.get(0));
            } else {
                return new ResultVO(ResStatus.NO, "登录失败,密码错误!", null);
            }
        }
    }
}
