package com.breaker.ssi.utils.entity;

/**
 * Created with family.
 * author: cy
 * Date: 16/6/23
 * Time: 下午2:06
 * description:
 */
public enum FlagStatus {
    //启用1  停用0
    START("1"),STOP("0");

    private String status;

    FlagStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }}
