package com.jude.sms.controller;


import com.jude.sms.api.danmi.bo.*;
import com.jude.sms.api.danmi.service.SmsSendClientService;
import com.jude.sms.dto.SmsSendV1ReqDTO;
import org.springframework.beans.BeanUtils;
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
    private SmsSendClientService smsSendClientService;

    @PostMapping("/sendV1")
    public SmsSendResponse sendV1(@Valid @RequestBody SmsSendV1ReqDTO SmsSendV1ReqDTO) {
        SmsSendV1 smsSendV1 = new SmsSendV1();
        BeanUtils.copyProperties(SmsSendV1ReqDTO,smsSendV1);
        return smsSendClientService.sendV1(smsSendV1);
    }

    @PostMapping("/sendV2")
    public SmsSendResponse sendV2(@Valid @RequestBody SmsSendV2 smsSendV2) {
        return smsSendClientService.sendV2(smsSendV2);
    }

    @PostMapping("/sendBatchV1")
    public SmsSendVBatchResponse sendBatchV1(@Valid @RequestBody SmsSendVBatch smsSendVBatch) {
        return smsSendClientService.sendBatchV1(smsSendVBatch);
    }


}
