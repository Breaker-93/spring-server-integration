package com.breaker.ssi.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.RoleListDto;
import com.breaker.ssi.sys.entity.SysRole;
import com.breaker.ssi.sys.mapper.SysRoleMapper;
import com.breaker.ssi.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public IPage<RoleListDto> getRoleListByPage(Page page, String keyword) {
        return super.baseMapper.getRolesWithAccess(page, keyword);
    }
}
