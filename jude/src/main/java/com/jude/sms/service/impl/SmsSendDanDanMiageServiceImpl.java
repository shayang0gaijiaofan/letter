package com.jude.sms.service.impl;

import com.jude.sms.api.danmi.bo.SmsSendResponse;
import com.jude.sms.api.danmi.bo.SmsSendV1;
import com.jude.sms.api.danmi.service.SmsSendClientService;
import com.jude.sms.dto.SmsResDTO;
import com.jude.sms.dto.SmsSendReqDTO;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.service.SmsSendManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-16 05:36
 */
@Service("smsSendDanMiManageService")
public class SmsSendDanDanMiageServiceImpl implements SmsSendManageService {

    @Resource
    private SmsSendClientService smsSendClientService;

    /**
     * 创建短信模版
     *
     * @param smsSendReqDTO
     * @return
     */
    @Override
    public SmsResDTO sendMessage(SmsSendReqDTO smsSendReqDTO, SupplierEnums supplierEnum) {
        SmsSendV1 smsSendV1 = new SmsSendV1();
        BeanUtils.copyProperties(smsSendReqDTO, smsSendV1);
        SmsSendResponse smsSendResponse = smsSendClientService.sendV1(smsSendV1);
        SmsResDTO smsResDTO = new SmsResDTO();
        smsResDTO.setSuccess(false);
        if (Objects.nonNull(smsSendResponse) && RespCodeEnum.SUCCESS.getCode().equals(smsSendResponse.getRespCode())) {
            // todo 保存短信发送记录‘
            // todo 加定时任务查询短信发送的消息和落表情况
            smsResDTO.setSuccess(true);
        }
        return smsResDTO;
    }
}
