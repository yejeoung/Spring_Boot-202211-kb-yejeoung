package com.yejeoung.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yejeoung.board.dto.response.ResponseDto;
import com.yejeoung.board.dto.user.GetUserResponseDto;
import com.yejeoung.board.dto.user.PatchUserDto;
import com.yejeoung.board.dto.user.PostUserDto;
import com.yejeoung.board.dto.user.ResultResponseDto;
import com.yejeoung.board.entity.MemberEntity;
import com.yejeoung.board.repository.MemberRepository;

@Service
public class UserService { // 실제 회원가입 되는 프로세서

	// MemberRepository => Entity명을 따라가게 된다.
	@Autowired
	MemberRepository memberRepository;

	public ResponseDto<GetUserResponseDto> getUser(String email) {
		// 해당 이메일을 데이터베이스에서 검색
		MemberEntity member;

		try {
			member = memberRepository.findById(email).get();
		}
		// 존재하지 않으면 "Not Exist User" 메세지를 포함한 Failed Response 반환
		catch (Exception e) {
			return ResponseDto.setFailed("Not Exist User");
		}
		// 존재하면 User정보 반환
		// 1
//		GetUserResponseDto responseData = new GetUserResponseDto();
//		responseData.setEmail(member.getEmail());
//		responseData.setNickname(member.getNickname());
//		responseData.setProfile(member.getProfile());
//		responseData.setTelNumber(member.getProfile());
//		responseData.setAddress(member.getAddress());
//		
//		return ResponseDto.setSuccess("Get User Success", responseData);

		// 2 GetUserResponse Class에 Builder 추가
//		GetUserResponseDto responseData = 
//				GetUserResponseDto
//				.builder()
//				.email(email)
//				.nickname(email)
//				.profile(email)
//				.telNumber(email)
//				.address(email)
//				.build();
//		
//		return ResponseDto.setSuccess("Get User Success", responseData);

		// 3
//		return ResponseDto.setSuccess(
//				"Get User Success", 
//				new GetUserResponseDto(
//						member.getEmail(),
//						member.getNickname(),
//						member.getProfile(),
//						member.getProfile(),
//						member.getAddress()
//					)
//				);
		// GetUserResponse Class에 member 생성자 만듦
		return ResponseDto.setSuccess("Get User Success", new GetUserResponseDto(member));

	}

	public ResponseDto<ResultResponseDto> postUser(PostUserDto dto) {

		// 데이터베이스에 해당 이메일이 존재하는지 체크
		// 존재한다면 Failed Response를 반환
		// select * from m where email = ?
		// email을 꺼내와서 존재하는지(중복o) 존재하지 않는지(중복x) 확인
		// 중복이 있는 상태로 프로세서를 실행하게 되면 update 작업을 수행한다.
		// failed Response를 사용해 중복된 결과물이라고 뜨게 작업을 해준다.
		String email = dto.getEmail();
		// 하나의 함수로 빼게 되면 코드가 깔끔하게 처리가 가능하다.
		try {
			if (memberRepository.existsById(email))
				return ResponseDto.setFailed("이미 존재하는 이메일입니다.");
		} catch (Exception e) {
			return ResponseDto.setFailed("데이터베이스 오류입니다.");
		}
		// Repository를 사용할 경우 try catch에 넣어서 작업해주는 것이 제일 좋다.
		// 서버는 잘 작동하나 중간에 데이터베이스 서버에 오류가 발생할 수 있기 때문

//		try {			
//			MemberEntity member = memberRepository.findById(email).get();
//		} catch(Exception e) {
//			return ResponseDto.setFailed("이미 존재하는 이메일입니다.");
//		}

		String password = dto.getPassword();
		String password2 = dto.getPassword2();

		// 사용자가 입력한 비밀번호가 같은지 다른지 검증하는 조건문
		if (!password.equals(password2))
			return ResponseDto.setFailed("비밀번호가 서로 다릅니다.");

		// MemberEntity Class에 입력해놓은 것들을 가져옴(get)
		MemberEntity member = MemberEntity.builder().email(dto.getEmail()).password(dto.getPassword())
				.nickname(dto.getNickname()).telNumber(dto.getTelNumber())
				.address(dto.getAddress() + " " + dto.getAddressDetail()).build();

		// JpaRepository.save(Entity) 메서드
		// 해당 Entity Id가 데이터베이스 테이블에 존재하지 않으면,
		// Entity INSERT 작업을 수행한다.
		// 해당 Entity Id가 데이터베이스 테이블에 존재하면
		// 존재하는 Entity UPDATE 작업을 수행한다.
		memberRepository.save(member);
		// 만들어진 Entity 데이터가 데이터베이스에 저장된다.

		return ResponseDto.setSuccess("회원가입에 성공했습니다.", new ResultResponseDto(true));
	}

	public ResponseDto<GetUserResponseDto> patchUser(PatchUserDto dto) {
		// dto에서 이메일을 가지고 온다.
		String email = dto.getEmail();

		// repository를 이용해서 데이터베이스에 있는 member 테이블 중
		// 해당 email에 해당하는 데이터를 불러온다.
		MemberEntity member = null;
		try {
			// 이메일에 해당하는 user 정보
			member = memberRepository.findById(email).get();
		} catch (Exception e) {
			// 만약 존재하지 않으면 Failed Response로 "Not Exist User" 반환
			return ResponseDto.setFailed("Not Exist User");
		}

		// Request Body로 받은 nickname과 profile로 각각 변경
		member.setNickname(dto.getNickname());
		member.setProfile(dto.getProfile());

		// 변경한 entity를 repository를 이용해서 데이터베이스에 적용(저장)
		memberRepository.save(member);

		// 결과를 ResponsDto에 담아서 반환
		return ResponseDto.setSuccess("User Patch Success", new GetUserResponseDto(member));
	}

	public ResponseDto<ResultResponseDto> deleteUser(String email) {
		// repository를 이용해서 데이터베이스에 있는 Member 테이블 중
		// email에 해당하는 데이터를 삭제한다.
		memberRepository.deleteById(email);

		return ResponseDto.setSuccess("Delete User Sussecc", new ResultResponseDto(true));
	}
}