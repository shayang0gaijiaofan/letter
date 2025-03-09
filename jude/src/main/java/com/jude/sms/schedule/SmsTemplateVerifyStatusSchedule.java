package com.jude.sms.schedule;

import com.jude.sms.bo.SmsTemplateQuery;
import com.jude.sms.bo.SmsTemplateQueryResponse;
import com.jude.sms.entity.SmsTemplateEntity;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.VerifyStatusEnums;
import com.jude.sms.repository.SmsTemplateRepository;
import com.jude.sms.service.SmsTemplateClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 21:29
 */
//@Component

public class SmsTemplateVerifyStatusSchedule {

    private static final Logger log = LoggerFactory.getLogger(SmsTemplateVerifyStatusSchedule.class);

    @Resource
    SmsTemplateRepository templateRepository;

    @Resource
    private SmsTemplateClientService smsTemplateClientService;

//    @Scheduled(cron = "0 0/30 * * * ?")
//    @Scheduled(fixedRate = 5000)
    public void verifyStatusConfirm() {
        log.info("定时任务-短信模版审核状态查询开始");
        List<SmsTemplateEntity> templateEntityList = templateRepository.findByVerifyStatus(VerifyStatusEnums.PENDING.getCode());

        if (CollectionUtils.isEmpty(templateEntityList)) {
            log.info("定时任务-短信模版审核状态无待查询条数");
            return;
        }
        templateEntityList.forEach(item -> {
            SmsTemplateQuery smsTemplate = new SmsTemplateQuery();
            smsTemplate.setTemplateid(item.getTemplateid());
            SmsTemplateQueryResponse smsTemplateQueryResponse = smsTemplateClientService.queryTemplate(smsTemplate);
            if (Objects.nonNull(smsTemplateQueryResponse) &&
                    !VerifyStatusEnums.PENDING.getCode().equals(smsTemplateQueryResponse.getVerifyStatus())) {

                if (RespCodeEnum.SUCCESS.getCode().equals(smsTemplateQueryResponse.getRespCode())) {
                    BeanUtils.copyProperties(smsTemplateQueryResponse, item);
                } else {
                    item.setIsDeleted(true);
                }
                item.setUpdateTime(new Date());
                templateRepository.save(item);
                log.info("本地id[{}]模版id[{}]状态变动", item.getId(), item.getTemplateid());
            }
        });
        log.info("定时任务-短信模版审核状态查询结束");
    }

}
