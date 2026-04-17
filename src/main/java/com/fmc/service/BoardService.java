package com.fmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmc.domain.BoardVO;
import com.fmc.domain.MemberVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.Criteria;
import com.fmc.mapper.board.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardMapper boardMapper;
	
	@Transactional
	public void insert(BoardVO vo) {
		int cnt = boardMapper.insert(vo);
		if(cnt == 0) {
			throw new RuntimeException("게시물 insert에 실패했습니다.");
		}
	}
	public BoardDetailDTO getPostDetail(int bno) {
		BoardDetailDTO dto = boardMapper.getPostDetail(bno);
		if(dto == null) {
			throw new RuntimeException("게시물을 불러올 수 없습니다.");
		}
		return dto;
	}
	public List<BoardDetailDTO> getPostList(){
		return boardMapper.getPostList();
	}
	public List<BoardDetailDTO> getPostListWithPaging(Criteria cri){
		return boardMapper.getPostListWithPaging(cri);
	}
	public int getTotalCount(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}
	@Transactional
	public void updatePost(BoardVO vo) {
		int cnt = boardMapper.update(vo);
		if(cnt == 0) {
			throw new RuntimeException("업데이트에 실패했습니다.");
		}
	}
	
	@Transactional
	public void deletePost(int bno) {
		int cnt = boardMapper.deletePost(bno);
		if(cnt == 0) {
			throw new RuntimeException("삭제에 실패했습니다.");
		}
	}
}
