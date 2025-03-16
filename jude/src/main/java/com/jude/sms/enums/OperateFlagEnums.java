package com.jude.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 本地是否有操作更新
 */
@Getter
@AllArgsConstructor
public enum OperateFlagEnums {
    MODIFY("0","有变动"),
    CHECK_DONE("1","更新完毕");

    private final String code;
    private final String desc;
}
