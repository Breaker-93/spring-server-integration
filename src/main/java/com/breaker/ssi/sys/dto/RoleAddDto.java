package com.breaker.ssi.sys.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleAddDto {
    private String name;
    private String code;
    private List<String> accesses;
    private String remarks;
}
