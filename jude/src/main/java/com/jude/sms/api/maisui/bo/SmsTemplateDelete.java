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
public class SmsTemplateDelete  {
    /**
     * 消息模板id
     */
    private String id;

    /**
     * 模板类型，1短信，2彩信，3视频短信
     */
    private String type;
}
