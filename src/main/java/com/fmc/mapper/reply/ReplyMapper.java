package com.fmc.mapper.reply;

import java.util.List;

import com.fmc.domain.ReplyVO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	public List<ReplyVO> getReplyListWithBoard(int bno);
	
	public List<ReplyVO> getReplyListWithMember(int mno);
	
	public int getTotalCountWithBoard(int bno);
	
	public int getTotalCountWithMember(int mno);
	
	public int update(ReplyVO vo);
	
	public int delete(int rno);

}
