package com.example.study.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/files")
	public String fileList(Model model) {
	    File folder = new File(UPLOAD_DIR);
	    List<String> fileNames = Arrays.stream(folder.listFiles())
	    		.filter(File::isFile)
	    		.map(File::getName)
	            .collect(Collectors.toList());
	    
	    System.out.println("파일 목록:");
	    fileNames.forEach(System.out::println);
	    
	    model.addAttribute("files", fileNames);
	    return "files";
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("name") String name) throws Exception {
	    Path path = Paths.get(UPLOAD_DIR).resolve(name);
	    Resource resource = new UrlResource(path.toUri());

	    if (!resource.exists()) {
	        throw new RuntimeException("파일을 찾을 수 없습니다: " + name);
	    }

	    // 한글 파일명 처리
	    String encodedName = URLEncoder.encode(resource.getFilename(), "UTF-8").replaceAll("\\+", "%20");

	    String contentDisposition = "attachment; filename=\"" + encodedName + "\"; filename*=UTF-8''" + encodedName;
	    
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
	        .body(resource);
	}
}
