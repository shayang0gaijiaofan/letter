package com.jude.sms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 13:44
 */
@Data
public class SmsTemplateQueryReqDTO {


    private Integer id;

    private Integer temId;

    private String supplier;
}
