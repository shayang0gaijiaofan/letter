package com.jude.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-19 16:18
 */
@Getter
@AllArgsConstructor
public enum SendStatusEnums {
    COMMIOTED("0", "成功"),
    CHECKED_SUCCESS("1", "失败"),
    WAITED("W", "等待发送");
    private final String code;
    private final String desc;
}
