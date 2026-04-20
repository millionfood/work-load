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
					<div class="board_detail_reply_form_box">
						<form>
							<textarea name="reply" id="replyContent"></textarea>
							<button type="button" onclick="addReply()">댓글작성</button>
						</form>
					</div>
					<div class="board_detail_content_comment" id="reply-list-container">
					</div>
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
		let isEditForm;
		
		if(deleteBtn){
			deleteBtn.addEventListener("click",()=>{
				if(confirm("정말 삭제하시겠습니까?")){
					deleteForm.submit();
				}
			})
		}
		$(document).ready(function(){
			showList();
		})
		function showList(){
			isEditForm = false;
			const boardId = ${board.bno};
			const listContainer = $("#reply-list-container");
			
			$.ajax({
				type:"GET",
				url:"${ContextPath}/reply/list/"+boardId,
				dataType: "JSON",
				success: function(data){
					let html ="";
					if(data.length > 0){
						$.each(data, function(index, reply){
							html += `
							<div class="board_detail_content_comment_item" id="reply_content_box_\${reply.rno}">
								<div>
									<span id="'reply_content_'+\${reply.rno}">\${reply.replyText}</span>
								</div>
								<div>
									<span
									>\${reply.nickname}님이 \${reply.updateDate}에 작성한 댓글
									</span>
									<div>
										<button type="button" onclick="editReplyMode(\${reply.rno},\${reply.replyText})">수정</button>
										<button type="button" onclick="deleteReply(\${reply.rno})">삭제</button>
									</div>
								</div>
							</div>
							`
						});
					}else{
						html = "<p>등록된 댓글이 업습니다.</p>"
					}
					listContainer.html(html);
				},
				error: function(){
					alert("댓글 목록을 불러오는데 실패했습니다.")
				}
			})
		}
		
		function addReply(){
			//1.dom 요소에서 값 추출
			const replyText = document.getElementById("replyContent").value
			const boardId = ${board.bno};
			
			if(!replyText.trim()){
				alert("내용을 입력해주세요.");
				return;
			}
			//2.서버로 보낼 데이터 객체 생성
			const replyData = {
					bno:"${board.bno}",
					replyText:replyText,
					writer:"${loggedMember.mno}"
			}
			//3.fetch를 이용한 비동기 요청
			<%-- fetch("/reply/new",{
				method:'POST',
				headers:{
					'Content-Type':'application/json'
				},
				body : JSON.stringify(replyData)
			})
			.then(response =>  response.text())
			.then(data =>{
				if(data === "success"){
					alert("댓글이 등록되었습니다.");
					document.getElementById("replyContent").value = "";
				}
			})
			.catch(error => {
				alert("댓글 등록에 실패했습니다.");
				console.error("Error : ",error);
			}) --%>
			//3.jquery ajax 호출
			$.ajax({
				type:"POST",
				url:"${ContextPath}/reply/new",
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(replyData),
				success: function(result){
					if(result === "success"){
						alert("댓글이 등록되었습니다.");
						$("#replyContent").val("")
						showList();
					}
				},
				error:function(xhr,status,error){
					console.error("에러 발생",error);
					alert("등록에 실패했습니다.");
				}
			})
		}

		function editReplyMode(rno,textContent){
			if(isEditForm) return;
			isEditForm = true;
			const replyItem = $("#reply_content_box_"+rno);
			const currentContent =  replyItem.find(".reply_content_"+rno);
			
			const editHtml =`
				<textarea
                  name="reply"
                  id="replyEditContent"
                  class="reply_edit_content_textarea"
                  placeholder="\${textContent}"
                ></textarea>
                <div class="reply_edit_content_button_box">
                  <button type="button" onclick="showList()">취소</button>
                  <button type="button" onclick="updateReply(\${rno})">
                    댓글작성
                  </button>
                </div>
			`;
			replyItem.html(editHtml);
		}

		function updateReply(rno){
			const replyText = $("#replyEditContent").val();
			const replyData = {
				rno : rno,
				replyText : replyText
			};

			$.ajax({
				type:"PUT",
				url:"${ContextPath}/reply/edit/"+rno,
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(replyData),
				success: function(result){
					if(result === "success"){
						alert("댓글이 수정되었습니다.");
						showList();
					}
				},
				error:function(xhr,status,error){
					alert("수정에 실패했습니다.");
					showList();
				}

			})
			
		}
		function deleteReply(rno){
			if(!confirm("이 댓글을 삭제하시겠습니까?")){
				return
			}
			$.ajax({
				type:"DELETE",
				url:"${ContextPath}/reply/delete/"+rno,
				success:function(result){
					if(result === "success"){
						alert("댓글이 삭제되었습니다.");
						showList();
					}
				},
				error: function(){
					alert("댓글 삭제에 실패했습니다.")
					showList();
				}
			})
		}
	</script>
</html>