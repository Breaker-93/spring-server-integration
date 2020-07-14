package com.breaker.ssi.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breaker.ssi.config.exception.UsernameNotExistException;
import com.breaker.ssi.sys.entity.SysLog;
import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.entity.User;
import com.breaker.ssi.sys.mapper.SysUserMapper;
import com.breaker.ssi.sys.mapper.SysUserRoleMapper;
import com.breaker.ssi.utils.IpAddressUtil;
import com.breaker.ssi.utils.annotation.OperationLog;
import com.breaker.ssi.utils.entity.DelStatus;
import com.breaker.ssi.utils.entity.UseStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    ISysLogService sysLogService;

    @Override
    public UserDetails loadUserByUsername(String username){
        QueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>query()
                .eq("username", username)
                .eq("del_flag", DelStatus.NORMAL.getStatus())
                .eq("use_flag", UseStatus.START.getStatus());
        // 查询出指定用户名、且未删除、且正启用的用户（获取到用户名和密码）
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        String authorities = "illegal";
//        User user = new User("admin", new BCryptPasswordEncoder().encode("123456789"));
//        判断用户名不存在情况需自定义异常、才能被过滤器单独捕获到（框架里自带的会被处理成BadCredentialsException抛出）
        if(null == sysUser){
            saveLog("", 0, username + "该登录名不存在");
            throw new UsernameNotExistException(String.format("'%s'该登录名不存在.",username));
        }else{
            saveLog(sysUser.getBusinessId(), 67, null);
            // 根据用户id获取用户的权限编码，多个权限以逗号间隔拼接
            List<String> accesses = sysUserRoleMapper.selectRolesByUser(sysUser.getBusinessId());
            if(accesses.size() > 0) {
               authorities = StringUtils.join(accesses, ",");
            }
        }
        return new User(sysUser.getBusinessId(), sysUser.getUsername(),sysUser.getPassword(),authorities);
    }

    private void saveLog(String userId, int result, String failReason) {
        SysLog sysLog = new SysLog();
        sysLog.setOperateContent("登录");
        sysLog.setOperatorId(userId);
        sysLog.setOperateResult(result);
        if(failReason != null) {
            sysLog.setFailReason(failReason);
        }

        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        sysLog.setOperateIp(IpAddressUtil.getIpAdrress(request));

        //请求的时间
        sysLog.setOperateTime(LocalDateTime.now());

        sysLogService.save(sysLog);
    }
}
