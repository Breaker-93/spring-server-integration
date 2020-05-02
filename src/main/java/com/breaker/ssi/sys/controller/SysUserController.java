package com.breaker.ssi.sys.controller;


import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.service.impl.SysUserServiceImpl;
import com.breaker.ssi.utils.entity.BaseDelController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class SysUserController extends BaseDelController<SysUserServiceImpl,SysUser> {

}
