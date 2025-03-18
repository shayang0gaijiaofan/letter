package com.jude.sms.service.impl;

import com.jude.sms.api.maisui.bo.*;
import com.jude.sms.api.maisui.service.SmsTemplateClientService;
import com.jude.sms.dto.*;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.enums.VerifyStatusEnums;
import com.jude.sms.service.SmsTemplateManageService;
import com.jude.sms.template.entity.SmsTemplateEntity;
import com.jude.sms.template.repository.SmsTemplateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 09:12
 */
//@Service("templateMaiSuiManageService")
@Order(1)
public class SmsTemplateMaiSuiManageServiceImpl implements SmsTemplateManageService {

    @Resource
    private SmsTemplateClientService smsTemplateClientService;

    @Resource
    private SmsTemplateRepository smsTemplateRepository;

    SupplierEnums supplierEnums;

    @PostConstruct
    public void init() {
        supplierEnums = SupplierEnums.MAISUI;
    }

    @Override
    public SmsTemplateResDTO createTemplate(SmsTemplateCreateReqDTO smsTemplateCreateReqDTO) {
        SmsTemplateCreate smsTemplate = new SmsTemplateCreate();
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        smsTemplate.setMsgTemplateName(smsTemplateCreateReqDTO.getTemplateName());
        smsTemplate.setContent(smsTemplateCreateReqDTO.getTemplateContent());
        SmsResponse smsResponse = smsTemplateClientService.createTemplate(smsTemplate);
        if (Objects.isNull(smsResponse) || !smsResponse.getSuccess()) {
            smsTemplateResDTO.setRespCode("9999");
            smsTemplateResDTO.setRespDesc("新增失败");
            return smsTemplateResDTO;
        }
        SmsTemplateList smsTemplateList = new SmsTemplateList();
        smsTemplateList.setMsgTemplateName(smsTemplateCreateReqDTO.getTemplateName());
        SmsPageReq page = new SmsPageReq();
        page.setPageNum("1");
        page.setPageSize("10");
        smsTemplateList.setPage(page);
        SmsResponse<SmsPageRes<SmsTemplateListResponse>> smsPageResSmsResponse = smsTemplateClientService.listTemplate(smsTemplateList);
        if (Objects.isNull(smsPageResSmsResponse)
                || !smsPageResSmsResponse.getSuccess()
                || Objects.isNull(smsPageResSmsResponse.getData())
                || CollectionUtils.isEmpty(smsPageResSmsResponse.getData().getList())) {
            smsTemplateResDTO.setRespCode("9999");
            smsTemplateResDTO.setRespDesc("查询失败");
            return smsTemplateResDTO;
        }
        Optional<SmsTemplateListResponse> first = smsPageResSmsResponse.getData().getList().stream().filter(item -> "0".equals(item.getCheckStatus())).findFirst();
        if (first.isPresent()) {
            SmsTemplateListResponse smsTemplateListResponse = first.get();
            SmsTemplateEntity entity = new SmsTemplateEntity();
            entity.setTemplateAuth(0);
            // 模版名称
            entity.setTemplateName(smsTemplateListResponse.getMsgTemplateName());
            // 模版内容
            entity.setTemplateContent(smsTemplateListResponse.getContent());
            // 模版id
            entity.setTemplateid(smsTemplateListResponse.getMsgTemplateId());
            // 模版签名
            entity.setTemplateSign(smsTemplateListResponse.getSign());
            entity.setSupplier(supplierEnums.getCode());
            entity.setVerifyStatus(VerifyStatusEnums.PENDING.getCode());
            entity.setVersion(1);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            smsTemplateRepository.save(entity);
            smsTemplateResDTO.setRespCode(RespCodeEnum.SUCCESS.getCode());
        };
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO updateTemplate(SmsTemplateUpdateReqDTO smsTemplateUpdateReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO deleteTemplate(SmsTemplateDeleteReqDTO smsTemplateDeleteReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        return smsTemplateResDTO;
    }

    /**
     * 查询模版详情
     *
     * @param smsTemplate
     * @return
     */
    @Override
    public SmsTemplateResDTO queryTemplate(SmsTemplateQueryReqDTO smsTemplate) {
        return null;
    }

    @Override
    public SmsTemplateResDTO updateTemplateAuth(@Valid SmsTemplateAuthReqDTO smsTemplateAuthReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        return smsTemplateResDTO;
    }

    @Override
    public SupplierEnums getSupplierEnums() {
        return supplierEnums;
    }
}
