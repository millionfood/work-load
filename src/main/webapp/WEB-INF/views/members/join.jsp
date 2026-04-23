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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="body_wrapper">
		<tag:nav></tag:nav>
		<tag:flash></tag:flash>
		<tag:bodyContainer>
			<div class="join_page_wrapper">
		        <div class="join_page_container">
		          <div class="join_page_box">
		            <h1>회원가입</h1>
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
		          
		        </div>
		      </div>
		</tag:bodyContainer>
	</div>
	<script>
		$(document).ready(function() {
		$(".join_page_form").on("submit", function(e) {
			
			// 비밀번호와 비밀번호 확인 값 가져오기
			var pw = $("#join_input3").val();
			var pwConfirm = $("#join_input4").val();

			if (pw !== pwConfirm) {
				alert("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
				
				$("#join_input4").focus();
				e.preventDefault(); 
				return false;
			}
			
		});
});
	</script>
</body>
</html>