package com.breaker.ssi.business.book.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookResponseDto {
    private String businessId;
    private String name;
    private String price;
    private String createDate;
    private String updateDate;
}
