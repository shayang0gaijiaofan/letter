package com.jude.sms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:39
 */


public class SmsResDTO {
    /**
     * 模板 ID
     */
    private boolean success;

    /**
     * 返回码值
     */
    private String resCode;
    /**
     * 返回码值
     */
    private String resMsg;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
