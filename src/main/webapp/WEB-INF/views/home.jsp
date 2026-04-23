<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="ContextPath"></c:set>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous"> 
	<link rel="stylesheet" href="${ContextPath}/resources/css/styles.css">
	<link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
    />
</head>
<body>
	<div class="body_wrapper">
		<tag:nav></tag:nav>
		<tag:flash></tag:flash>
		<tag:bodyContainer>
			 <div class="home_wrapper">
        <div class="home_container">
          <div class="home_box">
            <div class="home_box_title">
              <p>오늘 네 하루는 어때?</p>
              <p>우리만 아는 업무 뒷이야기,</p>
              <p>WORK_LOG</p>
            </div>
            <div class="home_box_gotoboard_container">
              <a class="home_box_gotoboard_box" href="${ContextPath}/board/list">
                <span>게시판으로 이동</span>
                <i class="fa-solid fa-arrow-right"></i>  
              </a>
            </div>
          </div>
          <div class="home_box">
            <div class="home_box_board_title">
              <span>인기글</span>
            </div>
            <div class="home_box_board_post">
              <c:forEach items="${list}" var="board">
                <div class="home_box_board_post_content">
                  <div class="home_box_board_post_img">
                    <img src="${board.thumbnail}" alt="" />
                  </div>
                  <div class="home_box_board_post_text">
                    <a href="${ContextPath}/board/detail/${board.bno}"><span>${board.title}</span></a>
                    <span>조회수:${board.viewcnt}</span>
                    <span>댓글:${board.replycnt}</span>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>
			
		</tag:bodyContainer>
	</div>
	
</body>
</html>