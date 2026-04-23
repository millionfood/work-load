package com.fmc.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyVO {
	private Long rno;
	private int bno;
	private String replyText;
	private int writer;
	private LocalDateTime regdate;
	private LocalDateTime updateDate;
}
