package com.jude.sms.schedule;

import com.jude.sms.api.danmi.bo.SmsReceipt;
import com.jude.sms.api.danmi.bo.SmsReceiptPullDr;
import com.jude.sms.api.danmi.bo.SmsReceiptResponse;
import com.jude.sms.api.danmi.bo.SmsReceiptSmsResult;
import com.jude.sms.api.danmi.service.SmsReceipClienttService;
import com.jude.sms.enums.SendStatusEnums;
import com.jude.sms.record.send.entity.SmsSend;
import com.jude.sms.record.send.repository.SmsSendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.Objects;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-19 21:16
 */
//@Component
@Slf4j
@Async
public class SmsSendStatusSchedule {

    @Resource
    SmsReceipClienttService smsReceipClienttService;

    @Resource
    SmsSendRepository smsSendRepository;

    @Scheduled(cron = "0 0/5 * * * ? ")
    void smsSendStatusReceive() {
        log.info("定时任务查询短信发送结果");
        SmsReceiptPullDr smsReceipt = new SmsReceiptPullDr();
        smsReceipt.setCount(100);
        smsReceipt.setRespDataType("JSON");
        SmsReceiptResponse<SmsReceiptSmsResult> smsReceiptSmsResultSmsReceiptResponse = smsReceipClienttService.receiptPullDr(smsReceipt);

        if (Objects.nonNull(smsReceiptSmsResultSmsReceiptResponse) && CollectionUtils.isEmpty(smsReceiptSmsResultSmsReceiptResponse.getSmsResult())) {
            return;
        }
        log.info("获取的短信记录[{}]",smsReceiptSmsResultSmsReceiptResponse.getSmsResult().size());
        smsReceiptSmsResultSmsReceiptResponse.getSmsResult().stream().forEach(item->{
            SmsSend bySmsIds = smsSendRepository.findBySmsIds(item.getSmsId());
            bySmsIds.setStatus(item.getStatus());
            bySmsIds.setReceiveTime(item.getReceiveTime());
            bySmsIds.setRespMessage(item.getRespMessage());
            bySmsIds.setChargingNum(item.getChargingNum());
            smsSendRepository.save(bySmsIds);
        });
        log.info("定时任务查询短信发送结果结束");
    }
}
