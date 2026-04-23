package com.fmc.service;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mindrot.jbcrypt.BCrypt;
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
public class MemberServiceImpl implements MemberService{


	private final MemberMapper memberMapper;
	
	private final BoardService boardService;
	private final ReplyService replyService;
	
	@Override
	public MemberVO getMember (MemberLoginDTO dto) {
		
		return memberMapper.getMemberEmail(dto.getEmail());
	}
	@Override
	public MemberVO getMemberByMno (int mno) {
		return memberMapper.getMemberByMno(mno);
	}
	@Override
	public List<MemberVO> getMemberList() {
		return memberMapper.getMemberList();
	}
	
	@Override
	@Transactional
	public void join (MemberJoinDTO dto) {
		String plainPw = dto.getPw();
		
		String hashedPw = BCrypt.hashpw(plainPw, BCrypt.gensalt());
		
		dto.setPw(hashedPw);
		int cnt = memberMapper.checkMember(dto.getEmail());
		if(cnt > 0) {
			throw new PersistenceException("이미 사용중인 아이디 입니다.");
		}
		
		MemberVO vo = dto.toVO();
		memberMapper.insert(vo);
	}
	
	@Override
	@Transactional
	public MemberVO modifyMemberInfo(MemberEditDTO dto) {
		int cnt = memberMapper.updateMember(dto.toVO());
		if(cnt == 0) {
			throw new PersistenceException("업데이트에 실패했습니다.");
		}
		return memberMapper.getMemberEmail(dto.getEmail());
	}
	
	@Override
	@Transactional
	public void deleteMember(int mno) {
		try {
			//reply삭제
			replyService.deleteReplyByMember(mno);
			//board삭제
			boardService.deletePostByMember(mno);
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new PersistenceException("계정 삭제에 실패했습니다.",e);
		}
		//회원 삭제
		int mcnt = memberMapper.deleteMember(mno);
		if(mcnt == 0) {
			throw new PersistenceException("계정 삭제에 실패했습니다.");
		}
	}
}
