package com.fmc.domain;

import lombok.Data;

@Data
public class AttachVO {
	private String uuid;
	private int bno;
	private String uploadPath;
	private String fileName;
	private String fileType;
	
}
