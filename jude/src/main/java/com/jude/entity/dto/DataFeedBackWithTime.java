package com.jude.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jude.entity.DataFeedBack;
import com.jude.entity.Letter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DataFeedBackWithTime extends DataFeedBack {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkEndTime;
}
