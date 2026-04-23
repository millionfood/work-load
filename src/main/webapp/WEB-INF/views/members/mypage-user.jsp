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
	<link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
    />
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<link rel="stylesheet" href="${ContextPath}/resources/css/styles.css">
</head>
<body>
	<div class="body_wrapper">
		<tag:nav></tag:nav>
		<tag:flash></tag:flash>
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
				<div class="my_page_controller_container">
						<div class="my_page_controller_menu_box">
							<a class="my_page_controller_menu ${path == 'board' ? 'select' : '' }" href="${ContextPath}/members/mypage-user/board">게시물</a>
							<a class="my_page_controller_menu ${path == 'reply' ? 'select' : '' }" href="${ContextPath}/members/mypage-user/reply">댓글</a>
						</div>
						<div class="my_page_controller_list_container">
							<div class="my_page_controller_list_box">
							<c:choose> 
								<c:when test="${path == 'board'}">
									<div class="my_page_controller_list_title_board">
										<span class="my_page_controller_list_title_id">ID</span>
										<span class="my_page_controller_list_title_nickName">제목</span>
										<span class="my_page_controller_list_title_email">작성자</span>
										<span class="my_page_controller_list_title_password">날짜</span>
										<span class="my_page_controller_list_title_created">조회수</span>
										<span class="my_page_controller_list_title_created">댓글</span>
										<span class="my_page_controller_list_title_delete"><i class="fas fa-times"></i></span>
									</div>
									<c:forEach items="${list }" var="board">
										<div class="my_page_controller_list_info_board">
											<span class="my_page_controller_list_info_id">${board.bno }</span>
											<span class="my_page_controller_list_info_nickName">${board.title }</span>
											<span class="my_page_controller_list_info_email">${board.nickname }</span>
											<span class="my_page_controller_list_info_password">${board.updateDate }</span>
											<span class="my_page_controller_list_info_created">${board.viewcnt }</span>
											<span class="my_page_controller_list_info_created">${board.replycnt }</span>
											<input type="hidden" class="hiddenInfo">
											<button type="button" class="mypage_delete_member_btn" onclick="deleteBoardByAdmin()">삭제</button>
											<form action="${pageContextPath}/board/mypage/delete" class="mypage_delete_member_form" method="post">
												<input type="hidden" name="bno" value=${board.bno}>
											</form>
										</div>                            
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div class="my_page_controller_list_title_reply">
										<span class="my_page_controller_list_title_id">ID</span>
										<span class="my_page_controller_list_title_nickName">닉네임</span>
										<span class="my_page_controller_list_title_content">내용</span>
										<span class="my_page_controller_list_title_created">작성일</span>
										<span class="my_page_controller_list_title_delete"><i class="fas fa-times"></i></span>
									</div>
									<c:forEach items="${list }" var="reply">
										<div class="my_page_controller_list_info_reply">
											<span class="my_page_controller_list_info_id">${reply.rno }</span>
											<span class="my_page_controller_list_info_nickName">${reply.nickname }</span>
											<span class="my_page_controller_list_info_content">${reply.replyText }</span>
											<span class="my_page_controller_list_info_created">${reply.formattedRegDateDetail }</span>
											<input type="hidden" class="hiddenInfo">
											<button type="button" onclick="deleteReply(${reply.bno},${reply.rno})">삭제</button>
										</div>                            
									</c:forEach>
									<div class="board_list_content_page_number">
									<ul>
										<c:if test="${pageMaker.prev }">
											<li>
												<a href="${ContextPath }/members/mypage/board?pageNum=${pageMaker.startPage -1}">이전</a>
											</li>
										</c:if>
										<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
											<li>
												<a href="${ContextPath }/members/mypage/board?pageNum=${num}" class="${pageMaker.cri.pageNum == num ? 'page_btn_active' : '' }">${num }</a>
											</li>
										</c:forEach>
										<c:if test="${pageMaker.next }">
											<li>
												<a href="${ContextPath }/members/mypage/board?pageNum=${pageMaker.endPage +1}">다음</a>
											</li>
										</c:if>
									</ul>
								</div>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
				   </div>
			</div>
		</tag:bodyContainer>
	</div>
	<script>
		const deleteBoardForm = document.querySelector(".mypage_delete_member_form");

		function deleteBoardByAdmin(){
				if(confirm("정말 삭제하시겠습니까?")){
					deleteBoardForm.submit();
				}
		}

		function deleteMember(){
			if(confirm("정말 탈퇴하시겠습니까?")){
				const form = document.createElement('form');
				form.method = 'POST';
				form.action = '${ContextPath}/members/delete';
				document.body.appendChild(form);
				form.submit();
			}
		}
		function deleteReply(bno,rno){
			if(!confirm("이 댓글을 삭제하시겠습니까?")){
				return
			}
			$.ajax({
				type:"DELETE",
				url:"${ContextPath}/reply/delete/"+bno+"/"+rno,
				success:function(result){
					if(result === "success"){
						alert("댓글이 삭제되었습니다.");
						location.reload();
					}
				},
				error: function(e){
					alert("댓글 삭제에 실패했습니다."+e.status);
					location.reload();
				}
			})
		}
	</script>
</body>
</html>