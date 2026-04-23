package com.fmc.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(LocalFileException.class)
	public String handleLocalFileException(LocalFileException e, HttpServletRequest request, RedirectAttributes rttr){
		rttr.addFlashAttribute("fail", e.getMessage());
		log.error("파일 관련 error : ",e);
		String referer = request.getHeader("Referer");
		
		return "redirect:"+(referer != null ? referer : "/");
	}
	@ExceptionHandler(PersistenceException.class)
	public String handlePersistenceException(PersistenceException e, HttpServletRequest request, RedirectAttributes rttr){
		rttr.addFlashAttribute("fail", e.getMessage());
		log.error("DB 관련 error===================================================================================== : ",e);
		String referer = request.getHeader("Referer");
		
		return "redirect:"+(referer != null ? referer : "/");
	}
	@ExceptionHandler(ApiException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleApiException (ApiException e){
		log.error("댓글 관련 error : ",e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500,e.getMessage(),LocalDateTime.now()));
	}
	
	@ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception e, HttpServletRequest request,RedirectAttributes rttr) {
		rttr.addFlashAttribute("fail", e.getMessage());
        log.error("서버 내부 오류 : ", e);
        String referer = request.getHeader("Referer");
        
        return "redirect:"+(referer != null ? referer : "/");
    }
}
