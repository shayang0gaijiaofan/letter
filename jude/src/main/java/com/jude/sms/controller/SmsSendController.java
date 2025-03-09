package com.jude.sms.controller;

import com.jude.sms.bo.*;
import com.jude.sms.service.SmsSendService;
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
public class SmsSendController {
    @Resource
    private SmsSendService smsSendService;

    @PostMapping("/sendV1")
    public SmsSendResponse sendV1(@Valid @RequestBody SmsSendV1 smsSendV1) {
        return smsSendService.sendV1(smsSendV1);
    }

    @PostMapping("/sendV2")
    public SmsSendResponse sendV2(@Valid @RequestBody SmsSendV2 smsSendV2) {
        return smsSendService.sendV2(smsSendV2);
    }

    @PostMapping("/sendBatchV1")
    public SmsSendVBatchResponse sendBatchV1(@Valid @RequestBody SmsSendVBatch smsSendVBatch) {
        return smsSendService.sendBatchV1(smsSendVBatch);
    }


}
