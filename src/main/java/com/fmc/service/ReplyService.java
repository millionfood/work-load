package com.fmc.service;

import org.springframework.stereotype.Service;

import com.fmc.domain.ReplyVO;
import com.fmc.mapper.reply.ReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	
	private final ReplyMapper replyMapper;
	
	public void insert(ReplyVO vo) {
		int cnt = replyMapper.insert(vo);
		if(cnt == 0) {
			throw new RuntimeException("댓글 등록에 실패했습니다.");
		}
	}
}
