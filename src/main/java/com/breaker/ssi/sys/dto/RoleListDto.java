package com.breaker.ssi.sys.dto;

import com.breaker.ssi.sys.entity.SysRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleListDto extends SysRole {
    private List<CommonDto> accessList;
}
