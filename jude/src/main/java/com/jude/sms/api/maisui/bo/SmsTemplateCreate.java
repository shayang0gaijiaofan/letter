package com.jude.sms.api.maisui.bo;

import com.jude.sms.api.danmi.bo.SmsAuth;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 15:21
 */

@Data
public class SmsTemplateCreate {
    /**
     * 2-20个字，请勿使用含特殊符号，如@、%、&、*等
     */
    @NotNull
    private String msgTemplateName;
    @NotNull
    /**
     * 短信内容，例如【天天向上】欢迎使用短信系统。其中{函件链接}表示函件图片打开地址。
     */
    private String content;
}
