package com.jude.sms.api.danmi.service;

import com.alibaba.fastjson2.JSONObject;
import com.jude.sms.api.danmi.bo.SmsSign;
import com.jude.sms.api.danmi.bo.SmsSignCodeCreate;
import com.jude.sms.api.danmi.bo.SmsSignResponse;
import com.jude.sms.api.danmi.client.ApiHttpClient;
import com.jude.sms.enums.SMServiceEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 10:48
 */

public interface SmsSignClientService {

    /**
     * 短信回执查询接口
     * @param smsSign
     * @return
     */
    public SmsSignResponse SignQuery(SmsSign smsSign) ;

    /**
     * 短信上行拉取接口
     * @param smsSign
     * @return
     */
    public SmsSignResponse SignUpPull(SmsSignCodeCreate smsSign) ;



}
