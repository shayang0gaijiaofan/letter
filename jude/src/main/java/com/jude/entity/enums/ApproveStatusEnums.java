package com.jude.entity.enums;

import com.jude.sms.enums.VerifyStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApproveStatusEnums {
    /**
     * 1、待审核，2、审核成功，3、审核失败 4、撤回
     */
    PENDING("1", "待审核"),
    APPROVED("2", "审核通过"),
    REJECTED("3", "审核拒绝"),
    WITHDRAWN("4", "撤回"),
    OTHER("999", "未知的状态");

    private final String code;
    private final String desc;

    /**
     * 根据状态码获取枚举对象
     */
    public static ApproveStatusEnums  fromCode(String code) {
        for (ApproveStatusEnums status : ApproveStatusEnums.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return ApproveStatusEnums.OTHER;
    }
}
