package com.jude.sms.api.maisui.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SMSMaisuiUrlEnums {
    LOGIN("/api/email/login","用户登录"),

    TEMPLATE_CREATE("/api/email/msg/template/sms/add", "新增短信模板"),
    TEMPLATE_DELETE("/api/email/msg/template/delete", "删除消息模板"),
    TEMPLATE_LIST("/api/email/msg/template/sms/list", "短信消息模板列表"),
    ;
    private final String operate;
    private final String desc;

}
