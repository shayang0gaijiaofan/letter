package com.jude.sms.api.danmi.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmsSignResponse  extends SmsResponse{
    private List<SmsSignRespData> respData;


}
