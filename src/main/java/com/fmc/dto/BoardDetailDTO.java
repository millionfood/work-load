package com.fmc.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class BoardDetailDTO {

	private int bno;
	private String title;
	private String content;
	private String nickname;
	private String address;
	private LocalDateTime updateDate;
	private int viewcnt;
	
	public String getFormattedUpdateDate() {
		if(this.updateDate == null) return "";
		return this.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
}
