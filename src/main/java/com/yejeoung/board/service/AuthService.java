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

	@Autowired
	MemberRepository memberRepository;

	public String hello() {
		// Entity Class로 entity 빌드
		MemberEntity memberEntity = MemberEntity.builder().email("abc123@gmail.com").password("abc123")
				.nickname("yejeoung").telNumber("010-1111-2222").address("busan").build();
		// 빌드한 Entity를 데이터베이스에 저장
		memberRepository.save(memberEntity);

		// MemberRepository가 상속받은 JpaRepository 메서드를 사용해서 데이터 검색
		MemberEntity savedMemberEntity = memberRepository.findById("abc123@gmail.com").get();

		// MemberRepository에 작성한 커스텀 메서드를 사용한 것
		List<MemberEntity> list = memberRepository.myFindAll("abc123@gmail.com");
		System.out.println(list.toString());

		return savedMemberEntity.getNickname();
	}

	public ResponseDto<LoginDto> login(AuthPostDto dto) {
		
		// 입력 받은 email으로 데이터베이스에서 검색
		String email = dto.getEmail();
		MemberEntity member;
		/* 밑에 try catch에서 member를 사용하게 되면 위에서 따로 선언을 해주어야 한다. */
		
		// 입력 받은 email이 존재하지 않는다면 없는 아이디 "로그인 실패" 반환
		try {
			member = memberRepository.findById(email).get();	
		} catch (Exception e) {
			return ResponseDto.setFailed("Login Failed");
		}
		
		// 입력 받은 email이 존재한다면 입력받은 패스워드와 데이터베이스의 패스워드와 동일한지 검사
		// 동일하지 않다면 "로그인 실패" 반환
		String password = dto.getPassword();
		String dbpassword = member.getPassword();
		/* equals 안에 함수를 넣는 것이 아닌 새로 변수를 생성해서 넣어주는 것이 좋다. */
		if (password.equals(dbpassword))
			return ResponseDto.setFailed("Login Failed");
		/* if문에 중괄호를 지워도 if문이 돌아간다. */
		
		// 동일하면 토큰 생성 후 토큰 및 만료시간 반환
		LoginDto result = new LoginDto("JWT", 3600000);
		return ResponseDto.setSuccess("", result);
		
		/* 하나의 조건들은 하나씩 따로 묶어주는 것이 좋다. */
	}
}
