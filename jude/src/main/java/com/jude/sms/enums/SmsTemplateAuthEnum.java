package com.jude.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmsTemplateAuthEnum {
    SHARED("0","共享"),
    EXCLUSIVE("1","专享"),
    ;
    private final String code;
    private final String desc;
}
