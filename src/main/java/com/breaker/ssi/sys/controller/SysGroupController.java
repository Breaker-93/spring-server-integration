package com.breaker.ssi.sys.controller;


import com.breaker.ssi.sys.entity.SysGroup;
import com.breaker.ssi.sys.service.impl.SysGroupServiceImpl;
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
 * @since 2020-05-02
 */
@RestController
@RequestMapping("/sys/sys-group")
public class SysGroupController extends BaseDelController<SysGroupServiceImpl, SysGroup> {
    @OperationLog("删除组")
    @Override
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        return super.removeById(businessId, logDel);
    }

    @OperationLog("添加组")
    @Override
    public Ret insert(SysGroup sysGroup) {
        return super.insert(sysGroup);
    }

    @OperationLog("编辑组")
    @Override
    public Ret updateById(@PathVariable(value="businessId") String businessId, SysGroup sysGroup) {
        return super.updateById(businessId, sysGroup);
    }
}
