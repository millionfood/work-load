package com.fmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmc.domain.MemberVO;
import com.fmc.dto.MemberEditDTO;
import com.fmc.dto.MemberJoinDTO;
import com.fmc.dto.MemberLoginDTO;
import com.fmc.mapper.member.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@lombok.RequiredArgsConstructor
@Slf4j
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	public MemberVO login (MemberLoginDTO dto) {
		
		return memberMapper.getMemberEmail(dto.getEmail());
	}
	
	@Transactional
	public void join (MemberJoinDTO dto) {
		int cnt = memberMapper.checkMember(dto.getEmail());
		if(cnt > 0) {
			throw new RuntimeException("이미 사용중인 이메일 입니다.");
		}
		
		MemberVO vo = dto.toVO();
		memberMapper.insert(vo);
		log.info("회원가입 완료 : " + vo.getEmail());
	}
	
	@Transactional
	public MemberVO modifyMemberInfo(MemberEditDTO dto) {
		int cnt = memberMapper.updateMember(dto.toVO());
		if(cnt == 0) {
			throw new RuntimeException("업데이트에 실패했습니다.");
		}
		log.info("회원정보 수정 완료 : "+ dto.getEmail());
		return memberMapper.getMemberEmail(dto.getEmail());
	}
	
	@Transactional
	public void deleteMember(String email) {
		int cnt = memberMapper.deleteMember(email);
		if(cnt == 0) {
			throw new RuntimeException("계정 삭제에 실패했습니다.");
		}
		log.info("회원정보 삭제 완료 : "+ email);
	}
}
