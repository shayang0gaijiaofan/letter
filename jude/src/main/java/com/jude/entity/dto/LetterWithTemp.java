package com.jude.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jude.entity.Letter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
public class LetterWithTemp extends Letter {
    @Column(length=200)
    private String tempName   ;// 函件模板名称

    @Column(length=200)
    private String type   ;// 函件类型
}
