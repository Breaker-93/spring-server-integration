package com.breaker.ssi.utils.result;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * 请求上下文
 * 主要用来生成/获取请求id等信息
 * <p>
 * 访问controller时生成
 * controller在return时候获取
 * Created by yc on 2017/3/28.
 */
public class RequestContext {
    private static ThreadLocal<RequestInfo> requestInfoTheadLocal = new ThreadLocal<>();

    private RequestContext(){}
    /**
     * 生成并设置
     *
     * @return
     */
    public static void setRequestInfo(RequestInfo requestInfo) {
//        if(requestInfo == null){
//            throw new BizCommonException("requestInfo is null");
//        }

        requestInfoTheadLocal.remove();

        long requestId = IdWorker.getId();
        requestInfo.setRequestId(requestId);

        requestInfoTheadLocal.set(requestInfo);
    }

    /**
     * 获取
     *
     * @return
     */
    public static RequestInfo getRequestInfo() {
        return requestInfoTheadLocal.get();
    }

    /**
     * 清除
     */
    public static void clear() {
        requestInfoTheadLocal.remove();
    }
}

