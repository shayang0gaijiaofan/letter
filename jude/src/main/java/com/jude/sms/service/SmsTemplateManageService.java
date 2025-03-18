package com.jude.sms.service;

import com.jude.sms.dto.*;
import com.jude.sms.enums.SupplierEnums;

public interface SmsTemplateManageService {
    /**
     * 创建短信模版
     * @param smsTemplateCreateReqDTO
     * @return
     */
    public SmsTemplateResDTO createTemplate(SmsTemplateCreateReqDTO smsTemplateCreateReqDTO);

    /**
     * 修改短信模版
     * @param smsTemplate
     * @return
     */
    public SmsTemplateResDTO updateTemplate(SmsTemplateUpdateReqDTO smsTemplate);

    /**
     * 删除短信模版
     * @param smsTemplate
     * @return
     */
    public SmsTemplateResDTO deleteTemplate(SmsTemplateDeleteReqDTO smsTemplate);

    /**
     * 查询模版详情
     * @param smsTemplate
     * @return
     */
    public SmsTemplateResDTO queryTemplate(SmsTemplateQueryReqDTO smsTemplate);

    /**
     * 修改模版权限
     * @param smsTemplate
     * @return
     */
    public SmsTemplateResDTO updateTemplateAuth(SmsTemplateAuthReqDTO smsTemplate);

    public SupplierEnums getSupplierEnums();
}
