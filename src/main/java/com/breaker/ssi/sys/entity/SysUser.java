package com.breaker.ssi.sys.entity;

import lombok.Data;

@Data
public class SysUser {
    private String businessId;
    private String loginName;
    private String password;
    private String name;
}
