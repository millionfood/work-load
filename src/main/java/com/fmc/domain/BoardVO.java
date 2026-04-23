package com.fmc.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardVO {
	private int bno;
	private String title;
	private String content;
	private int writer;
	private String address;
	private LocalDateTime regdate;
	private LocalDateTime updateDate;
	private int viewcnt;
	private int replycnt;
	private double lat; //위도(Latitude)
	private double lng; //경도(Longitude)
}
