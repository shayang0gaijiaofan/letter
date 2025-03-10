package com.jude.sms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-10 11:38
 */
@Data
public class SmsTemplateAuthReqDTO {
    @NotNull
    private Integer id;
    @NotNull
    @Pattern(regexp = "[01]", message = "状态只能是 0 或 1")
    private String templateAuth;
}
