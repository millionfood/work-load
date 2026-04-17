package com.fmc.dto;

import javax.validation.constraints.NotBlank;

import com.fmc.domain.BoardVO;

import lombok.Data;

@Data
public class BoardRegisterDTO {
	@NotBlank(message = "제목은 필수 입력 값입니다.")
	private String title;
	@NotBlank(message = "내용은 필수 입력 값입니다.")
	private String content;
	@NotBlank(message = "주소는 필수 입력 값입니다..")
	private String address;
	private int viewcnt = 0;
	
	public BoardVO toVO() {
		BoardVO vo = new BoardVO();
		vo.setTitle(this.title);
		vo.setContent(this.content);
		vo.setViewcnt(this.viewcnt);
		vo.setAddress(this.address);
		return vo;
	}
}
