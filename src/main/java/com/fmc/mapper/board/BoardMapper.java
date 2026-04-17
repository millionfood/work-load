package com.fmc.mapper.board;

import java.util.List;

import com.fmc.domain.BoardVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.Criteria;

public interface BoardMapper {
	
	public int insert(BoardVO vo);
	
	public BoardDetailDTO getPostDetail(int bno);
	
	public List<BoardDetailDTO> getPostList();
	
	public List<BoardDetailDTO> getPostListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public int update(BoardVO vo);
	
	public int deletePost(int bno);
}
