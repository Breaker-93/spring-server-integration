package com.breaker.ssi.utils.result;

public enum ResultEnums {
    SUCCESS("200", "操作成功"),
    LOGIN_SUCCESS("200","登录成功"),
    ERROR("400", "操作失败"),
    LOGIN_ERROR("401","登录失败"),
    UNAUTHORIZED("402", "未认证（签名错误）"),
    TOKEN_EXPIRED("402", "token已过期"),
    SYSTEM_ERROR("403", "系统异常"),
    NOT_FIND("404", "接口找不到"),
    BUSINESS_ERROR("500", "业务逻辑错误"),
    UPLOAD_SIZE_EXCEED("405", "文件上传超出最大限制");

    private String code;
    private String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
