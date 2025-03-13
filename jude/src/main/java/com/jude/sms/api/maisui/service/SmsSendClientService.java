package com.jude.sms.api.maisui.service;

import com.jude.sms.api.danmi.bo.*;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:48
 */

public interface SmsSendClientService  {

    public SmsSendResponse sendV1(SmsSendV1 smsSendV1);

    public SmsSendResponse sendV2(SmsSendV2 smsSendV2);

    public SmsSendVBatchResponse sendBatchV1(SmsSendVBatch smsSendVBatch);

}
