package com.jude.sms.api.danmi.service;

import com.jude.sms.api.danmi.bo.*;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:48
 */

public interface SmsSendClientService  {

    /**
     * 短信发送
     * @param smsSendV1
     * @return
     */
    public SmsSendResponse sendV1(SmsSendV1 smsSendV1);

    /**
     * 短信发送
     * @param smsSendV2
     * @return
     */
    public SmsSendResponse sendV2(SmsSendV2 smsSendV2);

    /**
     * 批量发送
     * @param smsSendVBatch
     * @return
     */
    public SmsSendVBatchResponse sendBatchV1(SmsSendVBatch smsSendVBatch);

}
