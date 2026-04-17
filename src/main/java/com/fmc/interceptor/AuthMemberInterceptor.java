package com.fmc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

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
	
}
