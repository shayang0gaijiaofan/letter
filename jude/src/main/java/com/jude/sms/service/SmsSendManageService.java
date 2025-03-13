package com.jude.sms.service;

import com.jude.sms.dto.*;
import com.jude.sms.enums.SupplierEnums;

public interface SmsSendManageService {
    /**
     * 创建短信模版
     *
     * @param smsSendReqDTO
     * @param supplierEnum
     * @return
     */
    public SmsResDTO sendMessage(SmsSendReqDTO smsSendReqDTO, SupplierEnums supplierEnum);
}
