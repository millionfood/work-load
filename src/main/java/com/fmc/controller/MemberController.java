package com.fmc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
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

import com.fmc.domain.MemberVO;
import com.fmc.dto.BoardDetailDTO;
import com.fmc.dto.Criteria;
import com.fmc.dto.MemberEditDTO;
import com.fmc.dto.MemberJoinDTO;
import com.fmc.dto.MemberLoginDTO;
import com.fmc.dto.ReplyDetailDTO;
import com.fmc.dto.pageDTO;
import com.fmc.service.BoardService;
import com.fmc.service.MemberService;
import com.fmc.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	private final BoardService boardService;
	private final ReplyService replyService;
	
	//login
	@GetMapping("/login")
	public String getLogin() {
		return "members/login";
	}
	@PostMapping("/login")
	public String postLogin(@Valid MemberLoginDTO dto, BindingResult result, RedirectAttributes rttr, HttpSession session) {
		if(result.hasErrors()) {
			String errMsg = result.getFieldError().getDefaultMessage();
			rttr.addFlashAttribute("fail", "로그인에 실패했습니다.");
			log.error("로그인에 실패 : {}",errMsg);
			return "redirect:/members/login";
		}
		MemberVO vo = memberService.getMember(dto);
		
		if(vo != null && BCrypt.checkpw(dto.getPw(), vo.getPw())) {
			session.setAttribute("loggedMember", vo);
			rttr.addFlashAttribute("success", "로그인에 성공했습니다.");
			return "redirect:/";
		}else {
			rttr.addFlashAttribute("fail", "해당 계정을 찾을 수 없습니다.");
			log.error("로그인에 실해했습니다. - 아이디 또는 비밀번호가 일치하지 않습니다.");
			return "redirect:/members/login";
		}
	}
	
	
	//join
	@GetMapping("/join")
	public String getJoin() {
		return "members/join";
	}
	@PostMapping("/join")
	public String postJoin(@Valid MemberJoinDTO dto, BindingResult result, RedirectAttributes rttr) {
		//검사결과 에러가 하나라도 있다면
		if(result.hasErrors()) {
			String errMsg = result.getFieldError().getDefaultMessage();
			rttr.addFlashAttribute("fail", "회원가입에 실패했습니다.");
			log.error("올바른 회원가입 정보가 아닙니다. : {}",errMsg);
			return "redirect:/members/join";
		}
		memberService.join(dto);
		rttr.addFlashAttribute("success", "회원가입이 완료되었습니다!");
		return "redirect:/members/login";
	}
	
	//logout
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes rttr) {
		session.invalidate();
		rttr.addFlashAttribute("success", "로그아웃 되었습니다..");
		return "redirect:/";
	}
	
	
	//mypage
	@GetMapping("/mypage-user/{path}") 
	public String myPageUser(@PathVariable("path")String path,@ModelAttribute("cri") Criteria cri,HttpSession session,Model model){
		MemberVO vo = (MemberVO) session.getAttribute("loggedMember");
		
		if(path.equals("board")) {
			List<BoardDetailDTO> boardList = boardService.getPostListByMnoWithPaging(vo.getMno(), cri);
			model.addAttribute("list", boardList);
		}else {
			List<ReplyDetailDTO> replyList = replyService.getListWithMno(vo.getMno(),cri);
			model.addAttribute("list", replyList);
		}	
		
		model.addAttribute("path", path);
		return "members/mypage-user";			
	}
	@GetMapping("/mypage-admin/{path}") 
	public String myPageAdmin(@PathVariable("path") String path,@ModelAttribute("cri") Criteria cri,Model model){
		if(path.equals("member")) {
			List<MemberVO> list = memberService.getMemberList();
			model.addAttribute("memberList", list);
		}else {
			List<BoardDetailDTO> list = boardService.getPostListWithPaging(cri);
			int total = boardService.getTotalCount(cri);
			model.addAttribute("boardList", list);
			model.addAttribute("pageMaker", new pageDTO(cri, total));
			
		}
		
		model.addAttribute("path", path);
		return "members/mypage-admin";
	}
	
	//edit
	@GetMapping("/edit") 
	public String getEdit(){
		return "members/mypage-edit";
	}
	@PostMapping("/edit")
	public String postEdit(MemberEditDTO dto, HttpSession session,RedirectAttributes rttr) {
			MemberVO vo = memberService.modifyMemberInfo(dto);
			session.setAttribute("loggedMember", vo);
			rttr.addFlashAttribute("success", "회원정보 수정에 성공했습니다.");
			log.info("회원 정보 수정 완료");
			if(vo.getRole().equals("USER")) {
				return "redirect:/members/mypage-user/board";
			}else {
				return "redirect:/members/mypage-admin/member";
			}
		
	}
	//delete
	@PostMapping("/delete")
	public String postDelete(HttpSession session, RedirectAttributes rttr) {
		MemberVO vo = (MemberVO) session.getAttribute("loggedMember");
		memberService.deleteMember(vo.getMno());
		rttr.addFlashAttribute("success", "회원탈퇴가 완료되었습니다.");
		log.info("회원 탈퇴 완료");
		session.invalidate();
		
		return "redirect:/";
	}
	@PostMapping("/admin/delete")
	public String adminPostDelete(@RequestParam("mno")int mno,RedirectAttributes rttr) {
		memberService.deleteMember(mno);
		rttr.addFlashAttribute("success", "회원삭제가 완료되었습니다.");
		return "redirect:/members/mypage-admin/member";
	}
}
