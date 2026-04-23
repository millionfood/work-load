package com.fmc.dto;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private String keyword ="";
	private String searchType ="";
	
	//기본 생성자 : 처음 접속 시 1페이지, 10개씩 설정
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	//limit 구문에서 사용할 건너뛸 행의 개수 계산
	//ex) 2페이지라면 (2-1) * 10 = 10개를 건너뛰고 11번부터 가져옴
	public int getSkip() {
		return (this.pageNum-1)*this.amount;
	}
}
