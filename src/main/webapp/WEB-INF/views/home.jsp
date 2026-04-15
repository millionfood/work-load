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
</head>
<body>
	<div class="body_wrapper">
		<tag:nav></tag:nav>
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
              <div class="home_box_gotoboard_box">
                <span>게시판으로 이동</span>
                <i class="fa-solid fa-arrow-right"></i>
              </div>
            </div>
          </div>
          <div class="home_box">
            <div class="home_box_board_title">
              <span>인기글</span>
            </div>
            <div class="home_box_board_post">
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>오늘 너무 힘든 하루였다.</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>일마치고 치맥 한잔 인증샷~</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>이직 조언 구해요..</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>오늘 너무 힘든 하루였다.</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>일마치고 치맥 한잔 인증샷~</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>이직 조언 구해요..</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>오늘 너무 힘든 하루였다.</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>일마치고 치맥 한잔 인증샷~</span>
                </div>
              </div>
              <div class="home_box_board_post_content">
                <div class="home_box_board_post_img">
                  <img src="" alt="" />
                </div>
                <div class="home_box_board_post_text">
                  <span>이직 조언 구해요..</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
			
		</tag:bodyContainer>
	</div>
	
</body>
</html>