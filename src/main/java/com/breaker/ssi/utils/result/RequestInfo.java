package com.breaker.ssi.utils.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RequestInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private long requestId;

}
