package com.fmc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fmc.domain.MemberVO;
import com.fmc.dto.MemberEditDTO;
import com.fmc.dto.MemberJoinDTO;
import com.fmc.dto.MemberLoginDTO;
import com.fmc.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	
	//login
	@GetMapping("/login")
	public String getLogin() {
		return "members/login";
	}
	@PostMapping("/login")
	public String postLogin(@Valid MemberLoginDTO dto, BindingResult result, RedirectAttributes rttr, HttpSession session) {
		if(result.hasErrors()) {
			String errMsg = result.getFieldError().getDefaultMessage();
			rttr.addFlashAttribute("fail", errMsg);
			log.info(errMsg);
			return "redirect:/members/login";
		}
		MemberVO vo = memberService.login(dto);
		
		if(vo != null && vo.getPw().equals(dto.getPw())) {
			session.setAttribute("loggedMember", vo);
			log.info("로그인에 성공했습니다.");
			return "redirect:/";
		}else {
			log.info("로그인에 실해했습니다.");
			rttr.addFlashAttribute("fail", "로그인에 실패했습니다.");
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
			rttr.addFlashAttribute("fail", errMsg);
			
			return "redirect:/";
		}
		try {
			memberService.join(dto);
			rttr.addFlashAttribute("success", "회원가입이 완료되었습니다!");
			return "redirect:/members/login";
		} catch (RuntimeException e) {
			rttr.addFlashAttribute("fail", "회원가입에 실패했습니다.");
			log.info(e.getMessage());
			return "redirect:/members/join";
		}		
	}
	
	//logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	//mypage
	@GetMapping("/mypage") 
	public String myPage(){
		return "members/mypage-user";
	}
	
	//edit
	@GetMapping("/edit") 
	public String getEdit(){
		return "members/mypage-user-edit";
	}
	@PostMapping("/edit")
	public String postEdit(MemberEditDTO dto, HttpSession session,RedirectAttributes rttr) {
		try {
			MemberVO vo = memberService.modifyMemberInfo(dto);
			session.setAttribute("loggedMember", vo);
		} catch (RuntimeException e) {
			rttr.addFlashAttribute("fail", "회원정보 수정에 실패했습니다.");
			log.info(e.getMessage());
			return "redirect:/members/edit";
		}
		
		return "redirect:/members/mypage";
	}
	//delete
	@PostMapping("/delete")
	public String postDelete(HttpSession session, RedirectAttributes rttr) {
		MemberVO vo = (MemberVO) session.getAttribute("loggedMember");
		try {
			memberService.deleteMember(vo.getEmail());
			session.invalidate();
		} catch (RuntimeException e) {
			rttr.addFlashAttribute("fail", "회원정보 삭제에 실패했습니다.");
			log.info(e.getMessage());
			return "redirect:/members/edit";
		}
		
		return "redirect:/";
	}
}
