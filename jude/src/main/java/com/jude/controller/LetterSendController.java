package com.jude.controller;

import com.jude.entity.*;
import com.jude.service.*;
import com.jude.sms.dto.SmsResDTO;
import com.jude.sms.dto.SmsSendReqDTO;
import com.jude.sms.dto.SmsTemplateQueryReqDTO;
import com.jude.sms.dto.SmsTemplateResDTO;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.service.SmsSendManageService;
import com.jude.sms.service.SmsTemplateManageService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 函件发送
 *
 * @author jude
 */
@RestController
@RequestMapping("/letterSend")
public class LetterSendController {

    @Resource
    private LetterSendService letterSendService;
    @Resource
    private SmsSendManageService smsSendManageService;

    @Resource
    @Qualifier("templateDanMiManageService")
    private SmsTemplateManageService smsTemplateManageService;

    @Resource
    private LogService logService;


    /**
     * 分页查询供应商信息
     *
     */
    @RequestMapping("/sendSMS")
    //@RequiresPermissions(value = { "供应商管理" })
    public Map<String, Object> sendSMS(@Valid @RequestBody SmsSendReqDTO smsSendReqDTO, String supplier) {
        // 1 通过本地模版id、选择的短信运营商 去查短信平台的id
        SmsTemplateQueryReqDTO smsTemplate = new SmsTemplateQueryReqDTO();
        smsTemplate.setTemId(smsSendReqDTO.getTemId());
        smsTemplate.setSupplier(SupplierEnums.DANMI.getCode());
        SmsTemplateResDTO smsTemplateResDTO = smsTemplateManageService.queryTemplate(smsTemplate);
        //  todo 加模版校验
        // 2 构建对应模版需要的参数
        smsSendReqDTO.setTemplateid(smsTemplateResDTO.getTemplateid());
        smsSendReqDTO.setParam(smsSendReqDTO.getParam()+","+"http://ysqd.natapp1.cc/pic/image?id="+smsSendReqDTO.getLetter());
        SmsResDTO smsResDTO = smsSendManageService.sendMessage(smsSendReqDTO, SupplierEnums.DANMI);
        // 3 根据返回结果 在发送列表改状态

        // 4 组装返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", smsResDTO.getSuccess());
        resultMap.put("code", smsResDTO.getResCode());
        resultMap.put("errorInfo", smsResDTO.getResMsg());
        return resultMap;
    }

    /**
     * 分页查询供应商信息
     *
     * @param LetterSend
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public Map<String, Object> list(LetterSend LetterSend, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        List<LetterSend> LetterSendList = letterSendService.list(LetterSend, page, rows, Sort.Direction.ASC, "id");
        Long total = letterSendService.getCount(LetterSend);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", LetterSendList);
        resultMap.put("total", total);
        //logService.save(new Log(Log.SEARCH_ACTION,"查询供应商信息")); // 写入日志
        return resultMap;
    }


    /**
     * 添加或者修改供应商信息
     *
     * @param LetterSend
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = {"供应商管理"})
    public Map<String, Object> save(LetterSend LetterSend) throws Exception {
        if (LetterSend.getId() != null) { // 写入日志
            logService.save(new Log(Log.UPDATE_ACTION, "更新供应商信息" + LetterSend));
        } else {
            logService.save(new Log(Log.ADD_ACTION, "添加供应商信息" + LetterSend));
        }
        Map<String, Object> resultMap = new HashMap<>();
        letterSendService.save(LetterSend);
        resultMap.put("success", true);
        return resultMap;
    }


    /**
     * 删除供应商信息
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    //@RequiresPermissions(value = { "供应商管理" })
    public Map<String, Object> delete(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            //logService.save(new Log(Log.DELETE_ACTION,"删除供应商信息"+ letterSendService.findById(id)));  // 写入日志
            letterSendService.delete(id);
        }
        resultMap.put("success", true);
        return resultMap;
    }
}
