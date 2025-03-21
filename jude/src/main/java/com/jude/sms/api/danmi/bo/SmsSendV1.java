package com.jude.sms.api.danmi.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsSendV1 extends SmsAuth {

    /**
     * 模板 ID
     */
    private String templateid;

    /**
     * 短信内容
     */
    private String smsContent;

    /**
     * 手机号
     */
    @NotNull
    private String to;

    /**
     * 扩展码
     */
    private String expandId;

    /**
     * 短信变量
     */
    private String param;

    /**
     * 业务账号  2-18
     */
//    @NotNull
    private String accountId;

    /**
     * 用户自定义
     */
    private String customSmsId;

}
