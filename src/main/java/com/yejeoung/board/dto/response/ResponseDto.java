package com.yejeoung.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName="set")
public class ResponseDto<D> {
	private boolean status;
	private String message;
	private D data;
	
	// 메세지 전송 성공
	public static <D> ResponseDto<D> setSuccess(String message, D data) {
		return ResponseDto.set(true, message, data);
	}
	
	// 메세지 전송 실패
	public static <D> ResponseDto<D> setFailed(String message) {
		return ResponseDto.set(false, message, null);
	}
}
