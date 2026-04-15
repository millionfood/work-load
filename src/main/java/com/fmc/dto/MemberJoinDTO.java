package com.fmc.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fmc.domain.MemberVO;

import lombok.Data;
@Data
public class MemberJoinDTO {
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Size(min=8,max=20, message = "비밀번호는 8자~16자 사이여야 합니다.")
	private String pw;
	private String pwConfirm;
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;
	
	public MemberVO toVO() {
		MemberVO vo = new MemberVO();
		vo.setEmail(this.getEmail());
		vo.setNickname(this.getNickname());
		vo.setPw(this.getPw());
		return vo;
	}
}
