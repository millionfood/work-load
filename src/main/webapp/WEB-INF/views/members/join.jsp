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
		<tag:bodyContainer>
			<div class="join_page_wrapper">
		        <div class="join_page_container">
		          <div class="join_page_box">
		            <h1>WELLCOME</h1>
		          </div>
		          <div class="join_page_box">
		            <form
		              method="post"
		              class="join_page_form"
		              action="${pageContext.request.contextPath}/members/join"
		            >
		              <div class="join_page_input_gourp">
		                <label for="join_input1">email</label>
		                <input
		                  type="text"
		                  id="join_input1"
		                  name="email"
		                  class="join_email"
		                  maxlength="30"
		                  required
		                />
		              </div>
		              <div class="join_page_input_gourp">
		                <label for="join_input2">nickname</label>
		                <input
		                  type="text"
		                  id="join_input2"
		                  name="nickname"
		                  class="join_password"
		                  maxlength="50"
		                  required
		                />
		              </div>
		              <div class="join_page_input_gourp">
		                <label for="join_input3">password</label>
		                <input
		                  type="password"
		                  id="join_input3"
		                  name="pw"
		                  class="join_password"
		                  maxlength="50"
		                  required
		                />
		              </div>
		              <div class="join_page_input_gourp">
		                <label for="join_input4">password check</label>
		                <input
		                  type="password"
		                  id="join_input4"
		                  name="pwConfirm"
		                  class="join_password"
		                  maxlength="50"
		                  required
		                />
		              </div>
		              <input type="submit" class="join_submit" value="JOIN" />
		            </form>
		          </div>
		          <div class="join_page_box">
		            <a href="/" class="join_naver_container">
		              <div class="join_naver_box">
		                <span>N</span>
		                <span>Join with Naver</span>
		              </div>
		            </a>
		            <a href="/" class="join_kakao_container">
		              <div class="join_kakao_box">
		                <i class="fas fa-comment"></i>
		                <span>Join with Kakao</span>
		              </div>
		            </a>
		          </div>
		        </div>
		      </div>
		</tag:bodyContainer>
	</div>
	
</body>
</html>