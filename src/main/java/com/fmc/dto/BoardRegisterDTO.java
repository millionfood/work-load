package com.fmc.dto;

import javax.validation.constraints.NotBlank;

import com.fmc.domain.BoardVO;

import lombok.Data;

@Data
public class BoardRegisterDTO {
	private int bno;
	@NotBlank(message = "제목은 필수 입력 값입니다.")
	private String title;
	@NotBlank(message = "내용은 필수 입력 값입니다.")
	private String content;
	@NotBlank(message = "주소는 필수 입력 값입니다..")
	private String address;
	private int viewcnt = 0;
	private int replycnt = 0;
	private double lat = 37.4979;
	private double lng = 127.0276;
	
	public BoardVO toVO() {
		BoardVO vo = new BoardVO();
		vo.setTitle(this.title);
		vo.setContent(this.content);
		vo.setViewcnt(this.viewcnt);
		vo.setReplycnt(this.replycnt);
		vo.setAddress(this.address);
		vo.setLat(this.lat);
		vo.setLng(this.lng);
		return vo;
	}
}
