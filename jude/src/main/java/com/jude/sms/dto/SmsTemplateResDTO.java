package com.jude.sms.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-09 13:33
 */
@Data
public class SmsTemplateResDTO {
    /**
     * 模板ID
     */
    private int id;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板内容
     */
    private String templateContent;
    /**
     * 短信签名
     */
    private String templateSign;
    /**
     * 1、待审核，2、审核成功，3、审核失败 4、撤回
     */
    private String verifyStatus;
    /**
     * 结果描述
     */
    private String verifyStatusMsg;
    /**
     * 模板权限
     */
    private Integer templateAuth;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 响应结果码
     */
    private String respCode;
    /**
     * 响应描述
     */
    private String respDesc;
}
