package com.jude.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jude.entity.LetMsgTem;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LetMsgTemWithTime extends LetMsgTem {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createEndTime;
}
