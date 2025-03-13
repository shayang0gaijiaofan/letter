package com.jude.sms.api.danmi.bo;

import lombok.Data;

import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-01 09:40
 */
@Data
public class SmsReceiptResponse<T> extends SmsResponse {

    private List<T> smsResult;
}
