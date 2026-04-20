package com.fmc.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyRegisterDTO {

	private int bno;
	private int mno;
	private String nickname;
	private String replyText;
	
}
