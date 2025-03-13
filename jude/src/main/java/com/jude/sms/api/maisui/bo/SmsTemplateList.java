package com.jude.sms.api.maisui.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-02-28 11:10
 */
@Data
public class SmsTemplateList {

    @NotNull
    private SmsPageReq page;
    /**
     * 消息模板编号
     */
    private String msgTemplateId;

    /**
     * 消息模板名称
     */
    private String msgTemplateName;

    /**
     * 0待审核，1审核通过，2审核未过
     */

    private String checkStatus;
    /**
     * 主题
     */
    private String title;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 开始时间
     */

    private String endDate;

}
