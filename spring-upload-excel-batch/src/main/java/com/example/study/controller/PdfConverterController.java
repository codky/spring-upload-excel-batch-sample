package com.example.study.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PdfConverterController {

	private final String uploadDir = System.getProperty("user.dir") + "/dev/pdf-images/";

    @GetMapping("/pdf/convert")
    public String showPdfUploadPage() {
        return "pdf_upload";
    }

    @PostMapping("/pdf/convert")
    public String handlePdfUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        List<String> imagePaths = new ArrayList<>();

        if (!file.isEmpty()) {
            // 저장 디렉토리 생성
            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("PDF 저장 폴더 생성 실패: " + uploadDir);
            }

            // 파일명 정리 및 저장
            String originalName = file.getOriginalFilename();
            String safeName = originalName.replaceAll("[\\\\/:*?\"<>|\\[\\]]", "_");
            String originalPath = uploadDir + safeName;
            File savedFile = new File(originalPath);
            file.transferTo(savedFile); // 실제 저장

            // PDF → 이미지 변환
            try (PDDocument document = PDDocument.load(savedFile)) {
                PDFRenderer renderer = new PDFRenderer(document);
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    BufferedImage image = renderer.renderImageWithDPI(i, 200);
                    String imageName = "page_" + System.currentTimeMillis() + "_" + i + ".png";
                    File imageFile = new File(uploadDir + imageName);
                    ImageIO.write(image, "png", imageFile);
                    imagePaths.add("/pdf-images/" + imageName);
                }
            }

            model.addAttribute("images", imagePaths);
        }

        return "pdf_result";
    }
}
