package com.breaker.ssi.sys.dto;

import com.breaker.ssi.sys.entity.SysUserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserListDto extends SysUserInfo {
    private String username;
    private List<CommonDto> roleList;
    private List<CommonDto> groupList;
}
