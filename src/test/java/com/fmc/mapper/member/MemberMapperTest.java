package com.fmc.mapper.member;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fmc.domain.MemberVO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class MemberMapperTest {

	@Autowired
	private MemberMapper mapper;
	
	@Test
	@Transactional
	public void testInsert() {
		MemberVO member = new MemberVO();
		member.setEmail("millionfood2@naver.com");
		member.setPw("123123");
		member.setNickname("웅이삼촌");
		
		mapper.insert(member);
		
		log.info("----------");
		log.info("insert 결과 확인:" + member);
		log.info("----------");
	}

}
