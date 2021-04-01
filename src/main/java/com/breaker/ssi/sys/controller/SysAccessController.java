package com.breaker.ssi.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.entity.SysAccess;
import com.breaker.ssi.sys.mapper.SysAccessMapper;
import com.breaker.ssi.sys.service.impl.SysAccessServiceImpl;
import com.breaker.ssi.utils.annotation.OperationLog;
import com.breaker.ssi.utils.entity.BaseDelController;
import com.breaker.ssi.utils.entity.DelStatus;
import com.breaker.ssi.utils.result.Ret;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/sys/sys-access")
public class SysAccessController extends BaseDelController<SysAccessServiceImpl, SysAccess> {

    @Autowired
    private SysAccessMapper sysAccessMapper;

    @OperationLog("删除权限")
    @Override
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        return super.removeById(businessId, logDel);
    }

    @OperationLog("添加权限")
    @Override
    public Ret insert(SysAccess sysAccess) {
        int result = judgeNameAndCode(sysAccess, "");
        if(result == 1) {
            return Ret.error().setMsg("权限名称不能相同");
        }else if (result == 2) {
            return Ret.error().setMsg("权限编码已存在");
        }
        return super.insert(sysAccess);
    }

    @OperationLog("编辑权限")
    @Override
    public Ret updateById(@PathVariable(value="businessId") String businessId, SysAccess sysAccess) {
        int result = judgeNameAndCode(sysAccess, businessId);
        if(result == 1) {
            return Ret.error().setMsg("权限名称不能相同");
        }else if (result == 2) {
            return Ret.error().setMsg("权限编码已存在");
        }
        return super.updateById(businessId, sysAccess);
    }

    @GetMapping("/page/withName")
    @ApiOperation(value = "分页条件查询权限的列表", notes = "名称和编号模糊匹配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true),
            @ApiImplicitParam(name = "size", value = "每页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "名称或编号的关键字")
    })
    public Ret<IPage> getListByPage(String keyword, Integer page, Integer size) {
        if (page == null && size == null) {
            return Ret.error().setData("当前页和页大小不能为空");
        }
        return Ret.ok().setData(sysAccessMapper.selectListByPage(new Page<>(page, size), keyword));
    }

    private int judgeNameAndCode(SysAccess sysAccess, String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(id != "") {
            queryWrapper.ne("business_id", id);
        }
        queryWrapper.eq("name", sysAccess.getName());
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        if(this.t.list(queryWrapper).size() > 0) {
            return 1;
        }
        queryWrapper = new QueryWrapper();
        if(id != "") {
            queryWrapper.ne("business_id", id);
        }
        queryWrapper.eq("code", sysAccess.getCode());
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        if(this.t.list(queryWrapper).size() > 0) {
            return 2;
        }
        return 0;
    }
}
