package com.jude.sms.service.impl;

import com.jude.sms.api.danmi.bo.SmsSendResponse;
import com.jude.sms.api.danmi.bo.SmsSendV1;
import com.jude.sms.api.danmi.service.SmsSendClientService;
import com.jude.sms.dto.SmsResDTO;
import com.jude.sms.dto.SmsSendReqDTO;
import com.jude.sms.dto.SmsTemplateQueryReqDTO;
import com.jude.sms.dto.SmsTemplateResDTO;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.SendStatusEnums;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.record.send.bo.SmsSendBO;
import com.jude.sms.record.send.service.SmsSendService;
import com.jude.sms.service.SmsSendManageService;
import com.jude.sms.service.SmsTemplateManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("templateDanMiManageService")
    private SmsTemplateManageService smsTemplateManageService;

    @Resource
    private SmsSendClientService smsSendClientService;

    @Resource
    private SmsSendService smsSendService;

    /**
     * 创建短信模版
     *
     * @param smsSendReqDTO
     * @return
     */
    @Override
    public SmsResDTO sendMessage(SmsSendReqDTO smsSendReqDTO, SupplierEnums supplierEnum) {
        SmsTemplateQueryReqDTO smsTemplate = new SmsTemplateQueryReqDTO();
        smsTemplate.setTemId(smsSendReqDTO.getTemId());
        smsTemplate.setSupplier(supplierEnum.getCode());
        SmsTemplateResDTO smsTemplateResDTO = smsTemplateManageService.queryTemplate(smsTemplate);
        //  todo 加模版校验
        // 2 构建对应模版需要的参数
        smsSendReqDTO.setTemplateid(smsTemplateResDTO.getTemplateid());
        SmsSendV1 smsSendV1 = new SmsSendV1();
        BeanUtils.copyProperties(smsSendReqDTO, smsSendV1);
        SmsSendResponse smsSendResponse = smsSendClientService.sendV1(smsSendV1);
        SmsResDTO smsResDTO = new SmsResDTO();
        smsResDTO.setSuccess(false);
        if (Objects.nonNull(smsSendResponse) && RespCodeEnum.SUCCESS.getCode().equals(smsSendResponse.getRespCode())) {
            SmsSendBO smsSendBO = new SmsSendBO();
            // 函件发送列表id
//            smsSendBO.setSendId(smsSendResponse.getSmsId());
            // 平台短信发送记录
            smsSendBO.setSmsId(smsSendResponse.getSmsId());
            //手机
            smsSendBO.setPhone(smsSendReqDTO.getTo());
            // 状态
            smsSendBO.setStatus(SendStatusEnums.WAITED.getCode());
            // 消息类型
//            smsSendBO.setMessageType("");
            // 操作账户
            smsSendBO.setOperationAccount(smsSendV1.getAccountId());
            // 函件id
            smsSendBO.setLetterId(smsSendReqDTO.getLetterId());
            // 平台模版序列号
            smsSendBO.setSmsTemId(smsTemplateResDTO.getId());
            // 纸峰短信模版模版序列号
            smsSendBO.setTemId(smsSendReqDTO.getTemId());
            // 短信参数
            smsSendBO.setSmsParams(smsSendV1.getParam());
            // 短信内容
            smsSendBO.setSmsContent(smsSendV1.getSmsContent());
            smsResDTO.setSuccess(true);
            smsSendService.saveSecdRecord(smsSendBO);
        }
        return smsResDTO;
    }
}
