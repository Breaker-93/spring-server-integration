package com.breaker.ssi.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.dto.UserListDto;
import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.mapper.SysUserMapper;
import com.breaker.ssi.sys.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-01
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public IPage<UserListDto> getUserListByPage(Page page, String keyword, String roleId, String groupId) {
        return super.baseMapper.getUsersWithRoleAndGroup(page, keyword, roleId, groupId);
    }
}
