package com.fmc.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemberVO {
	private int mno;
	private String email;
	private String pw;
	private String nickname;
	private LocalDateTime regdate;
	private LocalDateTime updateDate;
	
}
