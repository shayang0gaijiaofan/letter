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
    public SmsReceiptResponse<SmsReceiptSmsResult> receiptPullDr(SmsReceiptPullDr smsReceipt) {
        String stringResponseEntity = apiHttpClient.getFormUrlEncodedResponse(smsReceipt, SMServiceEnums.RECEIPT_PUL_DR);
        SmsReceiptResponse<SmsReceiptSmsResult> smsReceiptResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptResponse.class);
        return smsReceiptResponse;
    }

    public SmsReceiptResponse<SmsReceiptSmsResult> receiptPull(SmsReceipt smsReceipt) {
        String stringResponseEntity = apiHttpClient.getStringResponseEntity(smsReceipt, SMServiceEnums.RECEIPT_PULL);
        SmsReceiptResponse<SmsReceiptSmsResult> smsReceiptResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptResponse.class);
        return smsReceiptResponse;
    }
    /**
     * 短信上行拉取接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptMoPullResponse receiptPullMo(SmsReceiptMoPull smsReceipt) {
        String stringResponseEntity = apiHttpClient.getFormUrlEncodedResponse(smsReceipt, SMServiceEnums.RECEIPT_PULL_MO);
        SmsReceiptMoPullResponse smsReceiptMoPullResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptMoPullResponse.class);
        return smsReceiptMoPullResponse;
    }

//    public static void main(String[] args) {
//        String stringResponseEntity = "{\"respCode\":\"0000\",\"respDesc\":\"请求成功。\",\"moResult\":[{\"phone\":\"19117251744\",\"content\":\"OK\",\"timestamp\":1741786121069,\"sig\":null,\"accountId\":\"100727822613\",\"moport\":\"001\",\"motime\":\"2025-03-12 20:41:36\"}],\"success\":true}";
//        SmsReceiptMoPullResponse smsReceiptMoPullResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptMoPullResponse.class);
//        System.out.println(smsReceiptMoPullResponse);
//    }
    /**
     * 短信上行拉取接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptMoPullResponse receiptMoPull(SmsReceipt smsReceipt) {
        String stringResponseEntity = apiHttpClient.getStringResponseEntity(smsReceipt, SMServiceEnums.RECEIPT_MO_PULL);
        SmsReceiptMoPullResponse smsReceiptResponse = JSONObject.parseObject(stringResponseEntity, SmsReceiptMoPullResponse.class);
        return smsReceiptResponse;
    }


}
