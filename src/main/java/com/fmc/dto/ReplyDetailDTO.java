package com.fmc.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyDetailDTO {

	private int rno;
	private int bno;
	private int mno;
	private String nickname;
	private String replyText;
	private LocalDateTime updatedDate = LocalDateTime.now();
}
