package com.jude.controller;

import com.jude.common.ResponseEntity;
import com.jude.config.UploadFileConfig;
import com.jude.entity.dto.WordUpdateData;
import com.jude.util.FileUtil;
import com.jude.util.PDF2Pic;
import com.jude.util.UUIDUtil;
import com.jude.util.WordUtil;
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

    @PostMapping("/uploadWord")
    public ResponseEntity wordFileUpLoadController(MultipartFile file) throws IOException {
        // 根据文件后缀判断文件是word格式
        if (FileUtil.isWordFile(file.getOriginalFilename()))
        {
            file.transferTo(new File(uploadFileConfig.getFilePath() + file.getOriginalFilename()));
            return ResponseEntity.ok(file.getOriginalFilename());
        }
        else {
            return ResponseEntity.wrongFile("不是word格式文件！");
        }
    }

    @PostMapping("/uploadWordSavePic")
    public ResponseEntity wordPicFileController(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        // 根据文件后缀判断文件是word格式
        if (FileUtil.isWordFile(fileName))
        {
            String wordUUID = UUIDUtil.generateUUID() + "." + fileExtension;
            // 将word以UUID名称存入
            File wordFile = new File(uploadFileConfig.getFilePath() + wordUUID);
            file.transferTo(wordFile);

            return ResponseEntity.ok(FileUtil.saveWordRelatedFile(wordUUID, fileName, uploadFileConfig.getFilePath()));
        }
        else {
            return ResponseEntity.wrongFile("不是word格式文件！");
        }
    }

    @PostMapping("/existWordSavePic")
    public ResponseEntity wordPicFileController(String fileName) throws IOException {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        // 根据文件后缀判断文件是word格式
        if (FileUtil.isWordFile(fileName)) {
            return ResponseEntity.ok(FileUtil.saveWordRelatedFile(fileName, fileName, uploadFileConfig.getFilePath()));
        }
        else {
            return ResponseEntity.wrongFile("不是word格式文件！");
        }
    }

    @PostMapping("/deleteFile")
    public ResponseEntity deleteFile(String fileName) throws IOException {
        if (FileUtil.deleteFile(fileName)) {
            return ResponseEntity.ok("删除成功");
        }
        return ResponseEntity.err(500, "删除失败");
    }
}
