package com.jude.sms.api.danmi.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 11:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsTemplateAuthResponse extends SmsResponse  {
    @NotNull
    private String templateid; // 模板ID
}
