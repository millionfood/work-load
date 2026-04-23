package com.fmc.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class MemberVO {
	private int mno;
	private String email;
	private String pw;
	private String nickname;
	private LocalDateTime regdate;
	private LocalDateTime updateDate;
	private MemberRole role = MemberRole.USER;
	
	public String getFormattedRegDate() {
		if(this.regdate == null) return "";
		return this.regdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
