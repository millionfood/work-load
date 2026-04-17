<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<div class="board_list_wrapper">
				<div class="board_list_container">
					<div class="board_list_header_box">
						<div class="board_list_header_title">
						<span>WORK_LOG</span>
						</div>
						<div class="board_list_header_subtitle">
						<span>당신의 하루는 어땠나요?</span>
						</div>
					</div>
					<div class="board_list_content_box">
						<div class="board_list_content_search">
						<span>${listCnt}개의 게시물</span>
						<div class="board_list_content_search_form">
							<form action="${ContextPath}/board/list" method="get">
							<select name="searchType">
								<option value="title">제목</option>
								<option value="writer">작성자</option>
							</select>
							<input type="text" />
							<button class="board_list_content_search_btn">검색</button>
							</form>
						</div>
						<a href="${ContextPath}/board/write" class="board_list_content_search_link">
							<span>글쓰기</span> 
						</a>
						</div>
						<div class="board_list_content_list">
						<table class="board_list_content_list_table">
							<thead class="board_list_content_list_thead">
							<tr class="board_list_content_list_thead_tr">
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>날짜</th>
								<th>조회수</th>
							</tr>
							</thead>
							<tbody class="board_list_content_list_tbody">
							<c:forEach items="${list}" var="board">
								<tr class="board_list_content_list_tbody_tr">
								<td>${board.bno}</td>
								<td><a href="${ContextPath }/board/detail/${board.bno}">${board.title}</a></td>
								<td>${board.nickname}</td>
								<td>
									${board.formattedUpdateDate }
								</td>
								<td>${board.viewcnt}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						</div>
						<div class="board_list_content_page_number">
							<ul>
								<c:if test="${pageMaker.prev }">
									<li>
										<a href="${ContextPath }/board/list?pageNum=${pageMaker.startPage -1}">이전</a>
									</li>
								</c:if>
								<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
									<li>
										<a href="${ContextPath }/board/list?pageNum=${num}" class="${pageMaker.cri.pageNum == num ? 'page_btn_active' : '' }">${num }</a>
									</li>
								</c:forEach>
								<c:if test="${pageMaker.next }">
									<li>
										<a href="${ContextPath }/board/list?pageNum=${pageMaker.endPage +1}">다음</a>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</tag:bodyContainer>
	</div>
</body>
</html>