package com.fmc.mapper.member;

import java.util.List;

import com.fmc.domain.MemberVO;

public interface MemberMapper {
	
	public int insert(MemberVO vo);
	
	public MemberVO getMemberEmail(String email);
	
	public MemberVO getMemberByMno(int mno);
	
	public List<MemberVO> getMemberList ();
	
	public int checkMember(String email);
	
	public int updateMember(MemberVO vo);

	public int deleteMember(int mno);
	
}
