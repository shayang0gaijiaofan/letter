package com.jude.sms.template.service.impl;

import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.enums.VerifyStatusEnums;
import com.jude.sms.template.entity.SmsTemplateEntity;
import com.jude.sms.template.repository.SmsTemplateRepository;
import com.jude.sms.template.service.SmsTemplateSpecialQueryService;
import com.jude.sms.template.vo.SmsTemSuplierVO;
import com.jude.sms.template.vo.SmsTemSupportVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-21 02:10
 */
@Service
public class SmsTemplateSpecialQueryServiceImpl implements SmsTemplateSpecialQueryService {

    @Resource
    SmsTemplateRepository smsTemplateRepository;

    @Override
    public SmsTemSupportVO  querySendSupportTemplateList(@NotNull String temId,@NotNull String supplier) {
        List<SmsTemplateEntity> supports = smsTemplateRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(cb.equal(root.get("temId"), temId));
            predicates.add(cb.equal(root.get("supplier"), supplier));
            predicates.add(cb.equal(root.get("verifyStatus"), VerifyStatusEnums.APPROVED.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        });

        if (CollectionUtils.isEmpty(supports)) {
            return null;
        }

        List<SmsTemSuplierVO> collect = supports.stream().map(item -> {
            SmsTemSuplierVO smsTemSuplierVO = new SmsTemSuplierVO();

            smsTemSuplierVO.setSmsTemId(item.getTemplateid());
            smsTemSuplierVO.setSuplier(item.getSupplier());
            smsTemSuplierVO.setSuplierName(SupplierEnums.valueOf(item.getSupplier()).getCode());
            return smsTemSuplierVO;
        }).collect(Collectors.toList());
        SmsTemSupportVO smsTemSupportVO = new SmsTemSupportVO(temId,collect);
        return smsTemSupportVO;

    }

    public static void main(String[] args) {
        System.out.println(SupplierEnums.valueOf("1"));
    }
}
