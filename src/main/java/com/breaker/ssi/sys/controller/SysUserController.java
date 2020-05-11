package com.breaker.ssi.sys.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.UserAddDto;
import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.entity.SysUserGroup;
import com.breaker.ssi.sys.entity.SysUserInfo;
import com.breaker.ssi.sys.entity.SysUserRole;
import com.breaker.ssi.sys.service.ISysUserGroupService;
import com.breaker.ssi.sys.service.ISysUserInfoService;
import com.breaker.ssi.sys.service.ISysUserRoleService;
import com.breaker.ssi.sys.service.ISysUserService;
import com.breaker.ssi.sys.service.impl.SysUserServiceImpl;
import com.breaker.ssi.utils.annotation.OperationLog;
import com.breaker.ssi.utils.entity.BaseController;
import com.breaker.ssi.utils.entity.BaseDelController;
import com.breaker.ssi.utils.entity.DelStatus;
import com.breaker.ssi.utils.result.Ret;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * @since 2020-05-01
 */
@RestController
@RequestMapping("/sys/sys-user")
public class SysUserController extends BaseController<SysUserServiceImpl,SysUser> {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserGroupService sysUserGroupService;

    @Autowired
    private ISysUserInfoService sysUserInfoService;

    @OperationLog("添加用户")
    @Transactional(rollbackFor=Exception.class)
    @PostMapping("/withRoleAndGroup")
    @ApiOperation(value = "添加用户" , notes = "业务主键自动生成")
    public Ret insert(UserAddDto addDto) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(addDto.getUsername());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(addDto.getPassword()));
        if(sysUserService.save(sysUser)) {
            String userId = sysUser.getBusinessId();
            List<String> roleList = addDto.getRoleList();
            List<String> groupList = addDto.getGroupList();
            SysUserInfo userInfo = addDto.getDetailInfo();
            if(roleList != null) {
                List<SysUserRole> userRoles = new ArrayList<>();
                roleList.forEach( roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);
                    userRoles.add(userRole);
                });
                sysUserRoleService.saveBatch(userRoles);
            }
            if(groupList != null) {
                List<SysUserGroup> userGroups = new ArrayList<>();
                groupList.forEach( groupId -> {
                    SysUserGroup userGroup = new SysUserGroup();
                    userGroup.setGroupId(groupId);
                    userGroup.setUserId(userId);
                    userGroups.add(userGroup);
                });
                sysUserGroupService.saveBatch(userGroups);
            }
            if(userInfo == null) {
                userInfo = new SysUserInfo();
            }
            userInfo.setUserId(userId);
            sysUserInfoService.save(userInfo);
            return Ret.ok();
        }else {
            return Ret.error().setData("用户添加失败");
        }
    }

    @OperationLog("删除用户")
    @Transactional(rollbackFor=Exception.class)
    @DeleteMapping("/{businessId:\\w+}")
    @ApiOperation(value = "删除（默认逻辑删除）", notes = "逻辑删除，关联的角色、组关系一并逻辑删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name  =  "businessId",value  ="业务主键", required = true)
    })
    public Ret removeById(@PathVariable(value="businessId") String businessId) {
        Map map = new HashMap();
        map.put("user_id", businessId);
        sysUserRoleService.removeByMap(map);
        sysUserGroupService.removeByMap(map);

        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setUserId(businessId);
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.set("del_flag", DelStatus.DELETED.getStatus());
        sysUserInfoService.update(userInfo, wrapper);

        SysUser sysUser = new SysUser();
        sysUser.setBusinessId(businessId);
        sysUser.setDeleted();
        if(sysUserService.updateById(sysUser)) {
            return Ret.ok().setData("逻辑删除成功");
        }else {
            return Ret.error().setData("逻辑删除失败！");
        }
    }

    @OperationLog("编辑角色")
    @Override
    public Ret updateById(@PathVariable(value="businessId") String businessId, SysUser sysUser) {
        return super.updateById(businessId, sysUser);
    }

    @GetMapping("/withAccessAndGroup/page")
    @ApiOperation(value = "多条件复合模糊或多合一模糊查询角色及权限的列表（分页）", notes = "条件模糊匹配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true),
            @ApiImplicitParam(name = "size", value = "每页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "多合一条件的关键字，不传则为多条件复合")
    })
    public Ret<IPage> getRoleList(String keyword, String roleId, String groupId, Integer page, Integer size) {
        if (page == null && size == null) {
            return Ret.error().setData("当前页和页大小不能为空");
        }
        return Ret.ok().setData(sysUserService.getUserListByPage(new Page<>(page,size), keyword, roleId, groupId));
    }
}
