package com.breaker.ssi.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breaker.ssi.config.exception.UsernameNotExistException;
import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.entity.User;
import com.breaker.ssi.sys.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        QueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>query()
                .eq("login_name", username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
//        User user = new User("admin", new BCryptPasswordEncoder().encode("123456789"));
//        判断用户名不存在情况需自定义异常、才能被过滤器单独捕获到（框架里自带的会被处理成BadCredentialsException抛出）
        if(null == sysUser){
            throw new UsernameNotExistException(String.format("'%s'该登录名不存在.",username));
        }
        return new User(sysUser.getBusinessId(), sysUser.getLoginName(),sysUser.getPassword(),"manager");
    }
}
