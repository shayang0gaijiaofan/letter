package com.jude.sms.service;

import com.jude.sms.dto.*;

public interface SmsTemplateManageService {
    public SmsTemplateResDTO createTemplate(SmsTemplateCreateReqDTO smsTemplateCreateReqDTO);

    public SmsTemplateResDTO updateTemplate(SmsTemplateUpdateReqDTO smsTemplate);

    public SmsTemplateResDTO deleteTemplate(SmsTemplateDeleteReqDTO smsTemplate);

    public SmsTemplateResDTO queryTemplate(SmsTemplateQueryReqDTO smsTemplate);
}
