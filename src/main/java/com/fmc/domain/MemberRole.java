package com.fmc.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
	ADMIN("관리자"),
	USER("일반사용자");
	
	private final String description;
}
