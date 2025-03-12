package com.jude.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jude.entity.Letter;
import com.jude.entity.LetterTemplate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LetterTemplateWithTime extends LetterTemplate {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createEndTime;
}
