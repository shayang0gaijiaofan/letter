package com.jude.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDF2Pic {

    public static void convertPdfUrl2Jpg(String pdfFilePath, String jpgFilePath) throws IOException {
        // 加载PDF文档
        PDDocument document = PDDocument.load(new File(pdfFilePath));

        // 创建PDF渲染器
        PDFRenderer pdfRenderer = new PDFRenderer((document));

        // 遍历每一页
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            // 渲染指定页到图像
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300); // 300 DPI
            // 将图像写入文件
            ImageIO.write(bufferedImage, "JPG", new File(jpgFilePath));
        }
        // 关闭PDF文档
        document.close();
    }

    public static void convertPdf2Jpg(File file, String jpgFilePath) throws IOException {
        // 加载PDF文档
        PDDocument document = PDDocument.load(file);

        // 创建PDF渲染器
        PDFRenderer pdfRenderer = new PDFRenderer((document));

        // 遍历每一页
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            // 渲染指定页到图像
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300); // 300 DPI
            // 将图像写入文件
            ImageIO.write(bufferedImage, "JPG", new File(jpgFilePath));
        }
        // 关闭PDF文档
        document.close();
    }
}
