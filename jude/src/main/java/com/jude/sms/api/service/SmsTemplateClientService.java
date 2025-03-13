package com.jude.sms.api.service;

import com.jude.sms.api.bo.*;


/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 15:26
 */
public interface SmsTemplateClientService {

    // 创建短信模板
    public SmsTemplateCreateResponse createTemplate(SmsTemplateCreate smsTemplate) ;

    // 更新短信模板
    public SmsTemplateUpdateResponse updateTemplate(SmsTemplateUpdate smsTemplate) ;

    // 删除短信模板
    public SmsTemplateDeleteResponse deleteTemplate(SmsTemplateDelete smsTemplate) ;

    // 查询短信模板
    public SmsTemplateQueryResponse queryTemplate(SmsTemplateQuery smsTemplate) ;

    public SmsTemplateAuthResponse updateTemplateAuth(SmsTemplateAuth smsTemplate) ;
}
