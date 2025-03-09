package com.jude.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 09:26
 */
@Getter
@AllArgsConstructor
public enum RespCodeEnum {
    SUCCESS("0000","成功");
    private final String code;
    private final String desc;
}
