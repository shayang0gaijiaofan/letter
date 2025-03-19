package com.jude.controller;

import com.jude.common.ResponseEntity;
import com.jude.config.UploadFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/File")
public class FileController {

    @Autowired
    UploadFileConfig uploadFileConfig;

    @PostMapping("/uploadFile")
    public ResponseEntity fileUpLoadController(MultipartFile file) throws IOException {
//        System.out.println(file.getOriginalFilename());
        file.transferTo(new File(uploadFileConfig.getFilePath() + file.getOriginalFilename()));
        return ResponseEntity.ok(file.getOriginalFilename());
    }
}
