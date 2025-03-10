package com.jude.sms.api.service;

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

public interface SmsSendClientService  {

    public SmsSendResponse sendV1(SmsSendV1 smsSendV1);

    public SmsSendResponse sendV2(SmsSendV2 smsSendV2);

    public SmsSendVBatchResponse sendBatchV1(SmsSendVBatch smsSendVBatch);

}
