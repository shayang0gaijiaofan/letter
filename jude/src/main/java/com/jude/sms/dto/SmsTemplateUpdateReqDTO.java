package com.jude.sms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 13:42
 */
@Data
public class SmsTemplateUpdateReqDTO {
    /**
     * 模版id
     */
    @NotNull(message = "模版id不能为空")
    private Integer id;

    /**
     * 业务账号
     */
//    @NotNull(message = "业务账号不能为空")
//    private String accountId;

    /**
     * 创建模板时返回
     */
//    @NotNull
//    private String templateid;

    /**
     * 模板名称
     */
    @NotNull
    private String templateName;

    /**
     * 模板内容
     */
    @NotNull
    @Size(max = 1000)
    private String templateContent;

    /**
     * 短信签名
     */
//    @NotNull
//    private String templateSign; //
}
