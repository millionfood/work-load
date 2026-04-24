package com.fmc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fmc.domain.AttachVO;
import com.fmc.domain.BoardVO;
import com.fmc.domain.MemberVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.BoardRegisterDTO;
import com.fmc.dto.Criteria;
import com.fmc.dto.pageDTO;
import com.fmc.exception.LocalFileException;
import com.fmc.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private String kakaoApiKey ="57a33516b3ad65bcefc1149543eebe3b";
	
	//게시물 작성-get
	@GetMapping("/write")
	public String getWrite(Model model) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("serverTime", now.format(formatter));
		model.addAttribute("apiKey", kakaoApiKey);
		return "board/write";
	}
	//게시물 작성-post
	@PostMapping("/write")
	public String postWrite(MultipartFile[] uploadFile, @Valid BoardRegisterDTO dto,BindingResult result,RedirectAttributes rttr, HttpSession session) {
		if(result.hasErrors()) {
			String errMsg = result.getFieldError().getDefaultMessage();
			rttr.addFlashAttribute("fail", "게시물 등록에 실패했습니다.");
			log.error("게시물 등록 실패 : {} ",errMsg);
			return "redirect:/board/write";
		}
		BoardVO bvo = dto.toVO();
		MemberVO mvo = (MemberVO) session.getAttribute("loggedMember");
		bvo.setWriter(mvo.getMno());
		//첨부파일 확인
		if(!checkFile(uploadFile)) {
			throw new LocalFileException("허용되지 않는 파일 입니다.");
		}
		boardService.insert(bvo,uploadFile);
		return "redirect:/board/list";
	}
	//이미지 업로드 - ajax
	@PostMapping(value = "/uploadImage", produces = "text/plain; charshet=utf-8")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file) {
		
		try {
			return boardService.uploadImage(file);
		} catch (IOException e) {
			log.error("이미지 업로드 중 에러가 발생했습니다.: {}",e.getMessage());
			return "error";
		}
		
	}
		
	//게시물 조회 - 전체
	@GetMapping("/list")
	public String list(@ModelAttribute("cri") Criteria cri ,Model model) {
		List<BoardDetailDTO> list = boardService.getPostListWithPaging(cri);
		model.addAttribute("list", list);
		
		int total = boardService.getTotalCount(cri);
		
		model.addAttribute("pageMaker", new pageDTO(cri, total));
		
		return "board/list";
		
	}
	//게시물 조회 - 한개
	@GetMapping("/detail/{id}")
	public String boardDetail(@PathVariable("id")int id, Model model) {
		BoardDetailDTO dto = boardService.getPostDetail(id);
		model.addAttribute("board",dto);
		List<AttachVO> avoList = boardService.getAttachList(id);
		model.addAttribute("attachList", avoList);
		model.addAttribute("apiKey", kakaoApiKey);
		boardService.updateViewCnt(id);
		
		return "board/detail";
	}
	
	
	
	
	//수정
	@GetMapping("/edit/{id}")
	public String boardGetEdit(@PathVariable("id")int id, Model model) {
		BoardDetailDTO detailDTO = boardService.getPostDetail(id);
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("board", detailDTO);
		List<AttachVO> avoList = boardService.getAttachList(id);
		model.addAttribute("attachList", avoList);
		model.addAttribute("serverTime", now.format(formatter));
		model.addAttribute("apiKey", kakaoApiKey);
		return "board/edit";
	}
	@PostMapping("/edit/{id}")
	public String boardPostEdit(@PathVariable("id")int id,BoardRegisterDTO dto, 
			MultipartFile[] uploadFiles, @RequestParam(value="deleteFiles", required=false) List<String> deleteFiles,
			RedirectAttributes rttr) {
		dto.setBno(id);		
		boardService.updatePost(dto,uploadFiles,deleteFiles);
		rttr.addFlashAttribute("success", "게시글 수정에 성공했습니다.");
		return "redirect:/board/detail/" + id;
	}
	
	//삭제 - 상세페이지에서
	@PostMapping("/delete")
	public String deletePost(@RequestParam("bno") int bno,RedirectAttributes rttr) {
		boardService.deletePostOne(bno);
		rttr.addFlashAttribute("success", "게시물이 삭제되었습니다.");
		log.info("게시물이 삭제되었습니다.");
		return "redirect:/board/list";
	}
	//삭제 - 마이페이지에서
	@PostMapping("/mypage/delete")
	public String deletePostInMypage(@RequestParam("bno") int bno, HttpServletRequest request,RedirectAttributes rttr) {
		boardService.deletePostOne(bno);
		rttr.addFlashAttribute("success", "게시물이 삭제되었습니다.");
		String referer = request.getHeader("Referer");
		
		return "redirect:"+(referer != null? referer : "/");
	}
	//파일 넣기 전 검사
	public boolean checkFile(MultipartFile[] uploadFiles){
		if(uploadFiles == null || uploadFiles.length == 0 ) return true; 
		for(MultipartFile uploadFile : uploadFiles) {
			if(uploadFile.isEmpty()) continue;
			String fileName = uploadFile.getOriginalFilename();
			
			if(fileName == null || !fileName.contains(".")) {
				return false;
			}
			
			String ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			
			List<String> allowedExt = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf", "txt", "zip");
			
			if(!allowedExt.contains(ext)) {
				return false;
			}	
		}
		return true;
	}
	
	//파일 다운로드
	@GetMapping(value = "/file/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(String uuid, String uploadPath, String fileName) {
		
		String fullPath = "/home/upload/attach/" + uploadPath + "/" + uuid + "_" + fileName;
	  
	    Resource resource = new FileSystemResource(fullPath);
	    if (!resource.exists()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    String resourceName = resource.getFilename();
	    // UUID 제거한 원본 파일명으로 다운로드 되도록 설정
	    String downloadName = resourceName.substring(resourceName.indexOf("_") + 1);
	    
	    HttpHeaders headers = new HttpHeaders();
	    try {
	        // 한글 깨짐 방지 처리
	        headers.add("Content-Disposition", "attachment; filename=" + 
	                new String(downloadName.getBytes("UTF-8"), "ISO-8859-1"));
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    
	    return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	//파일 삭제
	@PostMapping("file/remove")
	public String removeFileOne(String fileName, String type) {
		return "";
	}
	
	
}
