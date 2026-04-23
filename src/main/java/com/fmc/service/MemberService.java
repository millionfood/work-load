package com.fmc.service;

import java.util.List;

import com.fmc.domain.MemberVO;
import com.fmc.dto.MemberEditDTO;
import com.fmc.dto.MemberJoinDTO;
import com.fmc.dto.MemberLoginDTO;


public interface MemberService {

	public MemberVO getMember (MemberLoginDTO dto);
	
	public MemberVO getMemberByMno (int mno);
	
	public List<MemberVO> getMemberList();
	
	public void join (MemberJoinDTO dto);
	
	public MemberVO modifyMemberInfo(MemberEditDTO dto);
	
	public void deleteMember(int mno);
}
