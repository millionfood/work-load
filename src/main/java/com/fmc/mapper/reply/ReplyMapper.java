package com.fmc.mapper.reply;

import java.util.List;

import com.fmc.domain.ReplyVO;
import com.fmc.dto.ReplyDetailDTO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	public List<ReplyDetailDTO> getReplyListWithBoard(Long bno);
	
	public List<ReplyDetailDTO> getReplyListWithMember(Long mno);
	
	public int getTotalCountWithBoard(int bno);
	
	public int getTotalCountWithMember(int mno);
	
	public int update(ReplyDetailDTO dto);
	
	public int delete(Long rno);

}
