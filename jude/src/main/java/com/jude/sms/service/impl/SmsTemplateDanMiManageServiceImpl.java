package com.jude.sms.service.impl;

import com.jude.sms.api.danmi.bo.*;
import com.jude.sms.dto.*;
import com.jude.sms.enums.OperateFlagEnums;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.template.entity.SmsTemplateEntity;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.VerifyStatusEnums;
import com.jude.sms.template.repository.SmsTemplateRepository;
import com.jude.sms.api.danmi.service.SmsTemplateClientService;
import com.jude.sms.service.SmsTemplateManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 09:12
 */
@Service("templateDanMiManageService")
@Order(1)
@Slf4j
public class SmsTemplateDanMiManageServiceImpl implements SmsTemplateManageService {

    @Resource
    private SmsTemplateClientService smsTemplateClientService;

    @Resource
    private SmsTemplateRepository smsTemplateRepository;

    @Override
    public SupplierEnums getSupplierEnums() {
        return supplierEnums;
    }

    SupplierEnums supplierEnums;

    @PostConstruct
    public void init() {
        supplierEnums = SupplierEnums.DANMI;
    }

    @Override
    public SmsTemplateResDTO createTemplate(SmsTemplateCreateReqDTO smsTemplateCreateReqDTO) {
        SmsTemplateCreate smsTemplate = new SmsTemplateCreate();
        BeanUtils.copyProperties(smsTemplateCreateReqDTO, smsTemplate);
        log.info("模版申请[{}]",smsTemplate);
        SmsTemplateCreateResponse smsResponse = smsTemplateClientService.createTemplate(smsTemplate);
        if (Objects.nonNull(smsResponse) && RespCodeEnum.SUCCESS.getCode().equals(smsResponse.getRespCode())) {
            SmsTemplateEntity entity = new SmsTemplateEntity();
            BeanUtils.copyProperties(smsResponse, entity);
            BeanUtils.copyProperties(smsTemplateCreateReqDTO, entity);
            entity.setSupplier(supplierEnums.getCode());
            entity.setVerifyStatus(VerifyStatusEnums.PENDING.getCode());
            entity.setVersion(1);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            smsTemplateRepository.save(entity);
        }
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        smsTemplateResDTO.setRespCode( smsResponse.getRespCode());
        smsTemplateResDTO.setRespDesc( smsResponse.getRespDesc());
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO updateTemplate(SmsTemplateUpdateReqDTO smsTemplateUpdateReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();

        //  查询是否有模版
        SmsTemplateEntity smsTemplateEntity = smsTemplateRepository.findOne((root, query, cb) -> {
            Predicate temId = cb.equal(root.get("temId").as(Long.class), smsTemplateUpdateReqDTO.getId());
            Predicate isDeleted = cb.equal(root.get("isDeleted").as(Boolean.class), false);
            return cb.and(temId, isDeleted);
        });
        if (Objects.isNull(smsTemplateEntity)) {
            smsTemplateResDTO.setRespCode("9999");
            smsTemplateResDTO.setRespDesc("非本系统的短信模版id");
            return smsTemplateResDTO;
        }

        //  校验本地状态是否可编辑
        if (!VerifyStatusEnums.REJECTED.getCode().equals(smsTemplateEntity.getVerifyStatus())) {
            smsTemplateResDTO.setRespCode("0001");
            smsTemplateResDTO.setRespDesc("审核拒绝的模板才能修改");
            return smsTemplateResDTO;
        }

        //  查询外部服务模版以及状态
        SmsTemplateQuery smsTemplateQueryReq = new SmsTemplateQuery();
        smsTemplateQueryReq.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateQueryResponse smsTemplateQueryResponse = smsTemplateClientService.queryTemplate(smsTemplateQueryReq);

        // 外部服务不存在或 非待审核状态 无法修改
        if (Objects.isNull(smsTemplateQueryResponse) || !VerifyStatusEnums.REJECTED.getCode().equals(smsTemplateQueryResponse.getVerifyStatus())) {
            BeanUtils.copyProperties(smsTemplateQueryResponse, smsTemplateEntity, "templateSign");
            smsTemplateEntity.setUpdateTime(new Date());
            // 同步外部服务状态
            smsTemplateRepository.save(smsTemplateEntity);
            smsTemplateResDTO.setRespCode("0001");
            smsTemplateResDTO.setRespDesc("审核拒绝的模板才能修改or外部服务短信模版查询失败");
            return smsTemplateResDTO;
        }

        // 尝试更新
        SmsTemplateUpdate smsTemplateUpdateReq = new SmsTemplateUpdate();
        smsTemplateUpdateReq.setAccountId(smsTemplateEntity.getAccountId());
        smsTemplateUpdateReq.setTemplateSign(smsTemplateEntity.getTemplateSign());
        smsTemplateUpdateReq.setTemplateContent(smsTemplateUpdateReqDTO.getTemplateContent());
        smsTemplateUpdateReq.setTemplateName(smsTemplateUpdateReqDTO.getTemplateName());
        smsTemplateUpdateReq.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateUpdateResponse smsTemplateUpdateResponse = smsTemplateClientService.updateTemplate(smsTemplateUpdateReq);

        // 更新失败
        if (Objects.isNull(smsTemplateUpdateResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateUpdateResponse.getRespCode())) {
            smsTemplateResDTO.setRespCode( smsTemplateUpdateResponse.getRespCode());
            smsTemplateResDTO.setRespDesc( smsTemplateUpdateResponse.getRespDesc());
            return smsTemplateResDTO;
        }

        // 更新成功
        BeanUtils.copyProperties(smsTemplateUpdateReqDTO, smsTemplateEntity);
        smsTemplateEntity.setVerifyStatus(VerifyStatusEnums.PENDING.getCode());
        smsTemplateEntity.setUpdateTime(new Date());
        smsTemplateRepository.save(smsTemplateEntity);

        smsTemplateResDTO.setRespCode( smsTemplateUpdateResponse.getRespCode());
        smsTemplateResDTO.setRespDesc( smsTemplateUpdateResponse.getRespDesc());
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO deleteTemplate(SmsTemplateDeleteReqDTO smsTemplateDeleteReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        //  查询是否有模版
        SmsTemplateEntity smsTemplateEntity = smsTemplateRepository.findOne((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("id").as(Long.class), smsTemplateDeleteReqDTO.getId()));
            predicates.add(cb.equal(root.get("isDeleted").as(Boolean.class), false));
            predicates.add(cb.equal(root.get("supplier").as(String.class), supplierEnums.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        });

        if (Objects.isNull(smsTemplateEntity)) {
            smsTemplateResDTO.setRespCode("000");
            smsTemplateResDTO.setRespDesc("已删除");
            return smsTemplateResDTO;
        }

        //  查询外部服务模版以及状态
        SmsTemplateQuery smsTemplateQueryReq = new SmsTemplateQuery();
        smsTemplateQueryReq.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateQueryResponse smsTemplateQueryResponse = smsTemplateClientService.queryTemplate(smsTemplateQueryReq);

        // 外部服务不存在或 非待审核状态 无法修改
        if (Objects.isNull(smsTemplateQueryResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateQueryResponse.getRespCode())) {
            smsTemplateResDTO.setRespCode( smsTemplateQueryResponse.getRespCode());
            smsTemplateResDTO.setRespDesc( smsTemplateQueryResponse.getRespDesc());
            return smsTemplateResDTO;
        }

        SmsTemplateDelete smsTemplate = new SmsTemplateDelete();
        smsTemplate.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateDeleteResponse smsTemplateDeleteResponse = smsTemplateClientService.deleteTemplate(smsTemplate);
        if (Objects.isNull(smsTemplateDeleteResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateDeleteResponse.getRespCode())) {
            smsTemplateResDTO.setRespCode( smsTemplateDeleteResponse.getRespCode());
            smsTemplateResDTO.setRespDesc( smsTemplateDeleteResponse.getRespDesc());
            return smsTemplateResDTO;
        }
        //  校验本地状态是否可删除
        smsTemplateEntity.setIsDeleted(true);
        smsTemplateRepository.save(smsTemplateEntity);
        smsTemplateResDTO.setRespCode( smsTemplateDeleteResponse.getRespCode());
        smsTemplateResDTO.setRespDesc( smsTemplateDeleteResponse.getRespDesc());
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO queryTemplate(SmsTemplateQueryReqDTO smsTemplateQueryReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();

        SmsTemplateEntity templateEntity = smsTemplateRepository.findOne((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 判断 id 是否传入
            if (smsTemplateQueryReqDTO.getId() != null) {
                predicates.add(cb.equal(root.get("id").as(Long.class), smsTemplateQueryReqDTO.getId()));
            }

            // 判断 temId 是否传入
            if (smsTemplateQueryReqDTO.getTemId() != null) {
                predicates.add(cb.equal(root.get("supplier").as(String.class), smsTemplateQueryReqDTO.getSupplier()));
                predicates.add(cb.equal(root.get("temId").as(String.class), smsTemplateQueryReqDTO.getTemId()));
            }
            predicates.add(cb.equal(root.get("isDeleted").as(Boolean.class), false));

            return cb.and(predicates.toArray(new Predicate[0]));
        });
        if (Objects.isNull(templateEntity)) {
            smsTemplateResDTO.setRespCode("0001");
            smsTemplateResDTO.setRespDesc("未查询到该id模版");
            return smsTemplateResDTO;
        }
        BeanUtils.copyProperties(templateEntity, smsTemplateResDTO);
        smsTemplateResDTO.setRespCode("0000");
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO updateTemplateAuth(@Valid SmsTemplateAuthReqDTO smsTemplateAuthReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        //  查询是否有模版
        SmsTemplateEntity smsTemplateEntity = smsTemplateRepository.findOne((root, query, cb) -> {
            Predicate id = cb.equal(root.get("id").as(Long.class), smsTemplateAuthReqDTO.getId());
            Predicate isDeleted = cb.equal(root.get("isDeleted").as(Boolean.class), false);
            return cb.and(id, isDeleted);
        });
        if (Objects.isNull(smsTemplateEntity)) {
            smsTemplateResDTO.setRespCode("9999");
            smsTemplateResDTO.setRespDesc("改id模版不存在");
            return smsTemplateResDTO;
        }

        //  查询外部服务模版以及状态
        SmsTemplateQuery smsTemplateQueryReq = new SmsTemplateQuery();
        smsTemplateQueryReq.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateQueryResponse smsTemplateQueryResponse = smsTemplateClientService.queryTemplate(smsTemplateQueryReq);

        // 外部服务不存在或 非待审核状态 无法修改
        if (Objects.isNull(smsTemplateQueryResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateQueryResponse.getRespCode())) {
            smsTemplateResDTO.setRespCode( smsTemplateQueryResponse.getRespCode());
            smsTemplateResDTO.setRespDesc( smsTemplateQueryResponse.getRespDesc());
            return smsTemplateResDTO;
        }

        SmsTemplateAuth smsTemplate = new SmsTemplateAuth();
        smsTemplate.setTemplateAuth(smsTemplateAuthReqDTO.getTemplateAuth());
        smsTemplate.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateAuthResponse smsTemplateAuthResponse = smsTemplateClientService.updateTemplateAuth(smsTemplate);
        if (Objects.isNull(smsTemplateAuthResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateAuthResponse.getRespCode())) {
            smsTemplateEntity.setTemplateAuth(Integer.parseInt(smsTemplateAuthReqDTO.getTemplateAuth()));
            smsTemplateRepository.save(smsTemplateEntity);
        }
        smsTemplateResDTO.setRespCode( smsTemplateAuthResponse.getRespCode());
        smsTemplateResDTO.setRespDesc( smsTemplateAuthResponse.getRespDesc());
        return smsTemplateResDTO;
    }
}
