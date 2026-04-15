<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath }" var="ContextPath"></c:set>
<header class="header_container">
  <div class="header_box">
    <div class="header_menu1">
      <div class="header_menu_box">
        <a class="header_menu_home" href="/"> WorkLog </a>
      </div>
    </div>
    <c:choose>
    	<c:when test="${empty loggedMember}">
    		<div class="header_menu2">
		      <div class="header_menu_box">
		        <a class="header_menu_join" href="${ContextPath}/members/join">
		          join
		        </a>
		      </div>
		      <div class="header_menu_box">
		        <a class="header_menu_login" href="${ContextPath}/members/login">
		          login
		        </a>
		      </div>
		    </div>
    	</c:when>
    	<c:otherwise>
    		<div class="header_menu2">
		      <div class="header_menu_box">
		        <a class="header_menu_join" href="${ContextPath}/members/logout">
		          logout
		        </a>
		      </div>
		      <div class="header_menu_box">
		        <a class="header_menu_login" href="${ContextPath}/members/mypage">
		          mypage
		        </a>
		      </div>
		    </div>
    	</c:otherwise>
    </c:choose>
  </div>
</header>