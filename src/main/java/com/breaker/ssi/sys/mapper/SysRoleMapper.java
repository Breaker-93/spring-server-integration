package com.breaker.ssi.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.RoleListDto;
import com.breaker.ssi.sys.dto.SimpleCommonDto;
import com.breaker.ssi.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<RoleListDto> getRolesWithAccess(Page page, String keyword);
    List<SimpleCommonDto> getRolesByUserId(String userId);
}
