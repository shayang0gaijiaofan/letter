package com.jude.sms.schedule;


import com.jude.entity.LetMsgTem;
import com.jude.entity.enums.ApproveStatusEnums;
import com.jude.service.LetMsgTemService;
import com.jude.sms.enums.OperateFlagEnums;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.template.entity.SmsTemplateEntity;
import com.jude.sms.template.repository.SmsTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 21:29
 */
@Component
@Async
@Slf4j
public class LetMsgTemStatusSchedule {

    @Resource
    SmsTemplateRepository templateRepository;

    @Resource
    private LetMsgTemService letterMsgService;

    @Scheduled(cron = "0 0/5 * * * ? ")
    public void verifyStatusConfirm() {
        log.info("定时任务-本地短信模版更新处理");
        List<SmsTemplateEntity> templateEntityList = templateRepository.findAll((root, query, cb) -> {
            Predicate operateFlag = cb.equal(root.get("operateFlag").as(String.class), OperateFlagEnums.MODIFY.getCode());
            Predicate isDeleted = cb.equal(root.get("isDeleted").as(Boolean.class), false);
            return cb.and(operateFlag, isDeleted);
        });

        if (CollectionUtils.isEmpty(templateEntityList)) {
            log.info("定时任务-本地短信模版无待更新处理");
            return;
        }
        templateEntityList.forEach(item -> {
            // 后续待修改 传入函数式接口
            if (SupplierEnums.DANMI.getCode().equals(item.getSupplier())) {
                try {
                    LetMsgTem letMsgTem = letterMsgService.findById(item.getTemId());
                    // 转换魏本地模版审核状态
                    letMsgTem.setApproveStatus(ApproveStatusEnums.fromCode(item.getVerifyStatus()).getCode());
                    // 同步短信平台模版内容
                    letMsgTem.setMsfText(item.getTemplateContent());
                    // 同步模版名称
                    letMsgTem.setMsgTemName(item.getTemplateName());
                    // 本地模版变更时间
                    letMsgTem.setUpdateTime(item.getUpdateTime());


                    // 更新本地模版
                    letterMsgService.save(letMsgTem);
                    // 修改平台模版时间
                    item.setOperateFlag(OperateFlagEnums.CHECK_DONE.getCode());
                    // 平台短信模版操作标识修改
                    templateRepository.save(item);
                } catch (Exception e) {
                    log.error("定时任务-本地短信模版[{}]无待更新处理旦米模版[{}]失败",item.getTemId(), item.getId(), e);
                }
            }
        });
        log.info("定时任务-短信模版审核状态查询结束");
    }

}
