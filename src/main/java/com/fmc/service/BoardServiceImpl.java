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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fmc.domain.AttachVO;
import com.fmc.domain.BoardVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.BoardRegisterDTO;
import com.fmc.dto.Criteria;
import com.fmc.exception.LocalFileException;
import com.fmc.exception.PersistenceException;
import com.fmc.mapper.board.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;
	private final ReplyService replyService;
	
	
	private String uploadSummerNotePath = "C:/dev/upload/summernote/";
	private String uploadAttachPath = "C:/dev/upload/attach/";
	
	@Override
	@Transactional
	public void insert(BoardVO bvo,MultipartFile[] uploadFile) {
		int bcnt = boardMapper.insert(bvo);
		if(bcnt == 0) {
			throw new PersistenceException("게시물 insert에 실패했습니다.");
		}
		
		if(uploadFile != null && uploadFile.length > 0) {
			String datePath = getFolder();
			File uploadPath = new File(uploadAttachPath,datePath);
			
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
			
			for(MultipartFile file : uploadFile) {
				if(file.isEmpty()) continue;
				
				String uuid = UUID.randomUUID().toString();
                String originName = file.getOriginalFilename();
                String saveName = uuid + "_" + originName;
                
				try {
					File saveFile = new File(uploadPath, saveName);
					file.transferTo(saveFile);
					
					AttachVO avo = new AttachVO();
					avo.setBno(bvo.getBno());
					avo.setUuid(uuid);
					avo.setFileName(originName);
					avo.setUploadPath(datePath);
					
					boardMapper.insertAttach(avo);
					
				}catch (Exception e) {
					throw new LocalFileException("파일 저장 중 오류가 발생했습니다.");
				}
			}
		}
	}
	@Override
	@Transactional
	public String uploadImage(MultipartFile file) throws IOException{
				
		File folder = new File(uploadSummerNotePath);
		if(!folder.exists()) folder.mkdirs();
		
		String originalName = file.getOriginalFilename();
		String extension = originalName.substring(originalName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();
		String saveName = uuid+"_"+extension;
		
		File target = new File(uploadSummerNotePath,saveName);
		file.transferTo(target);
		return "/upload/"+saveName;
	}
	
	
	
	@Override
	public BoardDetailDTO getPostDetail(int bno) {
		BoardDetailDTO dto = boardMapper.getPostDetail(bno);
		if(dto == null) {
			throw new PersistenceException("게시물을 불러올 수 없습니다.");
		}
		return dto;
	}
	@Override
	public List<BoardDetailDTO> getPostList(){
		return boardMapper.getPostList();
	}
	@Override
	public List<BoardDetailDTO> getPostListWithPopular(){
		return boardMapper.getPostListWithPopular();
	}
	@Override
	public List<BoardDetailDTO> getPostListWithPaging(Criteria cri){
		return boardMapper.getPostListWithPaging(cri);
	}
	@Override
	public List<BoardDetailDTO> getPostListByMnoWithPaging(int mno,Criteria cri){
		return boardMapper.getPostListByMnoWithPaging(mno, cri);
	}
	
	@Override
	public int getTotalCount(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}
	@Override
	public List<AttachVO> getAttachList(int bno){
		return boardMapper.getAttachList(bno);
	}
	@Override
	@Transactional
	public void updatePost(BoardRegisterDTO dto, MultipartFile[] uploadFiles,List<String> deleteFiles) {
		BoardDetailDTO oldData = boardMapper.getPostDetail(dto.getBno());
		String oldContent = oldData.getContent();
		String newContent = dto.getContent();
		
		int cnt = boardMapper.update(dto);
		if(cnt == 0) {
			throw new PersistenceException("게시물 업데이트에 실패했습니다. - boardDto");
		}
		//summernote 미사용 이미지 삭제
		List<String> oldFiles = getFileNames(oldContent);
		List<String> newFiles = getFileNames(newContent);
		
		for(String oldFile : oldFiles) {
			if(!newFiles.contains(oldFile)) {
				File file = new File(uploadSummerNotePath+oldFile);
				if(file.exists()) {
					file.delete();
				}
			}
		}
		//jsp에서 삭제한 첨부파일 삭제
		if(deleteFiles != null) {
			for(String uuid : deleteFiles) {
				try {
					deleteLocalFiles(uuid);
				} catch (RuntimeException e) {
					throw new LocalFileException("첨부파일 삭제에 실패했습니다.",e);
				}
			}
		}
		//첨부 파일 업로드
		if(uploadFiles != null && uploadFiles.length > 0) {
			String datePath = getFolder();
			File uploadPath = new File(uploadAttachPath,datePath);
			
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
			
			for(MultipartFile file : uploadFiles) {
				if(file.isEmpty()) continue;
				
				String uuid = UUID.randomUUID().toString();
                String originName = file.getOriginalFilename();
                String saveName = uuid + "_" + originName;
                
				try {
					File saveFile = new File(uploadPath, saveName);
					file.transferTo(saveFile);
					
					AttachVO avo = new AttachVO();
					avo.setBno(dto.getBno());
					avo.setUuid(uuid);
					avo.setFileName(originName);
					avo.setUploadPath(datePath);
					
					boardMapper.insertAttach(avo);
					
				}catch (Exception e) {
					throw new LocalFileException("파일 저장 중 오류가 발생했습니다.",e);
				}
			}
		}
	}
	//update
	@Override
	@Transactional
	public void updateViewCnt(int bno) {
		int cnt = boardMapper.updateViewCount(bno);
		if(cnt == 0) {
			throw new PersistenceException("조회수 업데이트에 실패했습니다.");
		}
	}
	
	//delete
	//한개의 게시물 삭제
	@Override
	@Transactional
	public void deletePostOne(int bno) {
		BoardDetailDTO dto = boardMapper.getPostDetail(bno);
		//reply 삭제
		replyService.deleteReplyByBoard(bno);
		
		//db에서 게시물 삭제
		int bcnt = boardMapper.deletePostByBno(bno);
		if(bcnt == 0) {
			throw new PersistenceException("게시물 삭제에 실패했습니다. - db");
		}
		
		//attach 로컬 파일 삭제 
		List<AttachVO> avoList = boardMapper.getAttachList(bno);
		if(avoList != null) {
			for(AttachVO avo : avoList) {
				try {
					deleteLocalFiles(avo.getUuid());
				} catch (Exception e) {
					throw new LocalFileException("첨부파일 삭제에 실패했습니다.",e);
				}
			}
		}
		
		//summernote 로컬 파일 삭제
		String content = dto.getContent();
		deleteSummerNoteFiles(content);
		
		
	}
	//member와 관련된 board 삭제
	@Override
	@Transactional
	public void deletePostByMember(int mno) {
		//삭제할 첨부파일 리스트를 불러온다.
		List<AttachVO> avoList = boardMapper.getAttachListByMno(mno);
		//삭제할 summernote 정보를 불러온다.
		List<String> summernoteList = boardMapper.getSummernoteListByMno(mno);
		
		//attach를 삭제한다.
		boardMapper.deleteAttachByMno(mno);
		
		//게시물을 삭제한다.
		int bcnt = boardMapper.deletePostByMno(mno);
		if(bcnt == 0) throw new PersistenceException("멤버와 관련된 게시물 삭제에 실패했습니다. - db");
		
		//attach 로컬 파일 삭제 
		if(avoList != null) {
			for(AttachVO avo : avoList) {
				try {
					deleteLocalFiles(avo.getUuid());
				} catch (Exception e) {
					throw new LocalFileException("첨부파일 삭제에 실패했습니다.",e);
				}
			}
		}
		
		//summernote 로컬 파일 삭제
		if(summernoteList != null && summernoteList.size() > 0) {
			for(String content : summernoteList) {
				deleteSummerNoteFiles(content);				
			}
		}
	}
	//summernote 내부의 이미지 삭제 메서드
	private void deleteSummerNoteFiles(String content) {
		if(content == null || content.length() < 1) return;
		
		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
		
		Matcher matcher = pattern.matcher(content);
		
		while(matcher.find()) {
			String imgUrl = matcher.group(1);
			String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
			
			//실제 파일 경로 객체 생성
			File file = new File("C:/dev/upload/summernote/" + fileName);
			
			if(file.exists()) {
				file.delete();
			}
		}
	}
	//첨부파일 삭제 메서드
	private void deleteLocalFiles(String uuid) {
		AttachVO avo = boardMapper.getAttachByUUID(uuid);
		
		if(avo!=null) {
			String fullFileName = avo.getUuid()+"_"+avo.getFileName();
			Path path = Paths.get(uploadAttachPath,avo.getUploadPath(),fullFileName);
			File file = path.toFile();
			if(file.exists()) file.delete();
			
			int cnt = boardMapper.deleteAttachByUUID(uuid);
			if(cnt == 0) throw new PersistenceException("첨부파일 삭제에 실패했습니다. - db");
		}
	}
	
	//본문에서 파일명만 리스트로 뽑아주는 공통 메서드
	private List<String> getFileNames(String content){
		List<String> list = new ArrayList<>();
		if(content == null || content.isEmpty()) return list;
		
		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
	    Matcher matcher = pattern.matcher(content);
	    
	    while(matcher.find()) {
	    	String imgUrl = matcher.group(1);
	    	list.add(imgUrl.substring(imgUrl.lastIndexOf("/")+1));
	    }
	    
	    return list;
	}
	//오늘 날짜로 폴더 경로를 생성해준다.
	private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(new java.util.Date());
        return str.replace("-", File.separator);
    }
	
}
