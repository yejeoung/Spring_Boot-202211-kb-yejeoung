package com.yejeoung.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yejeoung.board.dto.response.ResponseDto;
import com.yejeoung.board.dto.user.GetUserResponseDto;
import com.yejeoung.board.dto.user.PatchUserDto;
import com.yejeoung.board.dto.user.PostUserDto;
import com.yejeoung.board.dto.user.ResultResponseDto;
import com.yejeoung.board.service.UserService;

@RestController
@RequestMapping("api/user/")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("{email}")
	public ResponseDto<GetUserResponseDto> getUser(@PathVariable("email") String email) {
		return userService.getUser(email);
	}
	
	@GetMapping("")
	public ResponseDto<List<GetUserResponseDto>> getAllUser() {
		return userService.getAllUser();
	}

	@PostMapping("")
	public ResponseDto<ResultResponseDto> postUser(@RequestBody PostUserDto requestBody) {
		return userService.postUser(requestBody);
		// PostUserDto에 있는 변수명들을 포스트맨에 있는 JSON으로 입력해놓은 값을 비교해서 받아냄
		// UserService Class에 입력해놓은 회원가입이 되는 프로세서가 동작하도록 반환함
	}

	@PatchMapping("")
	public ResponseDto<GetUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody) {
		return userService.patchUser(requestBody);
	}

	@DeleteMapping("{email}")
	public ResponseDto<ResultResponseDto> deleteUser(@PathVariable("email") String email) {
		return userService.deleteUser(email);
	}
}
