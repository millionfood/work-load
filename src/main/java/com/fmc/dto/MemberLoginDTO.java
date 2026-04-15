package com.fmc.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class MemberLoginDTO {
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String pw;
	
}
