package com.breaker.ssi.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breaker.ssi.sys.entity.SysGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
public interface SysGroupMapper extends BaseMapper<SysGroup> {
    IPage<SysGroup> selectListByPage(Page page, String keyword);
}
