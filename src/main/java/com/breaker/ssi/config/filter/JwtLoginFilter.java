package com.breaker.ssi.config.filter;

import com.alibaba.fastjson.JSON;
import com.breaker.ssi.config.exception.UsernameNotExistException;
import com.breaker.ssi.sys.dto.SimpleCommonDto;
import com.breaker.ssi.sys.entity.SysAccess;
import com.breaker.ssi.sys.entity.User;
import com.breaker.ssi.sys.mapper.SysRoleMapper;
import com.breaker.ssi.sys.mapper.SysUserRoleMapper;
import com.breaker.ssi.utils.result.ResultEnums;
import com.breaker.ssi.utils.result.Ret;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken( (String)httpServletRequest.getParameter("username"),  (String)httpServletRequest.getParameter("password")));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        String as = StringUtils.join(authorities, ",");
        User user = (User) authResult.getPrincipal();
        // 根据用户id获取用户的权限编码
        List<SimpleCommonDto> accesses = sysUserRoleMapper.selectAccessByUser(user.getBusinessId());
        // 获取角色集合
        List<SimpleCommonDto> roleList = roleMapper.getRolesByUserId(user.getBusinessId());
        String jwt = Jwts.builder()
                .claim("authorities", as)//配置用户权限
                .setSubject(user.getUsername())
                .setId(user.getBusinessId())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
        response.setContentType("application/json;charset=utf-8");
//        response.setHeader("token", jwt);
        PrintWriter out = response.getWriter();
//        out.write(new ObjectMapper().writeValueAsString(jwt));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(tokenHeader, jwt);
        resultMap.put("authorities", as);
        resultMap.put("accesses", accesses);
        resultMap.put("roles", roleList);
        String resJson = JSON.toJSONString(new Ret(ResultEnums.LOGIN_SUCCESS,resultMap));
        out.write(resJson);
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String tipInfo;
        if (failed.getCause() instanceof UsernameNotExistException) {
            tipInfo = failed.getMessage();
//        } else if (failed.getCause() instanceof RedisConnectionFailureException) {
//            tipInfo = "Redis连接失败";
        } else if (failed instanceof BadCredentialsException) {
            tipInfo = "密码错误";
        } else {
            tipInfo = failed.getMessage();
        }
        String resJson = JSON.toJSONString(Ret.build(ResultEnums.LOGIN_ERROR).setData(tipInfo));
        out.write(resJson);
        out.flush();
        out.close();
    }
}

