package com.breaker.ssi.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.UserListDto;
import com.breaker.ssi.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-01
 */
public interface ISysUserService extends IService<SysUser> {
    IPage<UserListDto> getUserListByPage(Page page, String keyword, String roleId, String groupId);
}
