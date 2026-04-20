package com.fmc.controller;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.PageData;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fmc.domain.BoardVO;
import com.fmc.domain.MemberVO;
import com.fmc.domain.ReplyVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.BoardRegisterDTO;
import com.fmc.dto.Criteria;
import com.fmc.dto.pageDTO;
import com.fmc.service.BoardService;
import com.fmc.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private final ReplyService replyService;
	
	//게시물 조회 - 전체
	@GetMapping("/list")
	public String list(@ModelAttribute("cri") Criteria cri ,Model model) {
//		List<BoardDetailDTO> list = boardService.getPostList();	
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
		
		return "board/detail";
	}
	
	//게시물 작성
	@GetMapping("/write")
	public String getWrite(Model model) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("serverTime", now.format(formatter));
		return "board/write";
	}
	@PostMapping("/write")
	public String postWrite(@Valid BoardRegisterDTO dto,BindingResult result,RedirectAttributes rttr, HttpSession session) {
		
		if(result.hasErrors()) {
			String errMsg = result.getFieldError().getDefaultMessage();
			rttr.addFlashAttribute("fail", errMsg);
			log.info("게시물 입력 실패 : " + errMsg);
		}
		
		BoardVO bvo = dto.toVO();
		MemberVO mvo = (MemberVO) session.getAttribute("loggedMember");
		bvo.setWriter(mvo.getMno());
		
		try {
			boardService.insert(bvo);
		} catch (RuntimeException e) {
			rttr.addFlashAttribute("fail", "게시물 등록에 실패했습니다.");
			log.info(e.getMessage());
			return "redirect:/board/write";
		}
		
		return "redirect:/board/list";
	}
	
	
	//수정
	@GetMapping("/edit/{id}")
	public String boardGetEdit(@PathVariable("id")int id, Model model) {
		BoardDetailDTO detailDTO = boardService.getPostDetail(id);
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("board", detailDTO);
		model.addAttribute("serverTime", now.format(formatter));
		return "board/edit";
	}
	@PostMapping("/edit/{id}")
	public String boardPostEdit(@PathVariable("id")int id,BoardRegisterDTO dto) {
		BoardVO vo = dto.toVO();
		vo.setBno(id);
		log.info("viewcnt가 정상적으로 들오왔는지 확인합니다."+dto.getViewcnt());
		
		try {
			boardService.updatePost(vo);
		} catch (RuntimeException e) {
			log.info("업데이트 실패"+e);
			return "redirect:/board/edit/" + id;
		}
		
		return "redirect:/board/detail/" + id;
	}
	
	//삭제
	@PostMapping("/delete")
	public String deletePost(@RequestParam("bno") int bno) {
		try {
			boardService.deletePost(bno);
		} catch (RuntimeException e) {
			log.info("게시물 삭제 실패" + e);
		}
		
		return "redirect:/board/list";
	}
	
	
}
