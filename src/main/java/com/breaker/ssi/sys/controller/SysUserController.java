package com.breaker.ssi.sys.controller;


import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.service.impl.SysUserServiceImpl;
import com.breaker.ssi.utils.annotation.OperationLog;
import com.breaker.ssi.utils.entity.BaseDelController;
import com.breaker.ssi.utils.result.Ret;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-01
 */
@RestController
@RequestMapping("/sys/sys-user")
public class SysUserController extends BaseDelController<SysUserServiceImpl,SysUser> {

    @OperationLog("删除用户")
    @Override
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        return super.removeById(businessId, logDel);
    }

    @OperationLog("添加用户")
    @Override
    public Ret insert(SysUser sysUser) {
        return super.insert(sysUser);
    }

    @OperationLog("编辑角色")
    @Override
    public Ret updateById(@PathVariable(value="businessId") String businessId, SysUser sysUser) {
        return super.updateById(businessId, sysUser);
    }
}
