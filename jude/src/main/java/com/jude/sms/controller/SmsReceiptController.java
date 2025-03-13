package com.jude.sms.controller;

import com.jude.sms.api.bo.*;
import com.jude.sms.api.service.impl.SmsReceiptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 14:35
 */
@RestController
@RequestMapping("/sms")
public class SmsReceiptController {
    @Resource
    private SmsReceiptService smsReceiptService;

    @PostMapping("/receiptPullDr")
    public SmsReceiptResponse<SmsReceiptSmsResult> receiptPullDr(@Valid @RequestBody SmsReceiptPullDr smsReceipt) {
        return smsReceiptService.receiptPullDr(smsReceipt);
    }

    @PostMapping("/receiptPull")
    public SmsReceiptResponse<SmsReceiptSmsResult> receiptPull(@Valid @RequestBody SmsReceipt smsReceipt) {
        return smsReceiptService.receiptPull(smsReceipt);
    }

    @PostMapping("/receiptPullMo")
    public SmsReceiptMoPullResponse receiptPullMo(@Valid @RequestBody SmsReceiptMoPull smsReceipt) {
        return smsReceiptService.receiptPullMo(smsReceipt);
    }

    @PostMapping("/receiptMoPull")
    public SmsReceiptMoPullResponse receiptMoPull(@Valid @RequestBody SmsReceipt smsReceipt) {
        return smsReceiptService.receiptMoPull(smsReceipt );
    }


}
