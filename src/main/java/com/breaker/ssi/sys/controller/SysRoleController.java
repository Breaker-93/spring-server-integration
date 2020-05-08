package com.breaker.ssi.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.RoleAddDto;
import com.breaker.ssi.sys.entity.SysRole;
import com.breaker.ssi.sys.entity.SysRoleAccess;
import com.breaker.ssi.sys.service.ISysRoleAccessService;
import com.breaker.ssi.sys.service.ISysRoleService;
import com.breaker.ssi.sys.service.impl.SysRoleServiceImpl;
import com.breaker.ssi.utils.annotation.OperationLog;
import com.breaker.ssi.utils.entity.BaseDelController;
import com.breaker.ssi.utils.result.Ret;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
@RestController
@RequestMapping("/sys/sys-role")
public class SysRoleController extends BaseDelController<SysRoleServiceImpl, SysRole> {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysRoleAccessService roleAccessService;

    @OperationLog("删除角色")
    @Override
    public Ret removeById(@PathVariable(value="businessId") String businessId, String logDel) {
        return super.removeById(businessId, logDel);
    }

    @OperationLog("添加角色")
    @Transactional(rollbackFor=Exception.class)
    @PostMapping("/withAccess")
    @ApiOperation(value = "添加角色（关联权限）" , notes = "业务主键自动生成")
    public Ret insert(RoleAddDto addDto) {
        SysRole sysRole = new SysRole();
        sysRole.setName(addDto.getName());
        sysRole.setCode(addDto.getCode());
        sysRole.setRemarks(addDto.getRemarks());
        if(sysRoleService.save(sysRole)) {
            List<String> list = addDto.getAccesses();
            if(list != null) {
                List<SysRoleAccess> roleAccessList = new ArrayList<>();
                list.forEach( accessId -> {
                    SysRoleAccess roleAccess = new SysRoleAccess();
                    roleAccess.setRoleId(sysRole.getBusinessId());
                    roleAccess.setAccessId(accessId);
                    roleAccessList.add(roleAccess);
                });
                roleAccessService.saveBatch(roleAccessList);
            }
            return Ret.ok().setData(sysRole);
        }else {
            return Ret.error().setData("角色添加失败");
        }
    }

    @OperationLog("编辑角色")
    @Override
    public Ret updateById(@PathVariable(value="businessId") String businessId, SysRole sysRole) {
        return super.updateById(businessId, sysRole);
    }

    @GetMapping("/withAccess/page")
    @ApiOperation(value = "多条件复合模糊或多合一模糊查询角色及权限的列表（分页）", notes = "条件模糊匹配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true),
            @ApiImplicitParam(name = "size", value = "每页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "多合一条件的关键字，不传则为多条件复合")
    })
    public Ret<IPage> getRoleList(String keyword, Integer page, Integer size) {
        if (page == null && size == null) {
            return Ret.error().setData("当前页和页大小不能为空");
        }
        return Ret.ok().setData(sysRoleService.getRoleListByPage(new Page<>(page,size), keyword));
    }
}
