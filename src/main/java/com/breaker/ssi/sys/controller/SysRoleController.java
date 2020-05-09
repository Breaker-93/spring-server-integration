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
import com.breaker.ssi.utils.DozerUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysRoleAccessService roleAccessService;


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
            List<String> list = addDto.getAccessList();
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

    @OperationLog("删除角色")
    @Transactional(rollbackFor=Exception.class)
    @DeleteMapping("/{businessId:\\w+}")
    @ApiOperation(value = "删除（默认物理删除）", notes = "物理删除，关联的权限关系一并删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name  =  "businessId",value  ="业务主键", required = true)
    })
    public Ret removeById(@PathVariable(value="businessId") String businessId) {
        Map map = new HashMap();
        map.put("role_id", businessId);
        roleAccessService.removeByMap(map);
        if(sysRoleService.removeById(businessId)) {
            return Ret.ok();
        }else {
            return Ret.error();
        }
    }

    @OperationLog("修改角色")
    @Transactional(rollbackFor=Exception.class)
    @PutMapping("/withAccess/{businessId:\\w+}")
    @ApiOperation(value = "修改更新角色及关联的权限", notes = "修改")
    public Ret updateById(@PathVariable(value="businessId") String businessId, RoleAddDto addDto) {
        // 简单处理，对角色关联的权限、无论修改与否，都先将其关联关系记录全部删除
        Map map = new HashMap();
        map.put("role_id", businessId);
        roleAccessService.removeByMap(map);

        List<String> list = addDto.getAccessList();
        if(list != null) {

            List<SysRoleAccess> roleAccessList = new ArrayList<>();
            list.forEach( accessId -> {
                SysRoleAccess roleAccess = new SysRoleAccess();
                roleAccess.setRoleId(businessId);
                roleAccess.setAccessId(accessId);
                roleAccessList.add(roleAccess);
            });
            roleAccessService.saveBatch(roleAccessList);
        }
        SysRole sysRole = DozerUtils.map(addDto, SysRole.class);
        sysRole.setBusinessId(businessId);
        if (sysRoleService.updateById(sysRole)) {
            return Ret.ok().setData("修改成功").setData(addDto);
        } else {
            return Ret.error().setData("修改失败");
        }
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
