package com.jude.controller;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.jude.common.ResponseEntity;
import com.jude.config.UploadFileConfig;
import com.jude.common.util.pds.annotation.PreventDuplicateSubmit;
import com.jude.entity.*;
import com.jude.entity.dto.WordUpdateData;
import com.jude.service.*;
import com.jude.sms.dto.SmsResDTO;
import com.jude.sms.dto.SmsSendReqDTO;
import com.jude.sms.dto.SmsTemplateQueryReqDTO;
import com.jude.sms.dto.SmsTemplateResDTO;
import com.jude.sms.enums.SupplierEnums;
import com.jude.sms.service.SmsSendManageService;
import com.jude.sms.service.SmsTemplateManageService;
import com.jude.util.FileUtil;
import com.jude.util.StringUtil;
import com.jude.util.UUIDUtil;
import com.jude.util.WordUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
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
    private LetterTemplateService letterTemplateService;
    @Resource
    private SmsSendManageService smsSendManageService;
    @Autowired
    UploadFileConfig uploadFileConfig;

    @Resource
    private LogService logService;


    /**
     * 分页查询供应商信息
     *
     */
    @RequestMapping("/sendSMS")
    @PreventDuplicateSubmit
    public Map<String, Object> sendSMS(@Valid @RequestBody SmsSendReqDTO smsSendReqDTO) {
        // 1 通过本地模版id、选择的短信运营商 去查短信平台的id
        smsSendReqDTO.setParam(smsSendReqDTO.getParam()+","+"http://ysqd.natapp1.cc/pic/image?id="+smsSendReqDTO.getLetter());
        SmsResDTO smsResDTO = smsSendManageService.sendMessage(smsSendReqDTO, smsSendReqDTO.getSupplierEnum());
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

    @RequestMapping("/generateLetter")
    public ResponseEntity generateLetter(Integer id) throws Exception {
        LetterSend letSend = letterSendService.findById(id);
        if (letSend.getTemNum() == null) {
            return ResponseEntity.err(500, "模板编号未绑定");
        }
        LetterTemplate tmp = letterTemplateService.findByNum(letSend.getTemNum()).get(0);
        if (tmp.getWordUUID() == null) {
            return ResponseEntity.err(500, "函件模板未上传文档");
        }
        // 函件替换key-value
        HashMap<String, String> kv = new HashMap<>();
        kv.put("逾期人姓名", "张均驰");
        kv.put("合同号", "110");
        kv.put("逾期金额", "114514.14");
        kv.put("客服电话", "110");
        kv.put("出函日期", "2025-02-13");

        String wordName = tmp.getWordUUID();
        if (!FileUtil.isWordFile(wordName)) return ResponseEntity.err(500, "函件模板不是word类型文件!");
        String fileExtension = wordName.substring(wordName.lastIndexOf(".") + 1).toLowerCase();
        String newWordUUID = UUIDUtil.generateUUID() + "." + fileExtension;

        // 获取替换后的word文件
        Document doc = WordUtil.docReplaceText(uploadFileConfig.getFilePath() + wordName, kv);
        // 另存文件
        File file = new File(uploadFileConfig.getFilePath() + newWordUUID);  //新建一个空白word文档
        FileOutputStream os = new FileOutputStream(file);
        WordUtil.saveDocStream(doc, os, fileExtension);

        return ResponseEntity.ok(FileUtil.saveWordRelatedFile(newWordUUID, newWordUUID, uploadFileConfig.getFilePath()));
    }

    @Transactional
    @RequestMapping("/getPic")
    public ResponseEntity getPic(Integer id) throws Exception{
        LetterSend temp = letterSendService.findById(id);
        if (temp == null) return ResponseEntity.err(500, "请输入正确的模板发送的id");
        if (StringUtil.isEmpty(temp.getLetPic())) return ResponseEntity.err(500, "请先生成函件文件");

        return ResponseEntity.ok(temp.getLetPic());
    }

    @Transactional
    @RequestMapping("/updateFile")
    public ResponseEntity updateFile(Integer id, String picUUIDName)throws Exception{
        LetterSend temp = letterSendService.findById(id);
        temp.setLetPic(picUUIDName);
        letterSendService.save(temp);

        return ResponseEntity.ok("修改成功！");
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
