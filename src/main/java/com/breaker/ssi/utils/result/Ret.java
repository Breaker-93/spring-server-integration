package com.breaker.ssi.utils.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.io.Serializable;

@Setter
@Getter
public class Ret<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Order(1)
    @ApiModelProperty(value = "响应状态码")
    private String code;

    @Order(2)
    @ApiModelProperty(value = "响应描述")
    private String msg;

    @Order(3)
    @ApiModelProperty(value = "请求时间")
    private Long timestamp;

    @Order(4)
    @ApiModelProperty(value = "请求ID")
    private String requestId;

    @Order(5)
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Ret(ResultEnums resultEnums) {
        this.code = resultEnums.getCode();
        this.msg = resultEnums.getMsg();
        this.timestamp = System.currentTimeMillis();
        if (RequestContext.getRequestInfo() != null) {
            this.requestId = String.valueOf(RequestContext.getRequestInfo().getRequestId());
        }
    }

    public Ret(ResultEnums resultEnums, T data) {
        this.code = resultEnums.getCode();
        this.msg = resultEnums.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
        if (RequestContext.getRequestInfo() != null) {
            this.requestId = String.valueOf(RequestContext.getRequestInfo().getRequestId());
        }
    }

    //构建成功返回
    public static Ret build(ResultEnums resultEnums) {
        return new Ret(resultEnums);
    }

    //构建成功返回
    public static Ret ok() {
        return new Ret(ResultEnums.SUCCESS);
    }

    //构建错误返回
    public static Ret error() {
        return new Ret(ResultEnums.ERROR);
    }

    public T getData() {
        return data;
    }

    public Ret<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Ret setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
