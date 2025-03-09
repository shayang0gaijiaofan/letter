package com.jude.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 09:00
 */
@Getter
@AllArgsConstructor
public enum VerifyStatusEnums {
    /**
     * 1、待审核，2、审核成功，3、审核失败 4、撤回
     */
    PENDING("1", "待审核"),
    APPROVED("2", "审核成功"),
    REJECTED("3", "审核失败"),
    WITHDRAWN("4", "撤回");

    private final String code;
    private final String desc;

    /**
     * 根据状态码获取枚举对象
     */
    public static VerifyStatusEnums fromCode(String code) {
        for (VerifyStatusEnums status : VerifyStatusEnums.values()) {
            if (status.getCode().equals(code) ) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的审核状态: " + code);
    }

}
