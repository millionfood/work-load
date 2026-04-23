package com.fmc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fmc.domain.AttachVO;
import com.fmc.domain.BoardVO;
import com.fmc.domain.MemberVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.BoardRegisterDTO;
import com.fmc.dto.Criteria;
import com.fmc.mapper.board.BoardMapper;
import com.fmc.mapper.reply.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

public interface BoardService {
	
	
	public void insert(BoardVO bvo,MultipartFile[] uploadFile);
	
	public String uploadImage(MultipartFile file) throws IOException;
	
	public BoardDetailDTO getPostDetail(int bno);
	
	public List<BoardDetailDTO> getPostList();
	
	public List<BoardDetailDTO> getPostListWithPopular();
	
	public List<BoardDetailDTO> getPostListWithPaging(Criteria cri);
	
	public List<BoardDetailDTO> getPostListByMnoWithPaging(int mno,Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<AttachVO> getAttachList(int bno);
	
	public void updatePost(BoardRegisterDTO dto, MultipartFile[] uploadFiles,List<String> deleteFiles);
	
	public void updateViewCnt(int bno);
	
	public void deletePostOne(int bno);
	
	public void deletePostByMember(int mno);	
}
