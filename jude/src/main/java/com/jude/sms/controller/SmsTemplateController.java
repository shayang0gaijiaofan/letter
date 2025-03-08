package com.jude.sms.controller;

import com.jude.sms.dto.*;
import com.jude.sms.service.SmsTemplateManageService;
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
    private SmsTemplateManageService smsTemplateManageService;

    @PostMapping("/createTemplate")
    public SmsTemplateResDTO createTemplate(@Valid @RequestBody SmsTemplateCreateReqDTO smsTemplate) {
        return smsTemplateManageService.createTemplate(smsTemplate); // 调用服务层方法创建模板
    }

    @PostMapping("/updateTemplate")
    public SmsTemplateResDTO updateTemplate(@RequestBody SmsTemplateUpdateReqDTO smsTemplate) {
        return smsTemplateManageService.updateTemplate(smsTemplate); // 调用服务层方法更新模板
    }

    @PostMapping("/deleteTemplate")
    public SmsTemplateResDTO deleteTemplate(@RequestBody SmsTemplateDeleteReqDTO smsTemplate) {
        return smsTemplateManageService.deleteTemplate(smsTemplate); // 调用服务层方法删除模板
    }

    @PostMapping("/queryTemplate")
    public SmsTemplateResDTO queryTemplate(@RequestBody SmsTemplateQueryReqDTO smsTemplate) {
        return smsTemplateManageService.queryTemplate(smsTemplate); // 调用服务层方法查询模板
    }
}
