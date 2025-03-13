package com.jude.sms.api.maisui.controller;


import com.jude.sms.api.maisui.bo.*;
import com.jude.sms.api.maisui.service.SmsTemplateClientService;
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
public class SmsTemplateController {
    @Resource
    private SmsTemplateClientService smsTemplateClientService;

    @PostMapping("/createTemplate")
    public SmsResponse createTemplate(@Valid @RequestBody SmsTemplateCreate smsTemplate) {
        SmsResponse template = smsTemplateClientService.createTemplate(smsTemplate);
        return template; // 调用服务层方法创建模板
    }

    @PostMapping("/deleteTemplate")
    public SmsResponse deleteTemplate(@Valid @RequestBody SmsTemplateDelete smsTemplate) {
        SmsResponse template = smsTemplateClientService.deleteTemplate(smsTemplate);
        return template; // 调用服务层方法创建模板
    }

    @PostMapping("/listTemplate")
    public SmsResponse listTemplate(@Valid @RequestBody SmsTemplateList smsTemplate) {
        SmsResponse template = smsTemplateClientService.listTemplate(smsTemplate);
        return template; // 调用服务层方法创建模板
    }
}
