package com.fmc.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReplyDetailDTO {

	private Long rno;
	private int bno;
	private int mno;
	private String nickname;
	private String replyText;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime regDate;
	
	public String getFormattedRegDateDetail() {
		if(this.regDate == null) return "";
		return this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
