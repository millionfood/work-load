package com.fmc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmc.domain.ReplyVO;
import com.fmc.dto.ReplyDetailDTO;
import com.fmc.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {

	private final ReplyService replyService;
	
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		replyService.insert(vo);
		log.info("댓글 등록에 성공했습니다.");
		return new ResponseEntity<>("success", HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/list/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReplyDetailDTO>> getList(@PathVariable("bno") int bno){
		List<ReplyDetailDTO> list = replyService.getListWithBno(bno);
		
		return new ResponseEntity<List<ReplyDetailDTO>>(list,HttpStatus.OK);
	}
	
	@PutMapping(value = "/edit/{rno}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> edit(@RequestBody ReplyDetailDTO dto, @PathVariable("rno") Long rno){
		replyService.update(dto);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@DeleteMapping(value="/delete/{bno}/{rno}",produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("bno") int bno, @PathVariable("rno") Long rno){
		replyService.deleteOne(bno,rno);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
}
