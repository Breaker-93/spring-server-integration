package com.breaker.ssi.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.UserListDto;
import com.breaker.ssi.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-01
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<UserListDto> getUsersWithRoleAndGroup(Page page, String keyword, String roleId, String groupId);
}
