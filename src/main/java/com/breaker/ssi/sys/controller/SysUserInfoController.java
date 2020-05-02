package com.breaker.ssi.sys.controller;



import com.breaker.ssi.sys.entity.SysUserInfo;
import com.breaker.ssi.sys.service.impl.SysUserInfoServiceImpl;
import com.breaker.ssi.utils.entity.BaseDelController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
@RestController
@RequestMapping("/sys/sys-user-info")
public class SysUserInfoController extends BaseDelController<SysUserInfoServiceImpl, SysUserInfo> {

}
