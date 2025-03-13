package com.jude.sms.api.maisui.bo;

import lombok.Data;

import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-15 23:46
 */
@Data
public class SmsPageRes<T> {
    /**
     * 总的行数
     */
    private Integer totalCount;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 当前页记录数
     */
    private Integer curPageCount;
    /**
     * 总的页数
     */
    private Integer totalPage;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 分页
     */
    private List<T> list;
}
