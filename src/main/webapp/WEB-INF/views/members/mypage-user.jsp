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
			<div class="my_page_wrapper">
		        <div class="my_page_container">
		          <div class="my_page_box">
		            <h1>MY PAGE</h1>
		          </div>
		          <div class="my_page_box">
		            <div class="my_page_input_gourp">
		              <label for="my_input1">email</label>
		              <input
		                type="text"
		                id="my_input1"
		                class="my_email"
		                value="${loggedMember.email}"
		                readonly
		              />
		            </div>
		            <div class="my_page_input_gourp">
		              <label for="my_input2">nickname</label>
		              <input
		                type="text"
		                id="my_input2"
		                class="my_password"
		                value="${loggedMember.nickname}"
		                readonly
		              />
		            </div>
		            <div class="my_page_input_gourp">
		              <label for="my_input3">password</label>
		              <input
		                type="text"
		                id="my_input3"
		                class="my_password"
		                value="${loggedMember.pw}"
		                readonly
		              />
		            </div>
		          </div>
		          <div class="my_page_box">
		            <a href="/members/edit" class="my_page_edit_link">
		              <div class="my_page_edit_link_box">
		                <span>edit</span>
		              </div>
		            </a>
		            <a href="#" onclick="deleteMember(); return false;" class="my_page_delete_link">
		              <div class="my_page_delete_link_box">
		                <span>delete</span>
		              </div>
		            </a>
		          </div>
		        </div>
		      </div>
		</tag:bodyContainer>
	</div>
	<script>
		function deleteMember(){
			if(confirm("정말 탈퇴하시겠습니까?")){
				const form = document.createElement('form');
				form.method = 'POST';
				form.action = '${ContextPath}/members/delete';
				document.body.appendChild(form);
				form.submit();
			}
		}
	</script>
</body>
</html>