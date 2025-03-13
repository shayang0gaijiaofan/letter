package com.jude.sms.api.maisui.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 15:22
 */


public class SmsResponse<T> {

    @NotNull
    private boolean success;
    /**
     * 错误码，0成功
     */
    @NotNull
    private String resultCode;
    /**
     * 错误消息
     */
    @NotNull
    private String resultMsg;
    @NotNull
    private T data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
