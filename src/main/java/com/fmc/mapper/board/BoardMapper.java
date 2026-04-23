package com.fmc.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fmc.domain.AttachVO;
import com.fmc.domain.BoardVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.BoardRegisterDTO;
import com.fmc.dto.Criteria;

public interface BoardMapper {
	
	//insert
	public int insert(BoardVO vo);
	
	//get
	public BoardDetailDTO getPostDetail(int bno);
	
	public List<BoardDetailDTO> getPostList();
	
	public List<BoardDetailDTO> getPostListWithPopular();
	
	public List<BoardDetailDTO> getPostListWithPaging(Criteria cri);
	
	public List<BoardDetailDTO> getPostListByMnoAll(int mno);
	
	public List<BoardDetailDTO> getPostListByMnoWithPaging(@Param("mno")int mno,@Param("cri") Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	//update
	public int update(BoardRegisterDTO dto);
	
	public int updateViewCount(int bno);
	
	public int updateReplyCount(@Param("bno")int bno,@Param("cnt") int cnt);
	
	//delete
	public int deletePostByBno(int bno);
	
	public int deletePostByMno(int mno);

	
	
	//summernote
	public List<String> getSummernoteListByMno(int mno);
	
	//attach -insert
	public int insertAttach(AttachVO vo);
	
	//attach - get
	public List<AttachVO> getAttachList (int bno);
	
	public List<AttachVO> getAttachListByMno (int mno);
	
	public AttachVO getAttachByUUID (String uuid);
	
	//attach - delete
	public int deleteAttachByUUID(String uuid);
	
	public int deleteAttachByMno(int mno);
}
