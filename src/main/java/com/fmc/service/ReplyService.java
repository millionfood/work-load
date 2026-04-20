package com.fmc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fmc.domain.ReplyVO;
import com.fmc.dto.ReplyDetailDTO;
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
	public List<ReplyDetailDTO> getList(Long bno){
		return replyMapper.getReplyListWithBoard(bno);
	}
	public void update(ReplyDetailDTO dto) {
		int cnt = replyMapper.update(dto);
		if(cnt == 0) {
			throw new RuntimeException("업데이트에 실패했습니다.");
		}
	}
	public void delete(Long rno) {
		int cnt = replyMapper.delete(rno);
		if(cnt == 0) {
			throw new RuntimeException("삭제에 실패했습니다.");
		}
	}
}
