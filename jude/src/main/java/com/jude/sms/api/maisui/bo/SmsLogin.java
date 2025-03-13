package com.jude.sms.api.maisui.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:29
 */
@Data
public class SmsLogin {
    /**
     *     MD5(MD5(用户名) + MD5(api接口密码))
     */
    @NotNull
    private String password;
    @NotNull
    private String userName;
}
