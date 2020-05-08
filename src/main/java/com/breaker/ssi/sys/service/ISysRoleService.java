package com.breaker.ssi.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.RoleListDto;
import com.breaker.ssi.sys.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
public interface ISysRoleService extends IService<SysRole> {
    IPage<RoleListDto> getRoleListByPage(Page page, String keyword);
}
