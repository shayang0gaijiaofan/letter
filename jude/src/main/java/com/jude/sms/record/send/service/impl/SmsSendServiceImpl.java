package com.jude.sms.record.send.service.impl;

import com.jude.sms.record.send.bo.SmsSendBO;
import com.jude.sms.record.send.entity.SmsSend;
import com.jude.sms.record.send.repository.SmsSendRepository;
import com.jude.sms.record.send.service.SmsSendService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-19 03:44
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Resource
    SmsSendRepository smsSendRepository;

    @Override
    public void saveSecdRecord(SmsSendBO smsSendBO) {
        SmsSend entity = new SmsSend();
        BeanUtils.copyProperties(smsSendBO, entity);

        smsSendRepository.save(entity);
    }

    /**
     * 短信发送消息查询记录
     *
     * @param smsSendBO
     * @return
     */
    @Override
    public List<SmsSendBO> listSendRecord(SmsSendBO smsSendBO) {
        if (Objects.isNull(smsSendBO)) {
            return Collections.emptyList();
        }
        List<SmsSend> smsSendList = smsSendRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(smsSendBO.getStatus())) {
                predicates.add(cb.equal(root.get("status").as(String.class), smsSendBO.getStatus()));
            }
            if (StringUtils.hasText(smsSendBO.getSmsId())) {
                predicates.add(cb.equal(root.get("smsId").as(Long.class), smsSendBO.getId()));
            }
            if (StringUtils.hasText(smsSendBO.getSuplier())) {
                predicates.add(cb.equal(root.get("suplier").as(String.class), smsSendBO.getSuplier()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        });

        if (CollectionUtils.isEmpty(smsSendList)) {
            return Collections.emptyList();
        }
        return smsSendList.stream().map(item -> {
            SmsSendBO bo = new SmsSendBO();
            BeanUtils.copyProperties(item, bo);
            return bo;
        }).collect(Collectors.toList());

    }
}
