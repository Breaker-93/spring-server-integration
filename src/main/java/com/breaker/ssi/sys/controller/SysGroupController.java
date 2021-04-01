package com.breaker.ssi.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.entity.SysAccess;
import com.breaker.ssi.sys.entity.SysGroup;
import com.breaker.ssi.sys.mapper.SysGroupMapper;
import com.breaker.ssi.sys.service.impl.SysGroupServiceImpl;
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
@RequestMapping("/sys/sys-group")
public class SysGroupController extends BaseDelController<SysGroupServiceImpl, SysGroup> {

    @Autowired
    private SysGroupMapper sysGroupMapper;

    @OperationLog("删除组(逻辑删除)")
    @Override
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        return super.removeById(businessId, logDel);
    }

    @OperationLog("添加组")
    @Override
    public Ret insert(SysGroup sysGroup) {
        int result = judgeNameAndCode(sysGroup, "");
        if(result == 1) {
            return Ret.error().setMsg("组名称不能相同");
        }else if (result == 2) {
            return Ret.error().setMsg("组编码已存在");
        }
        return super.insert(sysGroup);
    }

    @OperationLog("编辑组")
    @Override
    public Ret updateById(@PathVariable(value="businessId") String businessId, SysGroup sysGroup) {
        int result = judgeNameAndCode(sysGroup, businessId);
        if(result == 1) {
            return Ret.error().setMsg("组名称不能相同");
        }else if (result == 2) {
            return Ret.error().setMsg("组编码已存在");
        }
        return super.updateById(businessId, sysGroup);
    }
    
    @GetMapping("/page/withName")
    @ApiOperation(value = "分页条件查询组的列表", notes = "名称和编号模糊匹配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true),
            @ApiImplicitParam(name = "size", value = "每页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "名称或编号的关键字")
    })
    public Ret<IPage> getListByPage(String keyword, Integer page, Integer size) {
        if (page == null && size == null) {
            return Ret.error().setData("当前页和页大小不能为空");
        }
        return Ret.ok().setData(sysGroupMapper.selectListByPage(new Page<>(page, size), keyword));
    }

    private int judgeNameAndCode(SysGroup sysGroup, String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(id != "") {
            queryWrapper.ne("business_id", id);
        }
        queryWrapper.eq("name", sysGroup.getName());
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        if(this.t.list(queryWrapper).size() > 0) {
            return 1;
        }
        queryWrapper = new QueryWrapper();
        if(id != "") {
            queryWrapper.ne("business_id", id);
        }
        queryWrapper.eq("code", sysGroup.getCode());
        queryWrapper.eq("del_flag", DelStatus.NORMAL.getStatus());
        if(this.t.list(queryWrapper).size() > 0) {
            return 2;
        }
        return 0;
    }
}
