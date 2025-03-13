package com.jude.sms.api.danmi.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsSendVBatch extends SmsAuth {
    @NotNull
    private String accountId; //业务账号  是 2-18 个数字；业务账号，在用户中心-文本短信-业务账号中获取
    @NotNull
    private List<SmsBatchCell> dataList; //  data 数组，不超过 100 个

}
