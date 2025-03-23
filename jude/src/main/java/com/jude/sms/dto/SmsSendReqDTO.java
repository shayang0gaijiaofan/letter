package com.jude.sms.dto;

import com.jude.sms.api.danmi.bo.SmsAuth;
import com.jude.sms.enums.SupplierEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:39
 */

@Data
public class SmsSendReqDTO  {

    /**
     * 本地模板 ID
     */
    private Integer temId;

    /**
     * 平台模板 ID
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
    @NotNull
    private String param;

    /**
     * 用户自定义
     */
    private String customSmsId;

    /**
     * 函件影像id
     */
    @NotNull
    private String letter;

    /**
     * 函件id
     */
    @NotNull
    private String letterId;
    /**
     * 运营商
     */
    @NotNull
    private String support;

    private SupplierEnums supplierEnum;

}
