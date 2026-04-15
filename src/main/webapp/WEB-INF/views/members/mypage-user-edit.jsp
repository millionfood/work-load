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
			<div class="my_page_edit_wrapper">
		        <div class="my_page_edit_container">
		          <div class="my_page_edit_box">
		            <h1>EDIT PAGE</h1>
		          </div>
		          <form
		            action="${pageContext.request.contextPath}/members/edit"
		            method="post"
		            class="my_page_edit_form"
		          >
		            <div class="my_page_edit_input_gourp">
		              <label for="my_input1">email</label>
		              <input
		                type="text"
		                name="email"
		                id="my_input1"
		                class="my_email"
		                value="${loggedMember.email}"
		                readonly
		              />
		            </div>
		            <div class="my_page_edit_input_gourp">
		              <label for="my_input2">nickname</label>
		              <input
		                type="text"
		                name="nickname"
		                id="my_input2"
		                class="my_password"
		                placeholder="${loggedMember.nickname}"
		              />
		            </div>
		            <div class="my_page_edit_input_gourp">
		              <label for="my_input3">password</label>
		              <input
		                type="text"
		                name="pw"
		                id="my_input3"
		                class="my_password"
		                placeholder="${loggedMember.pw}"
		              />
		            </div>
		            <input type="submit" class="edit_submit" value="EDIT" />
		          </form>
		        </div>
		      </div>
		</tag:bodyContainer>
	</div>
</body>
</html>




