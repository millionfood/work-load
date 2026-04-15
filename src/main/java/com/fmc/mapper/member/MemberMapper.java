package com.fmc.mapper.member;

import com.fmc.domain.MemberVO;

public interface MemberMapper {
	
	public int insert(MemberVO vo);
	
	public MemberVO getMemberEmail(String email);
	
	public int checkMember(String email);
	
	public int updateMember(MemberVO vo);

	public int deleteMember(String email);
	
}
