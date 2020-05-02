package com.breaker.ssi.sys.controller;


import com.breaker.ssi.sys.entity.SysAccess;
import com.breaker.ssi.sys.service.impl.SysAccessServiceImpl;
import com.breaker.ssi.utils.annotation.OperationLog;
import com.breaker.ssi.utils.entity.BaseDelController;
import com.breaker.ssi.utils.result.Ret;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
@RestController
@RequestMapping("/sys/sys-access")
public class SysAccessController extends BaseDelController<SysAccessServiceImpl, SysAccess> {
    @Override
    public Ret removeById(String businessId, String logDel) {
        return super.removeById(businessId, logDel);
    }

    @OperationLog("添加权限")
    @Override
    public Ret insert(SysAccess sysAccess) {
        return super.insert(sysAccess);
    }

    @Override
    public Ret updateById(String businessId, SysAccess sysAccess) {
        return super.updateById(businessId, sysAccess);
    }
}
