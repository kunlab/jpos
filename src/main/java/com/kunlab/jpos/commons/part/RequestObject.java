package com.kunlab.jpos.commons.part;

import com.kunlab.jpos.commons.secure.DataProtect;

/**
 * 数据脱敏工具类
 * @author likun
 */
public abstract class RequestObject {

    private String traceId;			// 系统跟踪号(TRACE_ID)
    private String requestModule;	// 请求模块系统编码(REQUEST_MODULE)

    @SuppressWarnings("unused")
    private RequestObject() {}

    /**
     * @param traceId:系统跟踪号(TRACE_ID)
     * @param requestModule:请求模块系统编码(REQUEST_MODULE)
     */
    public RequestObject(String traceId, String requestModule) {
        this.traceId = traceId;
        this.requestModule = requestModule;
    }

    public String getTraceId() {
        return traceId;
    }
    public String getRequestModule() {
        return requestModule;
    }

    @Override
    public String toString() {
        return DataProtect.toString(this);
    }
}
