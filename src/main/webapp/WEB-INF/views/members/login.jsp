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
			<div class="login_page_wrapper">
        <div class="login_page_container">
          <div class="login_page_box">
            <h1>로그인</h1>
          </div>
          <div class="login_page_box">
            <form
              method="post"
              class="login_page_form"
              action="${pageContext.request.contextPath}/members/login"
            >
              <div class="login_page_input_gourp">
                <label for="login_input1">email</label>
                <input
                  type="text"
                  id="login_input1"
                  name="email"
                  class="login_email"
                  maxlength="30"
                  required
                />
              </div>
              <div class="login_page_input_gourp">
                <label for="login_input2">password</label>
                <input
                  type="password"
                  id="login_input2"
                  name="pw"
                  class="login_password"
                  maxlength="50"
                  required
                />
              </div>
              <input type="submit" class="login_submit" value="LOGIN" />
            </form>
          </div>
        </div>
      </div>
		</tag:bodyContainer>
	</div>
	
</body>
</html>