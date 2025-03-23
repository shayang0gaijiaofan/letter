package com.jude.sms.record.send.bo;

import lombok.Data;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-19 03:49
 */
@Data
public class SmsSendBO {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 本地函件发送记录
     */
    @Deprecated
    private String sendId;
    /**
     * 平台短信发送记录
     */
    private String smsId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 发送状态
     */
    private String status;
    /**
     * 操作账户
     */
    private String operationAccount;
    /**
     * 消息类型
     */
    @Deprecated
    private String messageType;
    /**
     * 函件id 待定
     */
    private String letterId;
    /**
     * 纸峰短信模版模版序列号
     */
    private Integer temId;
    /**
     * 平台模版序列号
     */
    private Integer smsTemId;
    /**
     * 供应商
     */
    private String suplier;
    /**
     * 短信内容
     */
    private String smsContent;
    /**
     * 短信参数
     */
    private String smsParams;
}