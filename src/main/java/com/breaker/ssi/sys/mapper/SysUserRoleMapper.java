package com.breaker.ssi.sys.mapper;

import com.breaker.ssi.sys.dto.SimpleCommonDto;
import com.breaker.ssi.sys.entity.SysAccess;
import com.breaker.ssi.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-07
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<String> selectRolesByUser(String userId);
    List<SimpleCommonDto> selectAccessByUser(String userId);
}
