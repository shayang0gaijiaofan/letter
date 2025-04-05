package com.jude.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.Range;
import com.aspose.words.SaveFormat;
import com.jude.config.UploadFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.init.ResourceReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

public class WordUtil {

    public static boolean getLicense() {
        boolean result = false;
        try {
            // 获取ClassLoader的实例
            ClassLoader classLoader = ResourceReader.class.getClassLoader();
            // 使用ClassLoader获取资源文件的InputStream
            InputStream is = classLoader.getResourceAsStream("static/license/word2pdfLicense.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String wordPath, String pdfPath) {
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            File file = new File(pdfPath);  //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);                    //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Document docReplaceText(String wordPath, Map<String, String> kv) {
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(wordPath);
            Document doc = new Document(fis);
            // 执行替换

            for (String key : kv.keySet()) {
                String val = kv.get(key);
                  doc.getRange().replace(key, val);
            }
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveDocStream(Document doc, FileOutputStream os, String extension) throws Exception {
        if (extension.equals("doc")) doc.save(os, SaveFormat.DOC);
        else if (extension.equals("docx")) doc.save(os, SaveFormat.DOCX);
        else if (extension.equals("dot")) doc.save(os, SaveFormat.DOT);
        else if (extension.equals("dotx")) doc.save(os, SaveFormat.DOTX);
        else if (extension.equals("docm")) doc.save(os, SaveFormat.DOCM);
        else if (extension.equals("dotm")) doc.save(os, SaveFormat.DOTM);
    }
}
