package com.jude.sms.api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.jude.sms.api.bo.*;
import com.jude.sms.api.client.ApiHttpClient;
import com.jude.sms.enums.SMServiceEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:48
 */
@Service
@Slf4j
public class SmsReceiptService {
    @Resource
    private ApiHttpClient apiHttpClient;

    private final String SUCCESS_CODE = "0000";

    /**
     * 短信回执查询接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptResponse<SmsReceiptSmsResult> receiptQuery(SmsReceipt smsReceipt) {
        String stringResponseEntity = apiHttpClient.getStringResponseEntity(smsReceipt, SMServiceEnums.RECEIPT_QUERY);
        SmsReceiptResponse<SmsReceiptSmsResult> smsReceiptResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptResponse.class);
        return smsReceiptResponse;
    }

    /**
     * 短信上行拉取接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptMoPullResponse receiptUpPull(SmsReceiptMoPull smsReceipt) {
        smsReceipt.setAccountId("100396344471");
        String stringResponseEntity = apiHttpClient.getFormUrlEncodedResponse(smsReceipt, SMServiceEnums.RECEIPT_UP_PULL);
        SmsReceiptMoPullResponse smsReceiptMoPullResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptMoPullResponse.class);
        return smsReceiptMoPullResponse;
    }

    /**
     * 短信上行拉取接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptResponse<SmsReceiptMoResult> receiptUpQuery(SmsReceipt smsReceipt) {
        smsReceipt.setAccountId("100396344471");
        String stringResponseEntity = apiHttpClient.getStringResponseEntity(smsReceipt, SMServiceEnums.RECEIPT_UP_QUERY);
        SmsReceiptResponse<SmsReceiptMoResult> smsReceiptResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptResponse.class);
        return smsReceiptResponse;
    }
}
