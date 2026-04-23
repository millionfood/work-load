package com.fmc.mapper.reply;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fmc.domain.ReplyVO;
import com.fmc.dto.Criteria;
import com.fmc.dto.ReplyDetailDTO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	public List<ReplyDetailDTO> getReplyListWithBoard(int bno);
	
	public List<ReplyDetailDTO> getReplyListWithMember(@Param("mno")int mno,@Param("cri")Criteria cri);
	
	public int getTotalCountWithBoard(int bno);
	
	public int getTotalCountWithMember(int mno);
	
	public int update(ReplyDetailDTO dto);
	
	public int deleteByRno(Long rno);
	
	public int deleteByMno(int mno);
	
	public int deleteByBno(int bno);

}
