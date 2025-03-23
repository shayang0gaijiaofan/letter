package com.jude.sms.template.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-21 02:29
 */
@AllArgsConstructor
@Data
public class SmsTemSupportVO {
    // 模版id
    private  String temId;

    private List<SmsTemSuplierVO> sts;
}
