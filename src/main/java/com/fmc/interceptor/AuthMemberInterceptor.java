package com.fmc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fmc.domain.MemberVO;

public class AuthMemberInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		HttpSession session = request.getSession();
		
		MemberVO vo = (MemberVO) session.getAttribute("loggedMember");
		
		if(vo == null) {
			String location = request.getContextPath() + "/members/login";
			response.sendRedirect(location);
			return false;
		}
		
		
		return true;
	}
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        
        // 브라우저에게 페이지를 캐시하지 않도록 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0 (옛날 브라우저용)
        response.setDateHeader("Expires", 0); // 프록시 서버용
        
    }
	
}
