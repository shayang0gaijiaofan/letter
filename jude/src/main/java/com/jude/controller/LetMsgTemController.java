package com.jude.controller;

import com.alibaba.fastjson2.JSONArray;
import com.jude.entity.LetMsgTem;
import com.jude.entity.Log;
import com.jude.entity.dto.LetMsgTemWithTime;
import com.jude.repository.LetMsgTemRepository;
import com.jude.service.LetMsgTemService;
import com.jude.service.LogService;
import com.jude.sms.dto.SmsTemplateCreateReqDTO;
import com.jude.sms.dto.SmsTemplateDeleteReqDTO;
import com.jude.sms.dto.SmsTemplateResDTO;
import com.jude.sms.dto.SmsTemplateUpdateReqDTO;
import com.jude.sms.enums.RespCodeEnum;
import com.jude.sms.enums.SmsTemplateAuthEnum;
import com.jude.sms.service.SmsTemplateManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.*;

/**
 * 函件发送
 *
 * @author jude
 */
@RestController
@RequestMapping("/letMsgTem")
@Slf4j
@Validated
public class LetMsgTemController {

    @Resource
    private LetMsgTemService letterMsgService;

    @Resource
    private LetMsgTemRepository letMsgTemRepository;

    @Resource
    private LogService logService;

    @Resource
    @Qualifier("templateDanMiManageService")
    private SmsTemplateManageService smsTemplateManageService;

    /**
     * 分页查询函件短信模版信息
     *
     * @param letterMsg
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public Map<String, Object> list(LetMsgTemWithTime letterMsg, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        List<LetMsgTem> letterMsgList = letterMsgService.list(letterMsg, page, rows, Sort.Direction.ASC, "id");
        Long total = letterMsgService.getCount(letterMsg);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", letterMsgList);
        resultMap.put("total", total);
        //logService.save(new Log(Log.SEARCH_ACTION,"查询函件短信模版信息")); // 写入日志
        return resultMap;
    }

    @RequestMapping("/listMenu")
    public List<LetMsgTem> listMenu() throws Exception {
        List<LetMsgTem> list = letMsgTemRepository.findAll();
        return list;
    }


    /**
     * 添加或者修改函件短信模版信息
     *
     * @param letMsgTem
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public Map<String, Object> save(LetMsgTem letMsgTem) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        if (letMsgTem.getId() != null) { // 写入日志
            logService.save(new Log(Log.UPDATE_ACTION, "更新函件短信模版信息" + letMsgTem));
            SmsTemplateUpdateReqDTO smsTemplateUpdateReqDTO = new SmsTemplateUpdateReqDTO();
            smsTemplateUpdateReqDTO.setId(letMsgTem.getId());
            smsTemplateUpdateReqDTO.setTemplateName(letMsgTem.getMsgTemName());
            smsTemplateUpdateReqDTO.setTemplateContent(letMsgTem.getMsfText());
            SmsTemplateResDTO template = smsTemplateManageService.updateTemplate(smsTemplateUpdateReqDTO);
            if (Objects.isNull(template) || !RespCodeEnum.SUCCESS.getCode().equals(template.getRespCode())) {
                resultMap.put("errorInfo", template.getRespDesc());
                log.error("更新失败[{}]-[{}]回滚", template.getRespCode(), template.getRespDesc());
                return resultMap;
            }
            letterMsgService.save(letMsgTem);
            letMsgTem.setUpdateTime(new Date(System.currentTimeMillis()));
            resultMap.put("success", true);
            return resultMap;
        } else {
            logService.save(new Log(Log.ADD_ACTION, "添加函件短信模版信息" + letMsgTem));
            LetMsgTem entity = letterMsgService.save(letMsgTem);
            SmsTemplateCreateReqDTO smsTemplateCreateReqDTO = new SmsTemplateCreateReqDTO();
            smsTemplateCreateReqDTO.setAccountId("");
            // 短信签名
            smsTemplateCreateReqDTO.setTemplateSign(letMsgTem.getSignature());
            // 模板名称
            smsTemplateCreateReqDTO.setTemplateName(letMsgTem.getMsgTemName());
            // 模板内容
            smsTemplateCreateReqDTO.setTemplateContent(letMsgTem.getMsfText());
            // 模板权限
            smsTemplateCreateReqDTO.setTemplateAuth(Integer.parseInt(SmsTemplateAuthEnum.SHARED.getCode()));
            //
            smsTemplateCreateReqDTO.setTemId(entity.getId());
            SmsTemplateResDTO template = smsTemplateManageService.createTemplate(smsTemplateCreateReqDTO);
            if (Objects.isNull(template) || !RespCodeEnum.SUCCESS.getCode().equals(template.getRespCode())) {
                letterMsgService.delete(entity.getId());
                resultMap.put("errorInfo", template.getRespDesc());
                log.error("保存失败[{}]-[{}]回滚", template.getRespCode(), template.getRespDesc());
                return resultMap;
            }
            letMsgTem.setCreateTime(new Date(System.currentTimeMillis()));
            letMsgTem.setUpdateTime(new Date(System.currentTimeMillis()));
            letterMsgService.save(letMsgTem);
            resultMap.put("success", true);
            return resultMap;
        }
	}


    /**
     * 删除函件短信模版信息
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    //@RequiresPermissions(value = { "函件短信模版管理" })
    public Map<String, Object> delete(@NotNull String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", false);

        String[] idsStr = ids.split(",");
		if (idsStr.length == 0) {
			resultMap.put("success", false);
			resultMap.put("errorInfo", "未提供模版id!");
			return resultMap;
		}

        List<Integer> failList = new ArrayList<>();
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            SmsTemplateDeleteReqDTO smsTemplate = new SmsTemplateDeleteReqDTO();
            smsTemplate.setId(id);
            SmsTemplateResDTO smsTemplateResDTO = smsTemplateManageService.deleteTemplate(smsTemplate);
            if (!RespCodeEnum.SUCCESS.getCode().equals(smsTemplateResDTO.getRespCode())) {
                failList.add(i);
                log.info("模版id[{}]短信平台删除异常[{}]", i, smsTemplateResDTO.getRespDesc());
                continue;
            }
            letterMsgService.delete(id);
        }
		resultMap.put("success", true);

        if (failList.size() > 0) {
            resultMap.put("errorInfo", "模版id：" + JSONArray.toJSONString(failList) + "模版删除失败");
        }

        if (idsStr.length == failList.size()) {
            resultMap.put("errorInfo", "删除异常!");
			resultMap.put("success", false);
        }
        return resultMap;
    }
}
