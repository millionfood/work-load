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
	<%-- kakaoMap --%>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&libraries=services"></script>
</head>
<body>
	<div class="body_wrapper">
		<tag:nav></tag:nav>
		<tag:flash></tag:flash>
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
							<span>작성자:${board.nickname} | ${board.formattedUpdateDateDetail}</span>
							<span class="board_detail_content_detail_cnt">조회수:${board.viewcnt} | 댓글:${board.replycnt}개</span>
						</div>
					</div>
					<div class="board_detail_content">${board.content}</div>
					<div class="board_detail_upload_result">
						<span>첨부파일 목록</span>
						<c:forEach items="${attachList }" var="attach">
							<li data-uuid="${attach.uuid}" data-path="${attach.uploadPath}" data-filename="${attach.fileName}">
				                <div>
				                    <span> ${attach.fileName}</span><br>
				                    <c:url value="/board/file/download" var="downloadUrl">
									    <c:param name="uuid" value="${attach.uuid}" />
									    <c:param name="uploadPath" value="${attach.uploadPath}" />
									    <c:param name="fileName" value="${attach.fileName}" /> 
								    </c:url>
				                    <a href="${downloadUrl }">
				                        [다운로드]
				                    </a>
				                </div>
				            </li>
						</c:forEach>
					</div>
					<div class="board_detail_content">
						<span>회사 주소 : ${board.address}</span>
					</div>
					<div class="board_detail_content_address_box">
						<div class="board_detail_content_address_img" id="map" style="width: 500px; height: 400px"></div>
					</div>
					<div class="board_detail_content">
						<c:if test="${loggedMember.mno eq board.mno }">
							<a href="${pageContextPath}/board/edit/${board.bno}">
								<span>수정</span>
							</a>
							<button type="button" class="deletePostBtn">삭제</button>
						</c:if>
					</div>
					<div class="board_detail_content">
					<span id="board_detail_content_reply_count">댓글 ${board.replycnt}개</span>
					</div>
					<c:if test="${not empty loggedMember}">
						<div class="board_detail_reply_form_box">
							<form>
								<textarea name="reply" id="replyContent"></textarea>
								<button type="button" onclick="addReply()">댓글작성</button>
							</form>
						</div>
					</c:if>
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
		let loggedMemberMno = -1;
		const deleteBtn = document.querySelector(".deletePostBtn");
		const deleteForm = document.querySelector(".deletePostForm");
		const replyCntSpan = document.querySelector("#board_detail_content_reply_count");
		const viewCntAndReplyCnt = document.querySelector(".board_detail_content_detail_cnt");
		let replyCnt = ${board.replycnt};

		<c:if test="${not empty loggedMember}">
			loggedMemberMno = ${loggedMember.mno}
		</c:if>

		//카카오 지도
		var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
        var options = {
          //지도를 생성할 때 필요한 기본 옵션
          center: new kakao.maps.LatLng(${board.lat}, ${board.lng}), //지도의 중심좌표.
          level: 3, //지도의 레벨(확대, 축소 정도)
        };

        var map = new kakao.maps.Map(container, options);
        var geocoder = new kakao.maps.services.Geocoder();

        var marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(${board.lat}, ${board.lng}), // 초기 위치
            map: map // 마커가 표시될 지도 객체
        });

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
									<span id="reply_content_\${reply.rno}">\${reply.replyText}</span>
								</div>
								<div>
									<span
									>\${reply.nickname}님이 \${reply.regDate} 에 작성한 댓글
									</span>
									\${loggedMemberMno == reply.mno ? `
										<div>
											<button type="button" onclick="editReplyMode(\${reply.rno},'\${reply.replyText}')">수정</button>
											<button type="button" onclick="deleteReply(\${reply.bno},\${reply.rno})">삭제</button>
										</div>
									`:''}
								</div>
							</div>
							`;
						});
					}else{
						html = "<p>등록된 댓글이 없습니다.</p>"
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
						$(replyCntSpan).text("댓글 "+(++replyCnt)+" 개");
						$(viewCntAndReplyCnt).text("조회수:"+${board.viewcnt}+" | 댓글:"+(replyCnt)+"개")
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
                >\${textContent}</textarea>
                <div class="reply_edit_content_button_box">
                  <button type="button" onclick="showList()">취소</button>
                  <button type="button" onclick="updateReply(\${rno})">
                    수정완료
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
						$(replyCntSpan).text("댓글 "+(--replyCnt)+" 개");
						$(viewCntAndReplyCnt).text("조회수:"+${board.viewcnt}+" | 댓글:"+(replyCnt)+"개")
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
</body>
</html>