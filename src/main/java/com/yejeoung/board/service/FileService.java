package com.yejeoung.board.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	// 환경변수(application.properties)의 값
	@Value("${file.dir}")
	private String dir;
	
	// 파일 업로드 서비스
	public String fileUpload(MultipartFile file) {
		// file의 존재 여부 검사
		if (file.isEmpty()) return null;
		
		// Original file name을 가져 온다.
		String originalFileName = file.getOriginalFilename();
		
		// 확장자를 가져온다. (image.png)
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		// 저장할 때 이름으로 사용되는 UUID 생성
		String uuid = UUID.randomUUID().toString();
		
		// 실제로 저장되는 이름 생성
		String saveName = uuid + extension;
		
		// 파일이 저장된 실제 경로
		String savePath = dir + saveName;
		
		// 해당 파일을 실제로 해당 경로에 저장한다.
		try {
			file.transferTo(new File(dir + saveName));
		} catch(Exception e) {
			return null;
		}
		return saveName;
	}
	
	// 파일 다운로드
	public Resource fileDownload(String fileName) {
		try {
			// 해당 리소스
			return new UrlResource("file:" + dir + fileName);
		} catch(Exception e) {
			return null;
		}
	}
	
	// 이미지 출력
	public Resource getImage(String imageName) {
		try {
			// 해당 리소스
			return new UrlResource("file:" + dir + imageName);
		} catch(Exception e) {
			return null;
		}
	}
}
