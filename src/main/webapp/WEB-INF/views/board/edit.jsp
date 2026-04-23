<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="ContextPath"></c:set>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="${ContextPath}/resources/css/styles.css">
	<link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
    />
	<link
      href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <!-- include summernote css/js -->
    <link
      href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
    <%-- kakaoMap --%>
    <script src="//t1.kakaocdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&libraries=services"></script>
</head>
<body>
	<div class="body_wrapper">
		<tag:nav></tag:nav>
    <tag:flash></tag:flash>
		<tag:bodyContainer>
			<div class="board_edit_wrapper">
		        <div class="board_edit_container">
		          <div class="board_edit_header_box">
		            <div class="board_edit_header_title">
		              <span>WORK_LOG</span>
		            </div>
		            <div class="board_edit_header_subtitle">
		              <span>게시글 작성</span>
		            </div>
		          </div>
		          <div class="board_edit_content_box">
		            <div class="board_edit_content">
                <%-- form --%>
		              <form action="/board/edit/${board.bno}" method="post" enctype="multipart/form-data">
		                <div class="board_edit_content_title_edit_date_box">
		                  <div class="board_edit_content_title">
		                    <span>제목</span>
		                    <input type="text" name="title" value="${board.title}" />
		                  </div>
		                  <div class="board_edit_content_editr">
		                    <span>작성자</span>
		                    <input
		                      type="text"
		                      name="writer"
		                      value="${board.nickname}"
		                      readonly
		                    />
		                  </div>
		                  <div class="board_edit_content_date">
		                    <span>날짜</span>
		                    <input type="text" name="date" value="${serverTime}" />
		                  </div>
		                </div>
		                <textarea name="content" id="summernote">
		                  ${board.content}
                    </textarea>
		                <input type="hidden" name="veiwcnt" value="${board.viewcnt}">
                    <input
				                    class="board_write_content_file_input_box"
				                    type="file"
				                    name="uploadFiles"
				                    multiple
				            />
                    <div class="board_edit_upload_result">
                        <span>첨부파일 목록</span>
                        <input type="hidden" name="bno" value="${board.bno}">
                        <div id="deleteFileList"></div>
                        <div class="uploadResult">
                            <ul>
                                <c:forEach items="${attachList}" var="attach">
                                  <li data-uuid="${attach.uuid}" data-path="${attach.uploadPath}" data-filename="${attach.fileName}">
                                      <div>
                                          <span> ${attach.fileName}</span>
                                          <button type="button" class="attach_delete_btn" 
                                                    data-uuid="${attach.uuid}">
                                                    X
                                          </button>
                                      </div>
                                  </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
		                <div class="board_edit_content_address_box">
		                  <div class="board_edit_content_address_input">
		                    <span>회사 주소</span>
		                    <input type="hidden" name="lat" id="lat" value="${board.lat}">
                        <input type="hidden" name="lng" id="lng" value="${board.lng}">
                        <input type="text" name="address" onclick="onPostCode(event)" id="kakaoInput" value="${board.address}" required/>
		                  </div>
		                  <div class="board_edit_content_address_img" id="map" style="width: 500px; height: 400px"></div>
		                </div>
		                <div class="board_edit_content_button_box">
		                  <button>등록</button>
		                  <button type="button" onclick="history.back()">
		                    뒤로가기
		                  </button>
		                </div>
		              </form>
		            </div>
		          </div>
		        </div>
		      </div>
		</tag:bodyContainer>
	</div>
	<script>
      var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|jsp|asp|php|js)$");
      var maxSize = 10485760; 
      function checkExtension(fileName, fileSize) {
          if (fileSize >= maxSize) {
              alert("파일 사이즈 초과");
              return false;
          }

          if (regex.test(fileName)) {
              alert("해당 종류의 파일은 업로드할 수 없습니다.");
              return false;
          }
          return true;
      }

      // 파일 선택 시 이벤트
      $("input[type='file']").change(function(e) {
          var formData = new FormData();
          var inputFile = $("input[name='uploadFile']");
          var files = inputFile[0].files;

          for (var i = 0; i < files.length; i++) {
              if (!checkExtension(files[i].name, files[i].size)) {
                  $(this).val(""); // 잘못된 파일이면 비워줌
                  return false;
              }
          }
      });
      
      $(document).ready(function () {
        $(".attach_delete_btn").on("click", function() {
        if(confirm("이 파일을 삭제하시겠습니까? (수정 완료 시 실제로 삭제됩니다)")) {
            var uuid = $(this).data("uuid");
            var targetLi = $(this).closest("li");

            // 1. 화면에서 해당 파일 리스트 아이템 삭제(숨김)
            targetLi.remove();

            // 2. 삭제할 UUID를 담은 hidden input을 폼에 추가
            var str = "<input type='hidden' name='deleteFiles' value='" + uuid + "'>";
            $("#deleteFileList").append(str);
        }
        });
        $("#summernote").summernote({
          height: 400, // 에디터 높이
          minHeight: null, // 최소 높이
          maxHeight: null, // 최대 높이
          focus: true, // 에디터 로딩 후 포커스 설정
          lang: "ko-KR", // 한글 설정
          placeholder: "내용을 입력해주세요.",
          codeviewFilter: true,           // 코드 보기 모드에서 스크립트 필터링 활성화
          codeviewIframeFilter: true,
          toolbar: [
            // [groupName, [write of button]]
            ["fontname", ["fontname"]],
            ["fontsize", ["fontsize"]],
            [
              "style",
              ["bold", "italic", "underline", "strikethrough", "clear"],
            ],
            ["color", ["forecolor", "color"]],
            ["table", ["table"]],
            ["para", ["ul", "ol", "paragraph"]],
            ["height", ["height"]],
            ["insert", ["picture", "link", "video"]],
            ["view", ["fullscreen", "help"]],
          ],
          fontNames: [
            "Arial",
            "Arial Black",
            "Comic Sans MS",
            "Courier New",
            "맑은 고딕",
            "궁서",
            "굴림체",
            "굴림",
            "돋움체",
            "바탕체",
          ],
          fontSizes: [
            "8",
            "9",
            "10",
            "11",
            "12",
            "14",
            "16",
            "18",
            "20",
            "22",
            "24",
            "28",
            "30",
            "36",
            "48",
            "64",
            "82",
            "150",
          ],
          callbacks:{
            onImageUpload: function(files) {
              for(var i = 0; i< files.length; i++){
                uploadImage(files[i],this);
              }
            }
          }
        });
      });

      function uploadImage(file, editor){
        var data = new FormData();
        data.append("file", file);

        $.ajax({
          url: '/board/uploadImage', // 서버 업로드 주소
          type: 'POST',
          data: data,
          contentType: false,
          processData: false,
          success: function(url) {
              // 서버에서 받은 URL을 에디터 본문에 삽입
              $(editor).summernote('insertImage', url);
          },
          error: function() {
              alert("이미지 업로드에 실패했습니다.");
          }
        });
      }
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

      function onPostCode(e){
      e.preventDefault();
      new kakao.Postcode({
        oncomplete: function(data) {
          $("#kakaoInput").val(data.address)

          // 2. 주소로 상세 위치(좌표)를 검색
          geocoder.addressSearch(data.address, function(results, status) {

              if (status === kakao.maps.services.Status.OK) {
                  var result = results[0]; // 첫 번째 결과 선택
                  var coords = new kakao.maps.LatLng(result.y, result.x); // 좌표 생성

                  // 3. 지도를 보여준다 (만약 처음엔 숨겨놨다면)
                  $("#map").show();
                  map.relayout(); // 지도를 다시 그려줌 (크기 변화 대응)
                  
                  // 4. 지도 중심을 이동시키고 마커를 찍는다
                  map.setCenter(coords);
                  marker.setPosition(coords);

                  $("#lat").val(result.y);
                  $("#lng").val(result.x);
                }
            });
          }
        }).open();
      }
    </script>
</body>
</html>