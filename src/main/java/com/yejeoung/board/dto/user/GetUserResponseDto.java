package com.yejeoung.board.dto.user;

import com.yejeoung.board.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponseDto {
	private String email;
	private String nickname;
	private String profile;
	private String telNumber;
	private String address;
	
	public GetUserResponseDto(MemberEntity member) {
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.profile = member.getProfile();
		this.telNumber = member.getTelNumber();
		this.address = member.getAddress();
	}
	// 할당 된 것들을 받아 써도 되지만, 생성자를 만들어서 사용하는 것이 편리하다.
}
