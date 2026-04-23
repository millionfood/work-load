package com.fmc.service;

import java.util.List;


import com.fmc.domain.ReplyVO;
import com.fmc.dto.Criteria;
import com.fmc.dto.ReplyDetailDTO;

public interface ReplyService {
	
	
	public void insert(ReplyVO vo);
	
	public List<ReplyDetailDTO> getListWithBno(int bno);
	
	public List<ReplyDetailDTO> getListWithMno(int mno, Criteria cri);
	
	public int getReplyCntWithBoard(int bno);
	
	public void update(ReplyDetailDTO dto);
	
	public void deleteReplyByMember(int mno);
	
	public void deleteReplyByBoard(int bno);
	
	public void deleteOne(int bno, Long rno);
}

