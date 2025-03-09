package com.jude.sms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsSendV2 extends SmsAuth {

    /**
     * 模板 ID 模板ID，和短信内容必传一个
     */
    private String templateid;

    /**
     * 短信内容
     */
    private String smsContent;

    /**
     * 手机号 多个手机号，用英文逗号隔开 一次最多1000 个手机号
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
    private List<String> paramsList;

    /**
     * 业务账号
     */
    @NotNull
    private String accountId;

    /**
     * 用户自定义
     */
    private String customSmsId;

}
