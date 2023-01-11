package com.yejeoung.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yejeoung.board.service.FileService;

@RestController
@RequestMapping("file/")
public class FileControllor {

	@Autowired
	FileService fileService;

	// 파일을 서버에 업로드 (POST)
	@PostMapping("upload")
	// @RequestParam(field명) : RequestBody에서 특정 필드를 받아옴
	// Request body에 파일을 받아올 때는 MultipartFile 인스턴스로 받음
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		return fileService.fileUpload(file);
	}

	// 파일을 서버에서 다운로드 (GET)
	@GetMapping("download/{fileName}")
//	리스소스 그 자체를 쓰고 싶을 경우 ResponseEntity를 사용함
	public ResponseEntity<Resource> fileDownload(@PathVariable("fileName") String fileName) {
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.body(fileService.fileDownload(fileName));
	}
	
	// 이미지 파일일 경우 이미지를 출력 (GET)
	@GetMapping(value="image/{imageName}", produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public Resource getImage(@PathVariable("imageName") String imageName) {
		return fileService.getImage(imageName);
	}

}
