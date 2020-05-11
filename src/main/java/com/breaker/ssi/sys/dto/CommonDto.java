package com.breaker.ssi.sys.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonDto {
    private String businessId;
    private String name;
    private String code;
    private String parentId;
    private int sort;
    private String remarks;
    private String jsonConfig;
}
