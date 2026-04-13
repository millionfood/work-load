package com.fmc.service;

import org.springframework.stereotype.Service;

import com.fmc.domain.MemberVO;
import com.fmc.mapper.member.MemberMapper;

@Service
@lombok.RequiredArgsConstructor
public class MemberService {

	private final MemberMapper memberMapper;
	
	public boolean insert(MemberVO vo) {
		return memberMapper.insert(vo) != 0;
	}
}
