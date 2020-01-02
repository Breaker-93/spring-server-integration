package com.breaker.ssi.utils.entity;

/**
 * Created with family.
 * author: cy
 * Date: 16/6/23
 * Time: 下午2:06
 * description:
 */
public enum DelStatus {
    //正常0 逻辑删除1
    NORMAL("0"),DELETED("1");


    private String status;

    DelStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }}
