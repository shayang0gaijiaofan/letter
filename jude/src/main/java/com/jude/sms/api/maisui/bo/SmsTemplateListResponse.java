package com.jude.sms.api.maisui.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 11:10
 */
@Data
public class SmsTemplateListResponse {

    /**
     * 主键
     */
    @NotNull
    private Integer id;

    /**
     * 用户名
     */
    @NotNull
    private String userName;

    /**
     * 消息模板编号
     */
    @NotNull
    private String msgTemplateId;

    /**
     * 消息模板名称
     */
    @NotNull
    private String msgTemplateName;

    /**
     * 主题
     */
    private String title;

    /**
     * 内容
     */
    @NotNull
    private String content;

    /**
     * 签名
     */
    @NotNull
    private String sign;

    /**
     * 消息模板类型
     *  1短信，2彩信，3视频短信
     */
    @NotNull
    private Integer msgType;

    /**
     *  0、待审核，1、审核成功，2、审核失败
     */
    @NotNull
    private String checkStatus;

    /**
     *审核时间
     */
    private Integer checkTime;

    /**
     * 创建人
     */
    @NotNull
    private String creater;

    /**
     * 创建时间
     */
    @NotNull
    private Integer createtime;

    /**
     * 修改人
     */
    private String operator;

    /**
     * 修改时间
     */
    private Integer updatetime;

    /**
     * 审核说明
     */
    private String checkExplain;

    /**
     * 状态
     * 1正常，2禁用
     */
    @NotNull
    private Integer status;
}
