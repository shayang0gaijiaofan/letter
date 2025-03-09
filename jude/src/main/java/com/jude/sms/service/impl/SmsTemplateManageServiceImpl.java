package com.jude.sms.service.impl;

import com.jude.sms.bo.*;
import com.jude.sms.dto.*;
import com.jude.sms.entity.SmsTemplateEntity;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.VerifyStatusEnums;
import com.jude.sms.repository.SmsTemplateRepository;
import com.jude.sms.service.SmsTemplateClientService;
import com.jude.sms.service.SmsTemplateManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 09:12
 */
@Service
public class SmsTemplateManageServiceImpl implements SmsTemplateManageService {

    @Resource
    private SmsTemplateClientService smsTemplateClientService;

    @Resource
    private SmsTemplateRepository smsTemplateRepository;

    @Override
    public SmsTemplateResDTO createTemplate(SmsTemplateCreateReqDTO smsTemplateCreateReqDTO) {
        SmsTemplateCreate smsTemplate = new SmsTemplateCreate();
        BeanUtils.copyProperties(smsTemplateCreateReqDTO, smsTemplate);
        SmsTemplateCreateResponse smsResponse = smsTemplateClientService.createTemplate(smsTemplate);
        if (Objects.nonNull(smsResponse) && RespCodeEnum.SUCCESS.getCode().equals(smsResponse.getRespCode())) {
            SmsTemplateEntity entity = new SmsTemplateEntity();
            BeanUtils.copyProperties(smsResponse, entity);
            BeanUtils.copyProperties(smsTemplateCreateReqDTO, entity);
            entity.setVerifyStatus(VerifyStatusEnums.PENDING.getCode());
            entity.setVersion(1);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            smsTemplateRepository.save(entity);
        }
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        smsTemplateResDTO.setRespCode("R" + smsResponse.getRespCode());
        smsTemplateResDTO.setRespDesc("R" + smsResponse.getRespDesc());
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO updateTemplate(SmsTemplateUpdateReqDTO smsTemplateUpdateReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();

        //  查询是否有模版
        SmsTemplateEntity smsTemplateEntity = smsTemplateRepository.findOne(smsTemplateUpdateReqDTO.getId());
        if (Objects.isNull(smsTemplateEntity)) {
            smsTemplateResDTO.setRespCode("L9999");
            smsTemplateResDTO.setRespDesc("L非本系统的短信模版id");
            return smsTemplateResDTO;
        }

        //  校验本地状态是否可编辑
        if (!VerifyStatusEnums.REJECTED.getCode().equals(smsTemplateEntity.getVerifyStatus())) {
            smsTemplateResDTO.setRespCode("L0001");
            smsTemplateResDTO.setRespDesc("L审核拒绝的模板才能修改");
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
            smsTemplateResDTO.setRespCode("L0001");
            smsTemplateResDTO.setRespDesc("L审核拒绝的模板才能修改or外部服务短信模版查询失败");
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
            smsTemplateResDTO.setRespCode("R" + smsTemplateUpdateResponse.getRespCode());
            smsTemplateResDTO.setRespDesc("R" + smsTemplateUpdateResponse.getRespDesc());
            return smsTemplateResDTO;
        }

        // 更新成功
        BeanUtils.copyProperties(smsTemplateUpdateReqDTO, smsTemplateEntity);
        smsTemplateEntity.setVerifyStatus(VerifyStatusEnums.PENDING.getCode());
        smsTemplateEntity.setUpdateTime(new Date());
        smsTemplateRepository.save(smsTemplateEntity);

        smsTemplateResDTO.setRespCode("R" + smsTemplateUpdateResponse.getRespCode());
        smsTemplateResDTO.setRespDesc("R" + smsTemplateUpdateResponse.getRespDesc());
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO deleteTemplate(SmsTemplateDeleteReqDTO smsTemplateDeleteReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        //  查询是否有模版
        SmsTemplateEntity smsTemplateEntity = smsTemplateRepository.findOne(smsTemplateDeleteReqDTO.getId());

        if (Objects.isNull(smsTemplateEntity)) {
            smsTemplateResDTO.setRespCode("L9999");
            smsTemplateResDTO.setRespDesc("L非本系统的短信模版id或已删除");
            return smsTemplateResDTO;
        }

        if (smsTemplateEntity.getIsDeleted()) {
            smsTemplateResDTO.setRespCode("L0000");
            smsTemplateResDTO.setRespDesc("L已删除");
            return smsTemplateResDTO;
        }

        //  查询外部服务模版以及状态
        SmsTemplateQuery smsTemplateQueryReq = new SmsTemplateQuery();
        smsTemplateQueryReq.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateQueryResponse smsTemplateQueryResponse = smsTemplateClientService.queryTemplate(smsTemplateQueryReq);

        // 外部服务不存在或 非待审核状态 无法修改
        if (Objects.isNull(smsTemplateQueryResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateQueryResponse.getRespCode())) {
            smsTemplateResDTO.setRespCode("R" + smsTemplateQueryResponse.getRespCode());
            smsTemplateResDTO.setRespDesc("R" + smsTemplateQueryResponse.getRespDesc());
            return smsTemplateResDTO;
        }
        SmsTemplateDelete smsTemplate = new SmsTemplateDelete();
        smsTemplate.setTemplateid(smsTemplateEntity.getTemplateid());
        SmsTemplateDeleteResponse smsTemplateDeleteResponse = smsTemplateClientService.deleteTemplate(smsTemplate);
        if (Objects.isNull(smsTemplateDeleteResponse) || !RespCodeEnum.SUCCESS.getCode().equals(smsTemplateDeleteResponse.getRespCode())) {
            smsTemplateResDTO.setRespCode("R" + smsTemplateDeleteResponse.getRespCode());
            smsTemplateResDTO.setRespDesc("R" + smsTemplateDeleteResponse.getRespDesc());
            return smsTemplateResDTO;
        }
        //  校验本地状态是否可删除
        smsTemplateEntity.setIsDeleted(true);
        smsTemplateRepository.save(smsTemplateEntity);
        smsTemplateResDTO.setRespCode("R" + smsTemplateDeleteResponse.getRespCode());
        smsTemplateResDTO.setRespDesc("R" + smsTemplateDeleteResponse.getRespDesc());
        return smsTemplateResDTO;
    }

    @Override
    public SmsTemplateResDTO queryTemplate(SmsTemplateQueryReqDTO smsTemplateQueryReqDTO) {
        SmsTemplateResDTO smsTemplateResDTO = new SmsTemplateResDTO();
        SmsTemplateEntity smsTemplateEntity = new SmsTemplateEntity();
        smsTemplateEntity.setId(smsTemplateQueryReqDTO.getId());
        smsTemplateEntity.setIsDeleted(false);
        Example<SmsTemplateEntity> smsTemplateEntityExample = Example.of(smsTemplateEntity);
        SmsTemplateEntity templateEntity = smsTemplateRepository.findOne(smsTemplateEntityExample);
        if (Objects.nonNull(templateEntity)) {
            BeanUtils.copyProperties(templateEntity, smsTemplateResDTO);
            smsTemplateResDTO.setRespDesc("L0000");
        }
        return smsTemplateResDTO;
    }
}
