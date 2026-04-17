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
			<div class="board_detail_wrapper">
				<div class="board_detail_container">
				<div class="board_detail_header_box">
					<div class="board_detail_header_title">
					<span>WORK_LOG</span>
					</div>
					<div class="board_detail_header_subtitle">
					<span>게시글 확인</span>
					</div>
				</div>
				<div class="board_detail_content_box">
					<div class="board_detail_content">
						<div class="board_detail_content_title">
							<span>${board.title}</span>
						</div>
						<div class="board_detail_content_detail">
							<span>작성자:${board.nickname} | ${board.updateDate}</span>
							<span>조회수:${board.viewcnt} | 댓글:{board.replyCnt}개</span>
						</div>
					</div>
					<div class="board_detail_content">${board.content}</div>
					<div class="board_detail_content">
						<span>주소 : ${board.address}</span>
					</div>
					<div class="board_detail_content"></div>
					<div class="board_detail_content">
						<a href="${pageContextPath}/board/edit/${board.bno}">
							<span>수정</span>
						</a>
						<button type="button" class="deletePostBtn">삭제</button>
					</div>
					<div class="board_detail_content">
					<span>댓글 {board.replyCnt}개</span>
					</div>
					<div class="board_detail_content">
					<textarea name="reply"></textarea>
					<button type="button">댓글작성</button>
					</div>
					<div class="board_detail_content_comment">
					<div>
						<span>{reply.comment}</span>
					</div>
					<div>
						<span
						>{reply.writer}님이 {reply.updateDate}에 작성한 댓글
						</span>
						<div>
							<button>수정</button>
							<button>삭제</button>
						</div>
					</div>
					</div>
					<div class="board_detail_content8"></div>
					<form action="${pageContextPath}/board/delete" class="deletePostForm" method="post">
						<input type="hidden" name="bno" value=${board.bno}>
					</form>
				</div>
				</div>
			</div>
		</tag:bodyContainer>
	</div>
	<script type="text/javascript">
		const deleteBtn = document.querySelector(".deletePostBtn");
		const deleteForm = document.querySelector(".deletePostForm");
		
		if(deleteBtn){
			deleteBtn.addEventListener("click",()=>{
				if(confirm("정말 삭제하시겠습니까?")){
					deleteForm.submit();
				}
			})
		}
	</script>
</body>
</html>