package com.breaker.ssi.sys.dto;

import com.breaker.ssi.sys.entity.SysUserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserAddDto {
    private String username;
    private String password;
    private List<String> roleList;
    private List<String> groupList;
    private SysUserInfo detailInfo;
}
