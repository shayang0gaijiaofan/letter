package com.jude.entity.dto;

import com.jude.entity.LetterTemplate;
import lombok.Data;

@Data
public class LetterTempWithFile extends LetterTemplate {
    String fileData;
}
