package com.fmc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmc.domain.ReplyVO;
import com.fmc.dto.Criteria;
import com.fmc.dto.ReplyDetailDTO;
import com.fmc.exception.ApiException;
import com.fmc.mapper.board.BoardMapper;
import com.fmc.mapper.reply.ReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService{
	
	private final ReplyMapper replyMapper;
	private final BoardMapper boardMapper;
	
	@Override
	@Transactional
	public void insert(ReplyVO vo) {
		int rcnt = replyMapper.insert(vo);
		if(rcnt == 0) {
			throw new ApiException("댓글 등록에 실패했습니다.-reply insert");
		}
		int bno = vo.getBno();
		int cnt = 1;
		int bcnt = boardMapper.updateReplyCount(bno,cnt);
		if(bcnt == 0) {
			throw new ApiException("댓글 등록에 실패했습니다.-board replyCnt update");
		}
	}
	@Override
	public List<ReplyDetailDTO> getListWithBno(int bno){
		return replyMapper.getReplyListWithBoard(bno);
	}
	@Override
	public List<ReplyDetailDTO> getListWithMno(int mno, Criteria cri){
		return replyMapper.getReplyListWithMember(mno, cri);
	}
	@Override
	public int getReplyCntWithBoard(int bno) {
		return replyMapper.getTotalCountWithBoard(bno);
	}
	//update
	@Override
	@Transactional
	public void update(ReplyDetailDTO dto) {
		int cnt = replyMapper.update(dto);
		if(cnt == 0) {
			throw new ApiException("업데이트에 실패했습니다.");
		}
	}
	//delete
	//댓글 전체 삭제(멤버를 삭제할 때 사용)
	@Override
	@Transactional
	public void deleteReplyByMember(int mno) {
		int rcnt = replyMapper.deleteByMno(mno);
	}
	//댓글 전체 삭제(게시물을 삭제할 때 사용)
	@Override
	@Transactional
	public void deleteReplyByBoard(int bno) {
		int rcnt = replyMapper.deleteByBno(bno);
		if(rcnt == 0) {
			throw new ApiException("삭제에 실패했습니다.");
		}
	}
	//댓글 삭제하면서 게시물의 댓글 카운트를 내려줌(댓글만 한개 삭제할 때 사용)
	@Override
	@Transactional
	public void deleteOne(int bno, Long rno) {
		int rcnt = replyMapper.deleteByRno(rno);
		if(rcnt == 0) {
			throw new ApiException("삭제에 실패했습니다.");
		}
		int bcnt = boardMapper.updateReplyCount(bno,-1);
		if(bcnt == 0) {
			throw new ApiException("삭제에 실패했습니다.");
		}
	}
}
