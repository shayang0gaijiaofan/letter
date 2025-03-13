package com.jude.sms.api.maisui.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.jude.sms.api.maisui.bo.*;

import com.jude.sms.api.maisui.client.MaisuiHttpClient;
import com.jude.sms.api.maisui.enums.SMSMaisuiUrlEnums;
import com.jude.sms.api.maisui.service.SmsTemplateClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-27 15:26
 */
@Service("masui")
@Slf4j
public class SmsTemplateClientServiceImpl implements SmsTemplateClientService {

    @Resource
    private MaisuiHttpClient maisuiHttpClient;

    /**
     * 创建短信模板
     */
    public SmsResponse createTemplate(SmsTemplateCreate smsTemplate) {
        // 发送POST请求
        String response = maisuiHttpClient.getStringResponseEntity(smsTemplate, SMSMaisuiUrlEnums.TEMPLATE_CREATE);
        // 解析API响应并返回
        SmsResponse smsResponse = JSONObject.parseObject(response, SmsResponse.class);
        return smsResponse;
    }

    /**
     * 删除短信模板
     */
    public SmsResponse deleteTemplate(SmsTemplateDelete smsTemplate) {
        // 调用API接口删除模板的逻辑
        String response = maisuiHttpClient.getStringResponseEntity(smsTemplate, SMSMaisuiUrlEnums.TEMPLATE_DELETE);
        SmsResponse smsResponse = JSONObject.parseObject(response, SmsResponse.class);

        return smsResponse;
    }

    /**
     * 短信模板列表
     *
     * @param smsTemplate
     */
    @Override
    public SmsResponse<SmsPageRes<SmsTemplateListResponse>> listTemplate(SmsTemplateList smsTemplate) {
        String response = maisuiHttpClient.getStringResponseEntity(smsTemplate, SMSMaisuiUrlEnums.TEMPLATE_LIST);
        SmsResponse<SmsPageRes<SmsTemplateListResponse>>  smsResponse = JSONObject.parseObject(response, SmsResponse.class);
        return smsResponse;
    }
}
