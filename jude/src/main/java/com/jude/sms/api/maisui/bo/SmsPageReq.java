package com.jude.sms.api.maisui.bo;

import lombok.Data;

import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-15 23:46
 */
@Data
public class SmsPageReq{

    /**
     * 页面大小
     */
    private String pageSize;

    /**
     * 当前页数
     */
    private String pageNum;


}
