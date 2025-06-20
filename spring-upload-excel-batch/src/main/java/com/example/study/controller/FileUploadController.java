package com.example.study.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	private static final String UPLOAD_DIR = "C:/upload";
	
	@GetMapping("/upload")
	public String uploadForm() {
		return "upload";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		if (file.isEmpty()) {
			model.addAttribute("message", "파일이 비어 있습니다.");
			return "result";
		}
		
		File dir = new File(UPLOAD_DIR);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		String savePath = UPLOAD_DIR + "/" + file.getOriginalFilename();
		file.transferTo(new File(savePath));
		
		model.addAttribute("message", "업로드 성공: " + file.getOriginalFilename());
		return "result";
	}
}
