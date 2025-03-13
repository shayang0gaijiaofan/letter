package com.jude.sms.api.maisui.service;

import com.jude.sms.api.maisui.bo.*;


/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 15:26
 */
public interface SmsTemplateClientService {

    /**
     * 创建短信模板
     */
    public SmsResponse createTemplate(SmsTemplateCreate smsTemplate);

    /**
     * 删除短信模板
     */
    public SmsResponse deleteTemplate(SmsTemplateDelete smsTemplate);

    /**
     * 短信模板列表
     */
    public SmsResponse<SmsPageRes<SmsTemplateListResponse>> listTemplate(SmsTemplateList smsTemplate);


}
