package com.jude.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-13 10:57
 */
@Getter
@AllArgsConstructor
public enum SupplierEnums {
    DANMI("1","旦米"),
    MAISUI("2","麦穗");
    private final String code;
    private final String desc;

}
