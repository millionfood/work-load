package com.fmc.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAdvice {

	@Around("execution(* com.fmc.service..*Impl.*(..))")
	public Object logService(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		
		log.info("========================================================");
		log.info("Target Class : "+pjp.getTarget().getClass().getSimpleName());
		log.info("Method : "+pjp.getSignature().getName());
		log.info("Args :" +Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		
		try {
			result = pjp.proceed();
		}catch (Exception e) {
			log.error("AOP Exception : "+ e.getMessage());
			throw e;
		}
		
		long end = System.currentTimeMillis();
		log.info("Execution Time : "+(end - start) + "ms");
		log.info("========================================================");
		
		return result;
	}
	
	@Around("execution(* com.fmc.controller..*.*(..))")
	public Object logController(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		log.info("▶▶▶ [request] : {} {} | Controller : {}.{}", 
	            request.getMethod(), 
	            request.getRequestURI(),
	            pjp.getTarget().getClass().getSimpleName(), 
	            pjp.getSignature().getName());

		Object result = null;
		
		try {
			result = pjp.proceed();
		}catch (Exception e) {
			log.error("▶▶▶ [controller error] : {}", e.getMessage());
			throw e;
		}
		
		log.info("◀◀◀ [response] : {} ◀◀◀", 
	            (result != null ? result : "void/ajax"));
		
		return result;
	}
}
