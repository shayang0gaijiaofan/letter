package com.jude.sms.api.danmi.service;

import com.jude.sms.api.danmi.bo.*;


public interface SmsReceipClienttService{

    /**
     * 短信回执查询接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptResponse<SmsReceiptSmsResult> receiptPullDr(SmsReceiptPullDr smsReceipt) ;

    public SmsReceiptResponse<SmsReceiptSmsResult> receiptPull(SmsReceipt smsReceipt) ;
    /**
     * 短信上行拉取接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptMoPullResponse receiptPullMo(SmsReceiptMoPull smsReceipt) ;

    /**
     * 短信上行拉取接口
     * @param smsReceipt
     * @return
     */
    public SmsReceiptMoPullResponse receiptMoPull(SmsReceipt smsReceipt);

}
