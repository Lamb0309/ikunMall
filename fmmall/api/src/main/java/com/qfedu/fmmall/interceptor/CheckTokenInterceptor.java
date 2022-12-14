package com.qfedu.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        System.out.println("拦截器启动了-----");
        System.out.println("token:"+token);
        if (token == null) {
            ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录", null);
            doResponse(response, resultVO);
            return false;
        } else {
            try {
                //验证token
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("QIANfeng666");//解析token时的key，必须和设置的时候一致
                //如果信息正确，正常运行，否者抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录(拦截器)", null);
                doResponse(response,resultVO);
                return false;
            }
        }

    }

    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        writer.print(s);
        writer.flush();
        writer.close();
    }
}
