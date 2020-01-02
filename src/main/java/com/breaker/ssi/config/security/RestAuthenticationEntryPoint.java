package com.breaker.ssi.config.security;

import com.alibaba.fastjson.JSON;
import com.breaker.ssi.utils.result.ResultEnums;
import com.breaker.ssi.utils.result.Ret;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 实现AuthenticationEntryPoint的commence方法自定义校验不通过的方法
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // 捕获AuthenticationException中的message，并封装成自定义异常抛出
        response.setCharacterEncoding("utf-8");
        //header 起作用
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        if (e instanceof InsufficientAuthenticationException) {
            response.getWriter().write(JSON.toJSONString(new Ret(ResultEnums.UNAUTHORIZED)));
        } else {
            response.getWriter().write(JSON.toJSONString(Ret.error()));
        }
    }
}