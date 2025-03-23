package com.jude.sms.record.send.service;

import com.jude.sms.record.send.bo.SmsSendBO;

import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-19 03:43
 */

public interface SmsSendService {

    /**
     * 记录发送消息的时间状态等
     * @param smsSendBO
     */

    public void saveSecdRecord(SmsSendBO smsSendBO);

    /**
     * 短信发送消息查询记录
     * @param smsSendBO
     * @return
     */
    public List<SmsSendBO> listSendRecord(SmsSendBO smsSendBO);
}
