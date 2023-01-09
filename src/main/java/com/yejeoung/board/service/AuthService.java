package com.yejeoung.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yejeoung.board.dto.auth.AuthPostDto;
import com.yejeoung.board.dto.auth.LoginDto;
import com.yejeoung.board.dto.response.ResponseDto;
import com.yejeoung.board.entity.MemberEntity;
import com.yejeoung.board.repository.MemberRepository;

// service 작업하는 애라고 어노테이션을 걸어줌
// @Service : 해당 클래스가 Service 레이어 역할을 함
@Service
public class AuthService {
	
	@Autowired MemberRepository memberRepository;
	
	public String hello() {
		// Entity Class로 entity 빌드
		MemberEntity memberEntity = MemberEntity
				.builder()
				.email("abc123@gmail.com")
				.password("abc123")
				.nickname("yejeoung")
				.telNumber("010-1111-2222")
				.address("busan")
				.build();
		// 빌드한 Entity를 데이터베이스에 저장해준다.
		memberRepository.save(memberEntity);
		
		// MemberRepository가 상속받은 JpaRepository 메서드를 사용해서 데이터 검색
		MemberEntity savedMemberEntity = 
				memberRepository.findById("abc123@gmail.com").get();
		
		// MemberRepository에 작성한 커스텀 메서드를 사용한 것
		List<MemberEntity> list = memberRepository.myFindAll("abc123@gmail.com");
		System.out.println(list.toString());
		
		return savedMemberEntity.getNickname();
	}
	
	public ResponseDto<LoginDto> login(AuthPostDto dto) {
		LoginDto result = new LoginDto("JWT", 3600000);
		return ResponseDto.setSuccess("", result);
	}
}
