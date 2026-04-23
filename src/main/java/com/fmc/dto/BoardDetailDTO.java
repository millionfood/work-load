package com.fmc.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

@Data
public class BoardDetailDTO {

	private int bno;
	private int mno;
	private String title;
	private String content;
	private String nickname;
	private String address;
	private LocalDateTime updateDate;
	private int viewcnt;
	private int replycnt;
	private double lat; //위도(Latitude)
	private double lng; //경도(Longitude)
	
	public String getFormattedUpdateDate() {
		if(this.updateDate == null) return "";
		return this.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public String getFormattedUpdateDateDetail() {
		if(this.updateDate == null) return "";
		return this.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	public String getThumbnail() {
		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
	    Matcher matcher = pattern.matcher(content);
	    
	    if(matcher.find()) {
	    	return matcher.group(1);
	    }
	    return "upload/no-image.jpg";
	}
}
