package com.breaker.ssi.sys.controller;


import com.breaker.ssi.sys.entity.SysLog;
import com.breaker.ssi.sys.service.impl.SysLogServiceImpl;
import com.breaker.ssi.utils.entity.BaseController;
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
@RequestMapping("/sys/sys-log")
public class SysLogController extends BaseController<SysLogServiceImpl, SysLog> {

}
