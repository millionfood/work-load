package com.fmc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fmc.dto.BoardDetailDTO;
import com.fmc.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

	private final BoardService boardSerivce;
	
	@GetMapping("")
	public String home(Model model) {
		List<BoardDetailDTO> list = boardSerivce.getPostListWithPopular();
		model.addAttribute("list", list);
		return "home";
	}
	
}
