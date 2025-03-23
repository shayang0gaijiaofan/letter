package com.jude.util;

import com.jude.common.ResponseEntity;
import com.jude.config.UploadFileConfig;
import com.jude.entity.dto.WordUpdateData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtil {

    @Autowired
    UploadFileConfig uploadFileConfig;

    public static boolean isWordFile(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (fileExtension.equals("doc") ||
                fileExtension.equals("docx") ||
                fileExtension.equals("dot") ||
                fileExtension.equals("dotx") ||
                fileExtension.equals("docm") ||
                fileExtension.equals("dotm"))
            return true;
        return false;
    }
    
    public static WordUpdateData saveWordRelatedFile(String wordUUID, String fileName, String filePrefix) throws IOException {
        String picUUID = UUIDUtil.generateUUID() + ".jpg";
        String pdfUUID = UUIDUtil.generateUUID() + ".pdf";
        WordUpdateData wordUpdateData = new WordUpdateData();
        wordUpdateData.setWordUUIDName(wordUUID);
        wordUpdateData.setPicUUIDName(picUUID);
        wordUpdateData.setOriginWordName(fileName);

        // 将word转为pdf，再转为图片
        WordUtil.doc2pdf(filePrefix + wordUUID, filePrefix + pdfUUID);
        PDF2Pic.convertPdfUrl2Jpg(filePrefix + pdfUUID, filePrefix + picUUID);
        // 删除pdf
        deleteFile(filePrefix + pdfUUID);

        return wordUpdateData;
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }
}
