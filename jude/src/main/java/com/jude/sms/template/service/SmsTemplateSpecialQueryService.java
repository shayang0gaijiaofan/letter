package com.jude.sms.template.service;

import com.jude.sms.template.vo.SmsTemSupportVO;

public interface SmsTemplateSpecialQueryService {

    SmsTemSupportVO querySendSupportTemplateList(String temId, String supplier);
}
